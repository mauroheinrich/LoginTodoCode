
package com.mauroheinrich.logintodocode.logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Rol implements Serializable {
    
    @Id
    //@GeneratedValue (strategy=GenerationType.AUTO)
    private int id;
    private String nombreRol;
    private String descripcion;
    @OneToMany (mappedBy="unRol")
    private List<Usuario>listadeusuarios;

    public Rol() {
    }

    public Rol(int id, String nombreRol, String descripcion) {
        this.id = id;
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Usuario> getListadeusuarios() {
        return listadeusuarios;
    }

    public void setListadeusuarios(List<Usuario> listadeusuarios) {
        this.listadeusuarios = listadeusuarios;
    }
    
    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}


