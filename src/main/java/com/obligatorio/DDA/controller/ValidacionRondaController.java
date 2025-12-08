package com.obligatorio.DDA.controller;

import com.obligatorio.DDA.Services.IJuez;
import com.obligatorio.DDA.Services.ServidorService;
import com.obligatorio.DDA.models.Categoria;
import com.obligatorio.DDA.models.Jugador;
import com.obligatorio.DDA.models.Lobby;
import com.obligatorio.DDA.models.Partida;
import com.obligatorio.DDA.models.Respuesta;
import com.obligatorio.DDA.models.ResultadoCategoria;
import com.obligatorio.DDA.models.ResultadoRonda;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
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

    @Autowired
    private IJuez juez;   // <<--- SE AGREGA EL JUEZ !!!

    @GetMapping("/validacionRonda")
public String mostrarValidacion(Model model, HttpSession session) {

    Lobby lobby = servidorService.buscarLobbyUnicoLocalParaSingularPlayer();
    Partida partida = lobby.getPartidaActual();
    Jugador jugadorActual = lobby.getJugador();

    if (jugadorActual == null) {
        throw new IllegalStateException("El lobby no tiene jugador asignado.");
    }

    // Obtener respuestas del jugador
    Respuesta respuestaJugador = partida.getRespuestaJugador();
    if (respuestaJugador == null) {
        throw new IllegalStateException("No hay respuestas del jugador en esta ronda.");
    }

    Map<Categoria, String> mapaRespuestas = respuestaJugador.getRespuestas();

    // 1) Recuperar cache de IA
    ResultadoRonda resultado = (ResultadoRonda) session.getAttribute("resultadoIA");

    // 2) Si no existe → llamar IA SOLO UNA VEZ
    if (resultado == null) {

        Map<Jugador, Respuesta> respuestas = new HashMap<>();
        respuestas.put(jugadorActual, respuestaJugador);

        System.out.println("Llamando a la IA por PRIMERA VEZ...");

        resultado = juez.evaluar(respuestas);

        // Guardarlo para no volver a llamar
        session.setAttribute("resultadoIA", resultado);
    }

    // 3) Obtener resultados del jugador
    Map<ResultadoCategoria, Integer> resultadosJugador =
            resultado.getResultados().get(jugadorActual);

    int puntajeTotal = resultado.getPuntajeTotal(jugadorActual);

    // 4) Enviar a la vista
    model.addAttribute("letra", partida.getLetraSorteada());
    model.addAttribute("pares", mapaRespuestas.entrySet());
    model.addAttribute("resultadosJugador", resultadosJugador);
    model.addAttribute("puntajeTotal", puntajeTotal);

    return "validacionRonda";
}


    @GetMapping("/siguienteRonda")
    public String siguienteRonda(HttpSession session) {
        
         session.removeAttribute("resultadoIA");
         
        Lobby lobby = servidorService.buscarLobbyUnicoLocalParaSingularPlayer();
        Partida partida = lobby.getPartidaActual();

        int totalRondas = lobby.getCantRondas();

        if (partida.getRondaActual() < totalRondas) {
            partida.setRondaActual(partida.getRondaActual() + 1);
            return "redirect:/partida";
        } else {
            partida.setRondaActual(1);
            return "redirect:/resultadosFinales";
        }
    }
}
