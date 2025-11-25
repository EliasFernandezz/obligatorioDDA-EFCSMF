/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.Services;

import com.obligatorio.DDA.models.Categoria;
import com.obligatorio.DDA.models.Lobby;
import org.springframework.stereotype.Service;

/**
 *
 * @author 59898
 */

@Service
public class LobbyServiceSingularPlayer {
    
    public void iniciarPartida(Lobby lobby){
    
        lobby.setEstadoLobby(true);
        //logicaPartida pienso yo
    }
    
    public void cerrarPartida(Lobby lobby){
    
      lobby.setEstadoLobby(false);
        
    }
    
    public void modificarCantRondas(Lobby lobby, int cantRondas){
    
        lobby.setCantRondas(cantRondas);
    }
    
    
    public void modificarDuracionPartida(Lobby lobby, int duracionPartida){
    
        lobby.setDuracionPartida(duracionPartida);
    }
    
    
    public void modificarTiempoGracia(Lobby lobby, int tiempoGracia){
    
        lobby.setTiempoGracia(tiempoGracia);
    }
    
}
