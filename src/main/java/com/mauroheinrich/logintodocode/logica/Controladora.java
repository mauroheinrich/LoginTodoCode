
package com.mauroheinrich.logintodocode.logica;

import com.mauroheinrich.logintodocode.persistencia.ControladoraPersistencia;
import java.util.List;


public class Controladora {
    
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();

    public String validarUsuario(String usuario, String contrasenia) {
        
        String  mensaje ="";
        List <Usuario> listaUsuarios = controlPersis.traerUsuarios();
        
        for(Usuario usu: listaUsuarios){
            if(usu.getNombreUsuario().equals(usuario)){
                if(usu.getContrasenia().equals(contrasenia)){
                    mensaje = "Usuario y contraseña correcto. Bienvenido/a!!";
                }
                else{
                    mensaje = "Contraseña incorrecta. Intente nuevamente!";
                }
            }
            else{
                mensaje = "Usuario invalido";
            }
        }
        return mensaje;
    }
}
