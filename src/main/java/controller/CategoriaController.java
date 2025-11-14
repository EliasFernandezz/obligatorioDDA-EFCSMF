/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author mateo
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import model.Categoria;
import org.springframework.stereotype.Controller;
import service.CategoriaService;

@Controller
@RequestMapping("/api/categorias")

public class CategoriaController {
    private final CategoriaService categoriaService;
         
    
    
    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
    this.categoriaService = categoriaService;
    }

    @GetMapping("/GestionCategorias")
    public String mostrarCategorias()
    {
        return "CategoriaView";
    }
    
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
        return ResponseEntity.badRequest().body("El nombre de categoría es obligatorio y no puede estar vacío.");
    }   
    Categoria creada = categoriaService.crearCategoria(categoria);
    return ResponseEntity.ok(creada);
    }
        
    // RF1.2 - Modificar categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Categoria existente = categoriaService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre de categoría es obligatorio y no puede estar vacío.");
        }
        existente.setNombre(categoria.getNombre());
        existente.setEstadoCategoria(categoria.getEstadoCategoria());
        Categoria modificada = categoriaService.actualizarCategoria(existente);
        return ResponseEntity.ok(modificada);
    }
    // RF1.3 - Eliminar categoría no deseada antes de iniciar partida
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        Categoria existente = categoriaService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok().build();
    }
    // RF1.4 - Mostrar la lista de categorías activas disponibles para el juego
    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategoriasActivas() {
        List<Categoria> categorias = categoriaService.listarCategoriasActivas();
        return ResponseEntity.ok(categorias);
    }
    
    
}
