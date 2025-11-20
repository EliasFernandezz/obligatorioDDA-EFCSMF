/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.controller;

import com.obligatorio.DDA.Services.LobbyServiceSingularPlayer;
import com.obligatorio.DDA.Services.ServidorService;
import com.obligatorio.DDA.models.Categoria;
import com.obligatorio.DDA.models.Lobby;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 59898
 */

@Controller
@RequestMapping("/lobby/ConfiguracionPartida")
public class LobbyController {
    
    @Autowired
    private ServidorService servidorService; // servicio en general que se encarga de asuntos mas globales como buscar lobby, sortear letra

    @Autowired //servicio para jugador singular que tiene metodos de agregar, eliminar y modificar caantidad de rondas
    // duracion partida, tiempoGracia
    private LobbyServiceSingularPlayer lobbyConfiguracionService;

    @GetMapping
    public String mostrarCategoriasLobby(Model model) {

        // Obtener categorías activas y desactivadas tambien
        List<Categoria> activas = servidorService.obtenerCategoriasActivas();
        List<Categoria> desactivadas = servidorService.obtenerCategoriasDesactivadas();

        // Pasarlas a la vista
        model.addAttribute("activas", activas);
        model.addAttribute("desactivadas", desactivadas); 
        
      


        return "lobby"; 
    }
    
    
}
