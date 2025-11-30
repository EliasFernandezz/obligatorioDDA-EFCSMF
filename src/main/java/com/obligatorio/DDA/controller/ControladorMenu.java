/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Usuario
 */
@Controller
public class ControladorMenu {
    @GetMapping("/menu")
    public String comienzo(Model modelo) {

        return "menu";
    }
    
    @GetMapping("/lobby/menu")
    public String mostrarLobby(){
    
        return "lobby"; 
    
    }
}
