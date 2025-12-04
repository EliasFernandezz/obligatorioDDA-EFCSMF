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

public class Respuesta {
    
      private final Map<Categoria, String> respuestasPorCategoria = new HashMap<>();

    public void agregarRespuesta(Categoria categoria, String palabra) {
        respuestasPorCategoria.put(categoria, palabra);
    }

    public String getRespuesta(Categoria categoria) {
        return respuestasPorCategoria.get(categoria);
    }

    public Map<Categoria, String> getRespuestas() {
        return respuestasPorCategoria;
    }
}
