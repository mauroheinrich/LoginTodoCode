
package com.mauroheinrich.logintodocode.persistencia;

import com.mauroheinrich.logintodocode.logica.Usuario;
import java.util.List;


public class ControladoraPersistencia {
    
    UsuarioJpaController usuJpa = new UsuarioJpaController();

    public List<Usuario> traerUsuarios() {
        return usuJpa.findUsuarioEntities();
    }
    
}
