/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.controller;

import com.obligatorio.DDA.Services.ServidorService;
import com.obligatorio.DDA.models.Categoria;
import com.obligatorio.DDA.models.Lobby;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Usuario
 */
@Controller
public class PartidaController {

    @Autowired
    private ServidorService servidorService;

    @GetMapping("/partida")
    public String mostrarPartida(Model model) {

        // Obtener lobby actual
        Lobby lobby = servidorService.buscarLobbyUnicoLocalParaSingularPlayer();

        // Obtener solo categorías activas
        List<Categoria> categorias = servidorService.obtenerCategoriasActivas();

        // Sortear letra
        char letra = servidorService.sortearLetra();

        // Enviar datos a la vista
        model.addAttribute("lobby", lobby);
        model.addAttribute("categorias", categorias);
        model.addAttribute("letra", letra);

        return "partida";
    }
}
