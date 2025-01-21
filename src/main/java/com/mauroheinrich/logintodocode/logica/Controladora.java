
package com.mauroheinrich.logintodocode.logica;

import com.mauroheinrich.logintodocode.persistencia.ControladoraPersistencia;
import java.util.List;


public class Controladora {
    
    ControladoraPersistencia controlPersis;// = new ControladoraPersistencia();
    

    public Controladora() {
        controlPersis = new ControladoraPersistencia();
    }
    
    public Usuario validarUsuario(String usuario, String contrasenia) {
        
        //String  mensaje ="";
        Usuario usr = null; 
        List <Usuario> listaUsuarios = controlPersis.traerUsuarios();
        
        for(Usuario usu: listaUsuarios){
            if(usu.getNombreUsuario().equals(usuario)){
                if(usu.getContrasenia().equals(contrasenia)){
                  //  mensaje = "Usuario y contraseña correcto. Bienvenido/a!!";
                  usr = usu;
                  return usr;
                }
                else{
                   // mensaje = "Contraseña incorrecta. Intente nuevamente!";
                  
                }
            }
            else{
               // mensaje = "Usuario invalido";
                    usr = null;
                   
              
            }
        }
        return usr;
    }

    
}
