/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.models;

/**
 *
 * @author 59898
 */
public class JugadorHost extends JugadorIndividual{
    
    private String codigoPartida;  

    public JugadorHost(int id, String nombreJugador, int puntaje, int duracionPartida, String codigoPartida) {
        super(id, nombreJugador, puntaje, duracionPartida);
        
        this.codigoPartida = codigoPartida; 
    }
  
   
    
    public void invitar(){
    
    
    }
    
    public void crearCodigoInvitacion(){
    
    }

    public String getCodigoPartida() {
        return codigoPartida;
    }

    public void setCodigoPartida(String codigoPartida) {
        this.codigoPartida = codigoPartida;
    }
    
    
    
}
