/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.Services;

import com.obligatorio.DDA.models.Categoria;
import com.obligatorio.DDA.models.Lobby;
import com.obligatorio.DDA.repositoriosJPA.CategoriaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 59898
 */

@Service
public class ServidorService {
    
    private List<Lobby> lobbys = new ArrayList<>();
    
    private final CategoriaRepository categoriaRepository;
    private Lobby lobbySingularPlayer; 

    @Autowired
    public ServidorService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> obtenerCategoriasPredeterminadas() {
        return categoriaRepository.findAll();
    }
    
    public List<Categoria> obtenerCategoriasActivas() {
        return categoriaRepository.findByEstadoCategoriaTrue();
    }

    public List<Categoria> obtenerCategoriasDesactivadas() {
        return categoriaRepository.findByEstadoCategoriaFalse();
    }

    public Lobby crearLobby(Lobby lobby) {
        List<Categoria> categoriasPorDefecto = obtenerCategoriasPredeterminadas();
        lobby.setCategoriasSeleccionadas(categoriasPorDefecto);
        lobbys.add(lobby);
        return lobby;
    }

   

    public void eliminarLobby(int id) {
        lobbys.removeIf(lobby -> lobby.getId() == id);
    }
    
    public char sortearLetra() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return letras.charAt((int)(Math.random() * letras.length()));
    }

    public Lobby buscarLobbyUnicoLocalParaSingularPlayer() {
      if(lobbySingularPlayer == null){
        
            lobbySingularPlayer = new Lobby(); 
            lobbySingularPlayer.setCategoriasSeleccionadas(obtenerCategoriasPredeterminadas());
            
        }
        
        return lobbySingularPlayer; 
    }
    
    // se mantiene un lobby estatico para un jugadorsolo ya que jugara localmente en su maquina y no se necesita buscar mas de un lobby como en caso de
    // multijugador para diferenciar cada lobby a quien pertenece y que configuracion tiene el mismo sinmezclar un lobby con otro sabiendo que jugadores tiene cada lobby. 
    
    
    public Lobby buscarLobbyMultiplayer(int id) {
        return lobbys.stream()
                .filter(lobby -> lobby.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Lobby no encontrado"));
    }
    
    
}
