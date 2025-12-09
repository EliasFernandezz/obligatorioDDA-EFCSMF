/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class Partida { 

    private Respuesta respuestaJugador;
    private HashMap<Categoria, String> categoriasRespuestas;

    private char letraSorteada;
    private int rondaActual;
    private int puntajeAcumulado; // ← PUNTAJE TOTAL ACUMULADO

    public Partida() {
        this.categoriasRespuestas = new HashMap<>();
        this.rondaActual = 1;
        this.puntajeAcumulado = 0;
    }

    public HashMap<Categoria, String> getCategoriasRespuestas() {
        return categoriasRespuestas;
    }

    public void setCategoriasRespuestas(HashMap<Categoria, String> categoriasRespuestas) {
        this.categoriasRespuestas = categoriasRespuestas;
    }

    public void agregarCategoriaYPalabra(Categoria categoria, String palabra) {
        this.categoriasRespuestas.put(categoria, palabra);
    }

    public void limpiarHashMap() {
        this.categoriasRespuestas.clear();
    }

    public char getLetraSorteada() {
        return letraSorteada;
    }

    public void setLetraSorteada(char letraSorteada) {
        this.letraSorteada = letraSorteada;
    }

    public int getRondaActual() {
        return rondaActual;
    }

    public void setRondaActual(int rondaActual) {
        this.rondaActual = rondaActual;
    }

    public Respuesta getRespuestaJugador() {
        return respuestaJugador;
    }

    public void setRespuestaJugador(Respuesta respuestaJugador) {
        this.respuestaJugador = respuestaJugador;
    }

    // -------------------------------
    //   NUEVA LÓGICA ACUMULADORA
    // -------------------------------
    public int getPuntajeAcumulado() {
        return puntajeAcumulado;
    }

    public void sumarPuntaje(int puntos) {
        this.puntajeAcumulado += puntos;
    }

    public void resetearPuntajes() {
        this.puntajeAcumulado = 0;
    }
}
