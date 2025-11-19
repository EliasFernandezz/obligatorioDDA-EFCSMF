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
    

    @Autowired
    public ServidorService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> obtenerCategoriasPredeterminadas() {
        return categoriaRepository.findAll();
    }

    public Lobby crearLobby(Lobby lobby) {
        List<Categoria> categoriasPorDefecto = obtenerCategoriasPredeterminadas();
        lobby.setCategoriasSeleccionadas(categoriasPorDefecto);
        lobbys.add(lobby);
        return lobby;
    }

    public Lobby buscarLobby(int id) {
        return lobbys.stream()
                .filter(lobby -> lobby.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Lobby no encontrado"));
    }

    public void eliminarLobby(int id) {
        lobbys.removeIf(lobby -> lobby.getId() == id);
    }
    
    public char sortearLetra() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return letras.charAt((int)(Math.random() * letras.length()));
    }
    
}
