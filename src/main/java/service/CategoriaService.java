/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author mateo
 */

import java.util.List;
import java.util.Optional;
import model.Categoria;
import org.springframework.stereotype.Service;
import repository.CategoriaRepository;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    
     public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
     
     public Categoria crearCategoria(Categoria categoria) {
        // Aquí puedes agregar reglas adicionales de validación si lo deseas
        categoria.setEstadoCategoria(true); // Por defecto activa al crear
        return categoriaRepository.save(categoria);
    }
     
      public Categoria obtenerPorId(Long id) {
        Optional<Categoria> optCategoria = categoriaRepository.findById(id);
        return optCategoria.orElse(null);
    }
      
       public Categoria actualizarCategoria(Categoria categoria) {
        // Se asume que la categoría existe (debería validarse antes)
        return categoriaRepository.save(categoria);
    }
       
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
    
    public List<Categoria> listarCategoriasActivas() {
        return categoriaRepository.findByActivaTrue();
    }
      
}
