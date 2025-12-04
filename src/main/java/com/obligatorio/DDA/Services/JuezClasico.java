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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;



/**
 *
 * @author mateo
 */
public class JuezClasico implements IJuez {
    
  private static final Logger logger = LoggerFactory.getLogger(JuezClasico.class);

    @Value("${game.points.unique:100}")
    private int puntajeUnico;

    @Value("${game.points.duplicate:50}")
    private int puntajeDuplicado;

    @Override
    public ResultadoRonda evaluar(Map<Jugador, Respuesta> respuestas) {

        ResultadoRonda resultadoRonda = new ResultadoRonda();

        Map<Categoria, Map<String, Integer>> contador = new HashMap<>();

        // Contar apariciones
        for (Respuesta r : respuestas.values()) {
            for (Map.Entry<Categoria, String> e : r.getRespuestas().entrySet()) {

                String palabra = (e.getValue() == null) ? "" : e.getValue().trim().toUpperCase();
                Categoria categoria = e.getKey();

                contador.putIfAbsent(categoria, new HashMap<>());
                contador.get(categoria).merge(palabra, 1, Integer::sum);
            }
        }

        // Evaluación
        for (Map.Entry<Jugador, Respuesta> entry : respuestas.entrySet()) {

            Jugador jugador = entry.getKey();
            Respuesta rtaJugador = entry.getValue();

            for (Map.Entry<Categoria, String> r : rtaJugador.getRespuestas().entrySet()) {

                Categoria categoria = r.getKey();
                String palabra = r.getValue();
                String palabraNorm = (palabra == null) ? "" : palabra.trim().toUpperCase();

                ResultadoCategoria rc = new ResultadoCategoria(categoria, palabra);

                if (palabra == null || palabra.isBlank()) {
                    rc.setValida(false);
                    rc.setDuplicada(false);
                    rc.setPuntaje(0);
                    rc.setMotivo("Vacía");
                    resultadoRonda.agregarResultado(jugador, rc, 0);
                    continue;
                }

                int apariciones = contador.get(categoria).getOrDefault(palabraNorm, 1);

                if (apariciones > 1) {
                    rc.setValida(true);
                    rc.setDuplicada(true);
                    rc.setPuntaje(puntajeDuplicado);
                    rc.setMotivo("Duplicada");
                } else {
                    rc.setValida(true);
                    rc.setDuplicada(false);
                    rc.setPuntaje(puntajeUnico);
                    rc.setMotivo("Única");
                }

                logger.info("JuezClasico - Jugador={} Categoria={} Palabra={} Valida={} Duplicada={} Puntaje={}",
                        jugador.getNombreJugador(), categoria.getNombre(), palabra,
                        rc.isValida(), rc.isDuplicada(), rc.getPuntaje());

                resultadoRonda.agregarResultado(jugador, rc, rc.getPuntaje());
            }
        }

        return resultadoRonda;
    }
}
