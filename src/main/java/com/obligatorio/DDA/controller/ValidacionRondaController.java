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
    private IJuez juez;

    @GetMapping("/validacionRonda")
    public String mostrarValidacion(Model model, HttpSession session) {

        Lobby lobby = servidorService.buscarLobbyUnicoLocalParaSingularPlayer();
        Partida partida = lobby.getPartidaActual();
        Jugador jugadorActual = lobby.getJugador();

        if (jugadorActual == null) {
            model.addAttribute("errorMensaje", "No hay jugador asignado al lobby.");
            return "validacionRonda";
        }

        // Obtener respuestas del jugador
        Respuesta respuestaJugador = partida.getRespuestaJugador();
        if (respuestaJugador == null) {
            model.addAttribute("errorMensaje", "No se encontraron respuestas para esta ronda.");
            return "validacionRonda";
        }

        Map<Categoria, String> mapaRespuestas = respuestaJugador.getRespuestas();

        // 1) Recuperar cache de IA
        ResultadoRonda resultado = (ResultadoRonda) session.getAttribute("resultadoIA");

        // 2) Si no existe => llamar IA solo una vez
        if (resultado == null) {

            Map<Jugador, Respuesta> respuestas = new HashMap<>();
            respuestas.put(jugadorActual, respuestaJugador);

            try {
                System.out.println("Llamando a la IA por PRIMERA VEZ...");
                resultado = juez.evaluar(respuestas);

                // Guardarlo en sesión
                session.setAttribute("resultadoIA", resultado);

            } catch (Exception e) {
                model.addAttribute("errorMensaje", "El juez no pudo validar la ronda.");
                return "validacionRonda";
            }
        }

        // 3) Obtener resultados del jugador
        Map<ResultadoCategoria, Integer> resultadosJugador =
                resultado.getResultados().get(jugadorActual);

        int puntajeTotalRonda = resultado.getPuntajeTotal(jugadorActual);

        // 4) Sumarlo al puntaje acumulado de la partida
        partida.sumarPuntaje(puntajeTotalRonda);

        // 5) Enviar datos a la vista
        model.addAttribute("letra", partida.getLetraSorteada());
        model.addAttribute("pares", mapaRespuestas.entrySet());
        model.addAttribute("resultadosJugador", resultadosJugador);
        model.addAttribute("puntajeTotal", puntajeTotalRonda);

        // Si querés mostrar en la misma vista el acumulado:
        model.addAttribute("puntajeAcumulado", partida.getPuntajeAcumulado());

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
