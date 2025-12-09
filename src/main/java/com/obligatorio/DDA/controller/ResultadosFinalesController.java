package com.obligatorio.DDA.controller;

import com.obligatorio.DDA.Services.ServidorService;
import com.obligatorio.DDA.models.Jugador;
import com.obligatorio.DDA.models.Lobby;
import com.obligatorio.DDA.models.Partida;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResultadosFinalesController {

    @Autowired
    private ServidorService servidorService;

    @GetMapping("/resultadosFinales")
    public String mostrarResultadosFinales(Model model) {

        Lobby lobby = servidorService.buscarLobbyUnicoLocalParaSingularPlayer();
        Partida partida = lobby.getPartidaActual();

        int puntajeFinal = partida.getPuntajeAcumulado();

        model.addAttribute("puntajeFinal", puntajeFinal);

        return "resultadosFinales";
    }

    @GetMapping("/volverAlMenuDesdeFinal")
    public String volverAlMenuDesdeFinal() {

        Lobby lobby = servidorService.buscarLobbyUnicoLocalParaSingularPlayer();
        Partida partida = lobby.getPartidaActual();

        partida.resetearPuntajes(); // opcional

        return "redirect:/";
    }
}
