/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.controller;

import com.obligatorio.DDA.Services.ServidorService;
import com.obligatorio.DDA.models.Categoria;
import com.obligatorio.DDA.models.Jugador;
import com.obligatorio.DDA.models.Lobby;
import com.obligatorio.DDA.models.Partida;
import com.obligatorio.DDA.models.Respuesta;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        
        if (lobby.getJugador() == null) {
        Jugador jugador = new Jugador();
        jugador.setNombreJugador("Jugador Local"); // luego lo cambiás por nombre ingresado desde vista
        lobby.setJugador(jugador);
}

        // Obtener solo categorías activas
        List<Categoria> categorias = servidorService.obtenerCategoriasActivas();

        // Sortear letra
        char letra = servidorService.sortearLetra();
        
        

        // guardar letra en partidaActual
        lobby.getPartidaActual().setLetraSorteada(letra);

        // Enviar datos a la vista
        model.addAttribute("lobby", lobby);
        model.addAttribute("categorias", categorias);
        model.addAttribute("letra", letra);

        return "partida";
    }

    @PostMapping("/finalizarRonda")
    public String finalizarRonda(@RequestParam Map<String, String> datosFormulario, Model model) {

      Lobby lobby = servidorService.buscarLobbyUnicoLocalParaSingularPlayer();
    Partida partida = lobby.getPartidaActual();
    partida.limpiarHashMap();

    char letraSorteada = partida.getLetraSorteada();

    String accion = datosFormulario.get("accion");

    Respuesta respuestaJugador = new Respuesta();

    for (String nombreCategoria : datosFormulario.keySet()) {

        if (nombreCategoria.equals("accion")) {
            continue;
        }

        Categoria categoria = servidorService.obtenerCategoriasPredeterminadas()
                .stream()
                .filter(c -> c.getNombre().equals(nombreCategoria))
                .findFirst()
                .orElse(null);

        if (categoria != null) {

            String valor = datosFormulario.get(nombreCategoria);

            if (valor == null || valor.trim().isEmpty()) {
                valor = String.valueOf(letraSorteada);
            }

            respuestaJugador.agregarRespuesta(categoria, valor);
            partida.agregarCategoriaYPalabra(categoria, valor);
        }
    }

    partida.setRespuestaJugador(respuestaJugador);

    return "redirect:/validacionRonda";
    }
}
