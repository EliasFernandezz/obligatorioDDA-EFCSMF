/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.models;

/**
 *
 * @author 59898
 */
public class JugadorInvitado extends Jugador{
    
    private String codigoInvitacion;  

    public JugadorInvitado(int id, String nombreJugador, int puntaje, String codigoInvitacion) {
        super(id, nombreJugador, puntaje);
        
        this.codigoInvitacion = codigoInvitacion; 
    }
    
    
    
    public void unirseLobby(){
        //logica
    
    }

    public String getCodigoInvitacion() {
        return codigoInvitacion;
    }

    public void setCodigoInvitacion(String codigoInvitacion) {
        this.codigoInvitacion = codigoInvitacion;
    }
    
    
    
}
