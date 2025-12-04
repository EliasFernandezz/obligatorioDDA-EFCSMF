/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.models;

/**
 *
 * @author mateo
 */

import java.util.HashMap;
import java.util.Map;

public class ResultadoRonda {
    
    private final Map<Jugador, Map<ResultadoCategoria, Integer>> resultados = new HashMap<>();
    private final Map<Jugador, Integer> puntajeTotal = new HashMap<>();

    public void agregarResultado(Jugador jugador, ResultadoCategoria resultado, int puntaje) {
        resultados.computeIfAbsent(jugador, k -> new HashMap<>()).put(resultado, puntaje);
        puntajeTotal.put(jugador, puntajeTotal.getOrDefault(jugador, 0) + puntaje);
    }

    public int getPuntajeTotal(Jugador jugador) {
        return puntajeTotal.getOrDefault(jugador, 0);
    }

    public Map<Jugador, Map<ResultadoCategoria, Integer>> getResultados() {
        return resultados;
    }
}
