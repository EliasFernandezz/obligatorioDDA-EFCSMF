/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 59898
 */
public class Lobby {
    
    private int id; 
    private String codigoInvitacion; 
    private List<Categoria> categoriasSeleccionadas = new ArrayList<>(); 
    private int duracionPartida; 
    private int tiempoGracia; 
    private boolean estadoLobby; 
    private Jugador jugador; 
    private int cantRondas;
    
      private Partida partidaActual;

    public Lobby(int id, String codigoInvitacion, int duracionPartida, int tiempoGracia, boolean estadoLobby, Jugador jugador, int cantRondas) {
        this.id = id;
        this.codigoInvitacion = codigoInvitacion;
        this.duracionPartida = duracionPartida;
        this.tiempoGracia = tiempoGracia;
        this.estadoLobby = estadoLobby;
        this.jugador = jugador;
        this.cantRondas = cantRondas;
        
        // Inicializamos la partida actual
        this.partidaActual = new Partida();
    }
    
    public Lobby(){
    this.partidaActual = new Partida();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoInvitacion() {
        return codigoInvitacion;
    }

    public void setCodigoInvitacion(String codigoInvitacion) {
        this.codigoInvitacion = codigoInvitacion;
    }

    public List<Categoria> getCategoriasSeleccionadas() {
        return categoriasSeleccionadas;
    }

    public void setCategoriasSeleccionadas(List<Categoria> categoriasSeleccionadas) {
        this.categoriasSeleccionadas = categoriasSeleccionadas;
    }

    public int getDuracionPartida() {
        return duracionPartida;
    }

    public void setDuracionPartida(int duracionPartida) {
        this.duracionPartida = duracionPartida;
    }

    public int getTiempoGracia() {
        return tiempoGracia;
    }

    public void setTiempoGracia(int tiempoGracia) {
        this.tiempoGracia = tiempoGracia;
    }

    public boolean isEstadoLobby() {
        return estadoLobby;
    }

    public void setEstadoLobby(boolean estadoLobby) {
        this.estadoLobby = estadoLobby;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public int getCantRondas() {
        return cantRondas;
    }

    public void setCantRondas(int cantRondas) {
        this.cantRondas = cantRondas;
    }
    
    
    public Partida getPartidaActual() {
        return partidaActual;
    }

    public void setPartidaActual(Partida partidaActual) {
        this.partidaActual = partidaActual;
    }
    
    
    
    public void unirseAlLobby(Jugador jugador){
    
    
    }
    
    public void salirDelLobby(Jugador jugador){
    
    }
    
    
}
