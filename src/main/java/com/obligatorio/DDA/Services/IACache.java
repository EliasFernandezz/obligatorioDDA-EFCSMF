/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.Services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author mateo
 */
@Service
public class IACache  {
      private final Map<String, String> cache = new ConcurrentHashMap<>();

    public String obtener(String categoria, String palabra) {
        return cache.get(clave(categoria, palabra));
    }

    public void guardar(String categoria, String palabra, String respuestaIA) {
        cache.put(clave(categoria, palabra), respuestaIA);
    }

    private String clave(String categoria, String palabra) {
        return categoria.trim().toUpperCase() + "::" + palabra.trim().toUpperCase();
    }
}
