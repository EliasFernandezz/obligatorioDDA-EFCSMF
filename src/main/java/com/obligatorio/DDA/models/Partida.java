/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.models;

import java.util.HashMap;

/**
 *
 * @author Usuario
 */
public class Partida {

    private HashMap<Categoria, Respuesta> categoriasRespuestas;
    private char letraSorteada;
    private int rondaActual;
    private int puntajeAcumulado; //asumo que se implementa en conjunto con la IA

    public Partida() {
        this.categoriasRespuestas = new HashMap<>();
        this.rondaActual = 1;
    }

    public HashMap<Categoria, Respuesta> getCategoriasRespuestas() {
        return categoriasRespuestas;
    }

    public void setCategoriasRespuestas(HashMap<Categoria, Respuesta> categoriasRespuestas) {
        this.categoriasRespuestas = categoriasRespuestas;
    }

    public void agregarCategoriaRespuesta(Categoria categoria, Respuesta respuesta) {
        this.categoriasRespuestas.put(categoria, respuesta);
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
    
    
}
