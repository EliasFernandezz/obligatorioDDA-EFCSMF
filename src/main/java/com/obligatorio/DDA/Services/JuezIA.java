/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.Services;

import com.obligatorio.DDA.models.Categoria;
import com.obligatorio.DDA.models.Jugador;
import com.obligatorio.DDA.models.Respuesta;
import com.obligatorio.DDA.models.ResultadoCategoria;
import com.obligatorio.DDA.models.ResultadoRonda;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author mateo
 */
public class JuezIA implements IJuez{
 private static final Logger logger = LoggerFactory.getLogger(JuezIA.class);

    private final OpenAIService openAI;

    @Value("${game.points.unique:100}")
    private int puntajeUnico;

    @Value("${game.points.duplicate:50}")
    private int puntajeDuplicado;

    public JuezIA(OpenAIService openAI) {
        this.openAI = openAI;
    }

    @Override
    public ResultadoRonda evaluar(Map<Jugador, Respuesta> respuestas) {

        ResultadoRonda resultadoRonda = new ResultadoRonda();

        // ==== Conteo para detectar duplicados ====
        Map<Categoria, Map<String, Long>> frecuencias = new HashMap<>();

        for (Respuesta r : respuestas.values()) {
            for (Map.Entry<Categoria, String> e : r.getRespuestas().entrySet()) {

                Categoria cat = e.getKey();
                String palabra = (e.getValue() == null) ? "" : e.getValue().trim().toUpperCase();

                frecuencias.putIfAbsent(cat, new HashMap<>());
                frecuencias.get(cat).merge(palabra, 1L, Long::sum);
            }
        }

        // ==== Evaluación con IA ====
        for (Map.Entry<Jugador, Respuesta> entry : respuestas.entrySet()) {

            Jugador jugador = entry.getKey();
            Respuesta rtaJugador = entry.getValue();

            for (Map.Entry<Categoria, String> respuestaCat : rtaJugador.getRespuestas().entrySet()) {

                Categoria categoria = respuestaCat.getKey();
                String palabra = respuestaCat.getValue();
                String palabraNorm = (palabra == null) ? "" : palabra.trim().toUpperCase();

                ResultadoCategoria rc = new ResultadoCategoria(categoria, palabra);

                // Vacía
                if (palabra == null || palabra.isBlank()) {
                    rc.setValida(false);
                    rc.setPuntaje(0);
                    rc.setMotivo("Vacía");
                    resultadoRonda.agregarResultado(jugador, rc, 0);
                    continue;
                }

                // === Consultar IA ===
                String prompt = """
                        Respondé SOLO "SI" o "NO".
                        ¿La palabra "%s" pertenece a la categoría "%s"?
                        """.formatted(palabra, categoria.getNombre());

                String respuestaIA = openAI.consultarIA(prompt);

                if (respuestaIA == null) {
                    rc.setValida(false);
                    rc.setPuntaje(0);
                    rc.setMotivo("Error consultando IA");
                    resultadoRonda.agregarResultado(jugador, rc, 0);
                    continue;
                }

                respuestaIA = respuestaIA.trim().toUpperCase();

                if (!respuestaIA.contains("SI")) {
                    rc.setValida(false);
                    rc.setPuntaje(0);
                    rc.setMotivo("IA indica que NO pertenece a la categoría");
                    resultadoRonda.agregarResultado(jugador, rc, 0);
                    continue;
                }

                // === Valida por IA ===
                long apariciones = frecuencias.get(categoria).getOrDefault(palabraNorm, 1L);

                if (apariciones > 1) {
                    rc.setValida(true);
                    rc.setDuplicada(true);
                    rc.setPuntaje(puntajeDuplicado);
                    rc.setMotivo("Válida pero duplicada");
                } else {
                    rc.setValida(true);
                    rc.setDuplicada(false);
                    rc.setPuntaje(puntajeUnico);
                    rc.setMotivo("Válida y única");
                }

                logger.info("JuezIA - Jugador={} Categoria={} Palabra={} Valida={} Duplicada={} Puntaje={}",
                jugador.getNombreJugador(), categoria.getNombre(), palabra,
                rc.isValida(), rc.isDuplicada(), rc.getPuntaje());

                resultadoRonda.agregarResultado(jugador, rc, rc.getPuntaje());
            }
        }

        return resultadoRonda;
    }
}
