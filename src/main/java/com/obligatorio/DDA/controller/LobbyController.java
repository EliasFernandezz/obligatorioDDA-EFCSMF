/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.controller;

import com.obligatorio.DDA.Services.LobbyServiceSingularPlayer;
import com.obligatorio.DDA.Services.ServidorService;
import com.obligatorio.DDA.models.Categoria;
import com.obligatorio.DDA.models.Lobby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 59898
 */

@RestController
@RequestMapping("/lobby")
public class LobbyController {
    
    @Autowired
    private ServidorService servidorService;

    @Autowired
    private LobbyServiceSingularPlayer lobbyConfiguracionService;

    @PostMapping("/{id}/categoria")
    public ResponseEntity<?> agregarCategoria(
        @PathVariable int id,
        @RequestBody Categoria categoria) {

        Lobby lobby = servidorService.buscarLobby(id);
        lobbyConfiguracionService.agregarCategoria(lobby, categoria);

        return ResponseEntity.ok("Categoría agregada");
    }
    
}
