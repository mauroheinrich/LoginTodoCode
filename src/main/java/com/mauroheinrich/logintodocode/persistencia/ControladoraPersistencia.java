
package com.mauroheinrich.logintodocode.persistencia;

import com.mauroheinrich.logintodocode.logica.Rol;
import com.mauroheinrich.logintodocode.logica.Usuario;
import java.util.List;


public class ControladoraPersistencia {
    
    UsuarioJpaController usuJpa = new UsuarioJpaController();
    RolJpaController rolJpa = new RolJpaController();

    public List<Usuario> traerUsuarios() {
        return usuJpa.findUsuarioEntities();
    }

    public List<Rol> traerRoles() {
        return rolJpa.findRolEntities();
    }

    public void crearUsuario(Usuario usu) {
        usuJpa.create(usu);
    }
    
}
