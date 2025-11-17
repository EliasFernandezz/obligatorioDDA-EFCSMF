/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.models;

/**
 *
 * @author 59898
 */
public class JugadorIndividual extends Jugador {
    
   
    private int duracionDeseada; 

    public JugadorIndividual(int id, String nombreJugador, int puntaje, int duracionPartida) {
        super(id, nombreJugador, puntaje);
        
        this.duracionDeseada = duracionPartida; 
    }
    
  

    public int getDuracionDeseada() {
        return duracionDeseada;
    }

    public void setDuracionDeseada(int duracionDeseada) {
        this.duracionDeseada = duracionDeseada;
    }
    
    public void finalizarPartida(){
      
    }
    
    public void iniciarPartida(){
    
    
    }
    
    
    
}
