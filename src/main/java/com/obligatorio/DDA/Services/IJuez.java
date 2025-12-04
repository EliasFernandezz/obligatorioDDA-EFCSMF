/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.obligatorio.DDA.Services;

import com.obligatorio.DDA.models.Jugador;
import com.obligatorio.DDA.models.Respuesta;
import com.obligatorio.DDA.models.ResultadoRonda;
import java.util.Map;

/**
 *
 * @author mateo
 */
public interface IJuez {
    ResultadoRonda evaluar(Map<Jugador, Respuesta> respuestas);
}
