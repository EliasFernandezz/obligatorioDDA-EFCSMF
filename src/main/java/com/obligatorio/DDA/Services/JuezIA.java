/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obligatorio.DDA.models.Categoria;
import com.obligatorio.DDA.models.Jugador;
import com.obligatorio.DDA.models.Respuesta;
import com.obligatorio.DDA.models.ResultadoCategoria;
import com.obligatorio.DDA.models.ResultadoRonda;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 *
 * @author mateo
 */
@Service
@Primary
public class JuezIA implements IJuez {

    @Autowired
    private IACache iaCache;

    private static final Logger logger = LoggerFactory.getLogger(JuezIA.class);
    private final OpenAIService openAI;

    @Value("${game.points.unique:100}")
    private int puntajeUnico;

    @Value("${game.points.duplicate:50}")
    private int puntajeDuplicado;

  public JuezIA(OpenAIService openAI, IACache iaCache) {
    this.openAI = openAI;
    this.iaCache = iaCache;
}

    @Override
    public ResultadoRonda evaluar(Map<Jugador, Respuesta> respuestas) {

        ResultadoRonda resultado = new ResultadoRonda();


        List<Map<String, String>> pendientes = new ArrayList<>();

        for (var entry : respuestas.entrySet()) {
            for (var resp : entry.getValue().getRespuestas().entrySet()) {

                Categoria categoria = resp.getKey();
                String palabra = resp.getValue().trim();

                // Buscamos en cache
                String respCache = iaCache.obtener(categoria.getNombre(), palabra);

                if (respCache == null) {
                    // NO está cacheado, se debe consultar a la IA
                    Map<String, String> nodo = new HashMap<>();
                    nodo.put("categoria", categoria.getNombre());
                    nodo.put("palabra", palabra);
                    pendientes.add(nodo);
                }
            }
        }

        // ==================================================
        // 2) Si hay pendientes, construir un solo prompt
        // ==================================================
        String respuestaIAjson = null;

        if (!pendientes.isEmpty()) {

            StringBuilder sb = new StringBuilder();
            sb.append("Validá estas palabras del juego.\n");
            sb.append("Devolvé SOLO un JSON EXACTO con esta estructura:\n");
            sb.append("{ \"resultados\": [ { \"categoria\": \"\", \"palabra\": \"\", \"valida\": true/false } ] }\n\n");
            sb.append("Datos:\n");

            for (var p : pendientes) {
                sb.append(" - Categoria: ").append(p.get("categoria"))
                        .append(" → Palabra: ").append(p.get("palabra")).append("\n");
            }

            String prompt = sb.toString();

            // Llamada única a la IA
            respuestaIAjson = openAI.consultarIA(prompt);

            if (respuestaIAjson == null || respuestaIAjson.isBlank()) {
                throw new IllegalStateException("Error: La IA no respondió.");
            }

            // ============================================
            // 3) Guardar en cache los resultados devueltos
            // ============================================
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> json = mapper.readValue(respuestaIAjson, Map.class);
                List<Map<String, Object>> listaIA = (List<Map<String, Object>>) json.get("resultados");

                for (var obj : listaIA) {
                    String categoria = obj.get("categoria").toString();
                    String palabra = obj.get("palabra").toString();
                    String valida = obj.get("valida").toString();

                    // guardar en cache
                    iaCache.guardar(categoria, palabra, valida);
                }

            } catch (JsonProcessingException e) {
                logger.error("Error parseando JSON: " + respuestaIAjson, e);
            }
        }

        // ==================================================
        // 4) Construir lista de resultados completa (cache + IA)
        // ==================================================
        List<Map<String, Object>> listaFinal = new ArrayList<>();

        for (var entry : respuestas.entrySet()) {
            for (var resp : entry.getValue().getRespuestas().entrySet()) {

                Categoria categoria = resp.getKey();
                String palabra = resp.getValue().trim();

                String validaCache = iaCache.obtener(categoria.getNombre(), palabra);

                Map<String, Object> nodo = new HashMap<>();
                nodo.put("categoria", categoria.getNombre());
                nodo.put("palabra", palabra);
                nodo.put("valida", "true".equalsIgnoreCase(validaCache));

                listaFinal.add(nodo);
            }
        }

        // ==================================================
        // 5) Contar duplicados
        // ==================================================
        Map<String, Long> duplicados = new HashMap<>();

        for (var r : listaFinal) {
            String clave = r.get("categoria") + "#" + r.get("palabra");
            duplicados.merge(clave, 1L, Long::sum);
        }

        // ==================================================
        // 6) Armar ResultadoRonda con puntajes
        // ==================================================
        for (Map.Entry<Jugador, Respuesta> entry : respuestas.entrySet()) {

            Jugador jugador = entry.getKey();
            Respuesta res = entry.getValue();

            for (var e : res.getRespuestas().entrySet()) {

                Categoria categoria = e.getKey();
                String palabra = e.getValue().trim();

                Map<String, Object> match = listaFinal.stream()
                        .filter(m -> m.get("categoria").equals(categoria.getNombre())
                              && m.get("palabra").equals(palabra))
                        .findFirst()
                        .orElse(null);

                ResultadoCategoria rc = new ResultadoCategoria(categoria, palabra);

                boolean esValida = match != null && Boolean.TRUE.equals(match.get("valida"));

                if (!esValida) {
                    rc.setValida(false);
                    rc.setMotivo("IA indica que NO pertenece");
                    rc.setPuntaje(0);
                    resultado.agregarResultado(jugador, rc, 0);
                    continue;
                }

                long veces = duplicados.get(categoria.getNombre() + "#" + palabra);

                if (veces > 1) {
                    rc.setValida(true);
                    rc.setDuplicada(true);
                    rc.setMotivo("Válida pero duplicada");
                    rc.setPuntaje(puntajeDuplicado);
                } else {
                    rc.setValida(true);
                    rc.setDuplicada(false);
                    rc.setMotivo("Válida y única");
                    rc.setPuntaje(puntajeUnico);
                }

                resultado.agregarResultado(jugador, rc, rc.getPuntaje());
            }
        }

        return resultado;
    }
}
