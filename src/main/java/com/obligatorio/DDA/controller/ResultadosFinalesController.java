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

        // obtener lobby local del jugador (tu método actual)
        Lobby lobby = servidorService.buscarLobbyUnicoLocalParaSingularPlayer();
        Partida partida = lobby.getPartidaActual();

        // obtener jugadores de la partida
        //List<Jugador> jugadores = lobby.getJugadores();  COMENTADO POR SI SE LLEGA A USAR DESPUES
        // si tenés un método que calcula puntajes finales por jugador:
        //Map<Jugador, Integer> puntajesFinales = partida.calcularPuntajesFinales();  COMENTADO POR SI SE LLEGA A USAR DESPUES
        // agregar datos al modelo
        //model.addAttribute("jugadores", jugadores);
        //model.addAttribute("puntajesFinales", puntajesFinales);
        return "resultadosFinales"; // nombre de la template
    }

    @GetMapping("/volverAlMenuDesdeFinal")
    public String volverAlMenuDesdeFinal() {

        Lobby lobby = servidorService.buscarLobbyUnicoLocalParaSingularPlayer();
        Partida partida = lobby.getPartidaActual();

        //partida.resetearPuntajes(); // si llevás puntaje acumulado (opcional)
        return "redirect:/";
    }
}
