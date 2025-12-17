/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.Services;

import static ch.qos.logback.classic.pattern.Util.match;
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
import static javax.management.Query.match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import static org.thymeleaf.util.NumberPointType.match;

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

        /* =====================================================
           1️⃣ Detectar qué respuestas NO están en cache
        ===================================================== */
        List<Map<String, String>> pendientes = new ArrayList<>();

        for (var entry : respuestas.entrySet()) {
            for (var resp : entry.getValue().getRespuestas().entrySet()) {

                Categoria categoria = resp.getKey();
                String palabra = resp.getValue();

                if (palabra == null || palabra.isBlank()) continue;

                palabra = palabra.trim();

                String cache = iaCache.obtener(categoria.getNombre(), palabra);
                if (cache == null) {
                    Map<String, String> nodo = new HashMap<>();
                    nodo.put("categoria", categoria.getNombre());
                    nodo.put("palabra", palabra);
                    pendientes.add(nodo);
                }
            }
        }

        /* =====================================================
           2️⃣ PROMPT DEFINITIVO (CAMBIO CLAVE 🔥)
        ===================================================== */
        if (!pendientes.isEmpty()) {

            StringBuilder prompt = new StringBuilder();
            prompt.append("""
                Sos un validador estricto del juego Basta (Tutti Frutti).

                Para cada par (categoria, palabra) devolvé SI Y SOLO SI:
                - La palabra pertenece claramente a la categoría
                - No es un pronombre, verbo genérico, palabra abstracta ni incorrecta
                - La palabra tiene sentido semántico real para la categoría

                Respondé ÚNICAMENTE con este JSON EXACTO:
                {
                  "resultados": [
                    { "categoria": "X", "palabra": "Y", "valida": true|false }
                  ]
                }

                Datos a validar:
                """);

            for (var p : pendientes) {
                prompt.append("- Categoria: ")
                      .append(p.get("categoria"))
                      .append(" | Palabra: ")
                      .append(p.get("palabra"))
                      .append("\n");
            }

            String respuestaIA = openAI.consultarIA(prompt.toString());

            if (respuestaIA == null || respuestaIA.isBlank()) {
                resultado.setMensajeError("El juez de IA no pudo responder.");
                return resultado;
            }

            /* =====================================================
               3️⃣ Parseo defensivo del JSON (CAMBIO CLAVE 🔥)
            ===================================================== */
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> json = mapper.readValue(respuestaIA, Map.class);

                List<Map<String, Object>> lista =
                        (List<Map<String, Object>>) json.get("resultados");

                for (var r : lista) {
                    String categoria = r.get("categoria").toString();
                    String palabra = r.get("palabra").toString();
                    Object validaObj = r.get("valida");

                    boolean valida = false;
                    if (validaObj instanceof Boolean) {
                        valida = (Boolean) validaObj;
                    }

                    iaCache.guardar(categoria, palabra, String.valueOf(valida));
                }

            } catch (Exception e) {
                logger.error("Error parseando respuesta IA", e);
                resultado.setMensajeError("Error interpretando respuesta de la IA.");
                return resultado;
            }
        }

        /* =====================================================
           4️⃣ Construir resultados finales desde cache
        ===================================================== */
        Map<String, Long> contador = new HashMap<>();

        for (var entry : respuestas.entrySet()) {
            for (var r : entry.getValue().getRespuestas().entrySet()) {

                String clave = r.getKey().getNombre() + "#" + r.getValue();
                contador.merge(clave, 1L, Long::sum);
            }
        }

        /* =====================================================
           5️⃣ Asignar puntajes correctamente
        ===================================================== */
        for (var entry : respuestas.entrySet()) {

            Jugador jugador = entry.getKey();
            Respuesta resp = entry.getValue();

            for (var r : resp.getRespuestas().entrySet()) {

                Categoria categoria = r.getKey();
                String palabra = r.getValue();

                ResultadoCategoria rc = new ResultadoCategoria(categoria, palabra);

                String validaCache = iaCache.obtener(categoria.getNombre(), palabra);
                boolean esValida = "true".equalsIgnoreCase(validaCache);

                if (!esValida) {
                    rc.setValida(false);
                    rc.setPuntaje(0);
                    rc.setMotivo("El juez indica que NO pertenece a la categoría");
                    resultado.agregarResultado(jugador, rc, 0);
                    continue;
                }

                long veces = contador.get(categoria.getNombre() + "#" + palabra);

                rc.setValida(true);
                rc.setDuplicada(veces > 1);
                rc.setPuntaje(veces > 1 ? puntajeDuplicado : puntajeUnico);
                rc.setMotivo(veces > 1 ? "Válida pero duplicada" : "Válida y única");

                resultado.agregarResultado(jugador, rc, rc.getPuntaje());
            }
        }

        return resultado;
    }
}
