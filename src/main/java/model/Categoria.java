/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author mateo
 */


public class Categoria {
    private String Nombre;
    private boolean EstadoCategoria;

    public Categoria(String Nombre, boolean EstadoCategoria) {
        this.Nombre = Nombre;
        this.EstadoCategoria = EstadoCategoria;
    }

    public String getNombre() {
        return Nombre;
    }

    public boolean getEstadoCategoria() {
        return EstadoCategoria;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setEstadoCategoria(boolean b) {
        this.EstadoCategoria = b;
    }
    
    
}
