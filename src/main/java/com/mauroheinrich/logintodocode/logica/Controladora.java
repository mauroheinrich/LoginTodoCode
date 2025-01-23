
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

    public List<Usuario> traerUsuarios(){
        
        return controlPersis.traerUsuarios();
        
    }

    public List<Rol> traerRoles() {
        return controlPersis.traerRoles();
    }

    public void crearUsuario(String usuario, String contra, String rolRecibido) {
        Usuario usu = new Usuario();
        usu.setNombreUsuario(usuario);
        usu.setContrasenia(contra);
        
        Rol rolEncontrado = new Rol();
        rolEncontrado = this.traerRol(rolRecibido);
        if(rolEncontrado!=null){
             usu.setUnRol(rolEncontrado);
        }
        controlPersis.crearUsuario(usu);
    }

    private Rol traerRol(String rolRecibido) {
       List<Rol> listaRoles = controlPersis.traerRoles();
       
       
       for (Rol rol:listaRoles){
           if (rol.getNombreRol().equals(rolRecibido)){
               return rol;
           }
       
       }
       return null;
    }
}
