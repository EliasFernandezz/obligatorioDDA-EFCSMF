/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.models;

/**
 *
 * @author 59898
 */
public class Jugador {
    
    private int id; 
    private String nombreJugador; 
    private int puntaje; 

    public Jugador(int id, String nombreJugador, int puntaje) {
        this.id = id;
        this.nombreJugador = nombreJugador;
        this.puntaje = puntaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    
    
    public void rendirsePartida(){
      //logica
    
    }
    
    public void irseDelLobby(){
      //logica
    }
    
}
