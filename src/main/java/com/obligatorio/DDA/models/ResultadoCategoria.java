/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.models;

/**
 *
 * @author mateo
 */



public class ResultadoCategoria {
    
    private final Categoria categoria;
    private final String palabra;
    private boolean valida;
    private boolean duplicada;
    private int puntaje;
    private String motivo;
    
     public ResultadoCategoria(Categoria categoria, String palabra) {
        this.categoria = categoria;
        this.palabra = palabra;
    }

  

    public Categoria getCategoria() { return categoria; }
    public String getPalabra() { return palabra; }
    public boolean isValida() { return valida; }
    public boolean isDuplicada() { return duplicada; }
    public int getPuntaje() { return puntaje; }
    public String getMotivo() { return motivo; }

    public void setValida(boolean valida) { this.valida = valida; }
    public void setDuplicada(boolean duplicada) { this.duplicada = duplicada; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
