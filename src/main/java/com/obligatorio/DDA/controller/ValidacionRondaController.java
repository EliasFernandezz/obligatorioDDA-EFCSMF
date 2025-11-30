package com.obligatorio.DDA.controller;

import com.obligatorio.DDA.Services.ServidorService;
import com.obligatorio.DDA.models.Categoria;
import com.obligatorio.DDA.models.Lobby;
import com.obligatorio.DDA.models.Partida;
import com.obligatorio.DDA.models.Respuesta;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ValidacionRondaController {

    @Autowired
    private ServidorService servidorService;

    @GetMapping("/validacionRonda")
    public String mostrarValidacion(Model model) {

        Lobby lobby = servidorService.buscarLobbyUnicoLocalParaSingularPlayer();
        Partida partida = lobby.getPartidaActual();

        Map<Categoria, Respuesta> map = partida.getCategoriasRespuestas();

        model.addAttribute("letra", partida.getLetraSorteada());
        model.addAttribute("pares", map.entrySet());

        return "validacionRonda";
    }

    @GetMapping("/siguienteRonda")
    public String siguienteRonda() {

        Lobby lobby = servidorService.buscarLobbyUnicoLocalParaSingularPlayer();
        Partida partida = lobby.getPartidaActual();

        int totalRondas = lobby.getCantRondas();

        //si todavía quedan rondas, volver a página de partida
        if (partida.getRondaActual() < totalRondas) {
            partida.setRondaActual(partida.getRondaActual() + 1);
            return "redirect:/partida";
        } else {
            //si se terminaron las rondas → reiniciar contador de rondas e ir a resultados finales
            partida.setRondaActual(1);
            return "redirect:/resultadosFinales";
        }
    }
}
