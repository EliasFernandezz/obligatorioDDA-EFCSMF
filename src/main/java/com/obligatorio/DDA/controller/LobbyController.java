/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.controller;

import com.obligatorio.DDA.Services.LobbyServiceSingularPlayer;
import com.obligatorio.DDA.Services.ServidorService;
import com.obligatorio.DDA.models.Categoria;
import com.obligatorio.DDA.models.Lobby;
import com.obligatorio.DDA.repositoriosJPA.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 59898
 */

@Controller
@RequestMapping("/lobby/ConfiguracionPartida")
public class LobbyController {
    
    @Autowired
    private ServidorService servidorService; // servicio en general que se encarga de asuntos mas globales como buscar lobby, sortear letra

    @Autowired //servicio para jugador singular que tiene metodos de agregar, eliminar y modificar caantidad de rondas
    // duracion partida, tiempoGracia
    private LobbyServiceSingularPlayer lobbyConfiguracionService;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    

    @GetMapping
    public String mostrarCategoriasLobby(Model model) {

        // Obtener categorías activas y desactivadas tambien
        List<Categoria> activas = servidorService.obtenerCategoriasActivas();
        List<Categoria> desactivadas = servidorService.obtenerCategoriasDesactivadas();

        // Pasarlas a la vista
        model.addAttribute("activas", activas);
        model.addAttribute("desactivadas", desactivadas); 

        return "lobby"; 
    }
    
    @GetMapping("/alternarEstadoCategoria/{id}")
    public String alternarEstadoCategoria(@PathVariable int id) {
        //busca la categoria que eligio elusuario y de esa categoria se usa el  idpara ubicarla y devuelve la categoria elegida y en caso de que no exista la categoria devuelve categoria no encontrada(esto por las dudas pero las categoriasya estan en labase de datos)
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Cambiar estado (true → false, false → true) si se encuentra la categoria
        categoria.setEstadoCategoria(!categoria.estadoCategoria());

        categoriaRepository.save(categoria);

        
        
        
        return "redirect:/lobby/ConfiguracionPartida";
    }
    
    
    
    //Explicacion; en guardar configuracion se usan los metodos del servicio singular player ya que ese servicio tiene los metodos d los datos numericos
    // como cantidad de rondas, duracion etc  las categorias no precisa el mismo submit que tendra el metodo de guardarConfiguracion
    // esto por que las cateorias ya se guardan en la base de datos
    
    
    
    @PostMapping("/guardarConfiguracion")
    public String guardarConfiguracion(@RequestParam int idLobby, @RequestParam int cantRondas){
        
        Lobby lobby = servidorService.buscarLobby(idLobby);
        
        
        lobbyConfiguracionService.modificarCantRondas(lobby, cantRondas);
        
        
        return "redirect:/lobby/ConfiguracionPartida";
                
                
    }
    
    
}
