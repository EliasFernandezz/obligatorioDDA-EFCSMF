/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.obligatorio.DDA.repositoriosJPA;

/**
 *
 * @author 59898
 */
import com.obligatorio.DDA.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    // Spring genera automáticamente este método
   

    List<Categoria> findByEstadoCategoriaTrue();
    List<Categoria> findByEstadoCategoriaFalse();

}
