/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mauroheinrich.logintodocode.persistencia;

import com.mauroheinrich.logintodocode.logica.Rol;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mauroheinrich.logintodocode.logica.Usuario;
import com.mauroheinrich.logintodocode.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class RolJpaController implements Serializable {

    public RolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    public RolJpaController(){
        emf=Persistence.createEntityManagerFactory("loginPU");
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rol rol) {
        if (rol.getListadeusuarios() == null) {
            rol.setListadeusuarios(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedListadeusuarios = new ArrayList<Usuario>();
            for (Usuario listadeusuariosUsuarioToAttach : rol.getListadeusuarios()) {
                listadeusuariosUsuarioToAttach = em.getReference(listadeusuariosUsuarioToAttach.getClass(), listadeusuariosUsuarioToAttach.getId());
                attachedListadeusuarios.add(listadeusuariosUsuarioToAttach);
            }
            rol.setListadeusuarios(attachedListadeusuarios);
            em.persist(rol);
            for (Usuario listadeusuariosUsuario : rol.getListadeusuarios()) {
                Rol oldUnRolOfListadeusuariosUsuario = listadeusuariosUsuario.getUnRol();
                listadeusuariosUsuario.setUnRol(rol);
                listadeusuariosUsuario = em.merge(listadeusuariosUsuario);
                if (oldUnRolOfListadeusuariosUsuario != null) {
                    oldUnRolOfListadeusuariosUsuario.getListadeusuarios().remove(listadeusuariosUsuario);
                    oldUnRolOfListadeusuariosUsuario = em.merge(oldUnRolOfListadeusuariosUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getId());
            List<Usuario> listadeusuariosOld = persistentRol.getListadeusuarios();
            List<Usuario> listadeusuariosNew = rol.getListadeusuarios();
            List<Usuario> attachedListadeusuariosNew = new ArrayList<Usuario>();
            for (Usuario listadeusuariosNewUsuarioToAttach : listadeusuariosNew) {
                listadeusuariosNewUsuarioToAttach = em.getReference(listadeusuariosNewUsuarioToAttach.getClass(), listadeusuariosNewUsuarioToAttach.getId());
                attachedListadeusuariosNew.add(listadeusuariosNewUsuarioToAttach);
            }
            listadeusuariosNew = attachedListadeusuariosNew;
            rol.setListadeusuarios(listadeusuariosNew);
            rol = em.merge(rol);
            for (Usuario listadeusuariosOldUsuario : listadeusuariosOld) {
                if (!listadeusuariosNew.contains(listadeusuariosOldUsuario)) {
                    listadeusuariosOldUsuario.setUnRol(null);
                    listadeusuariosOldUsuario = em.merge(listadeusuariosOldUsuario);
                }
            }
            for (Usuario listadeusuariosNewUsuario : listadeusuariosNew) {
                if (!listadeusuariosOld.contains(listadeusuariosNewUsuario)) {
                    Rol oldUnRolOfListadeusuariosNewUsuario = listadeusuariosNewUsuario.getUnRol();
                    listadeusuariosNewUsuario.setUnRol(rol);
                    listadeusuariosNewUsuario = em.merge(listadeusuariosNewUsuario);
                    if (oldUnRolOfListadeusuariosNewUsuario != null && !oldUnRolOfListadeusuariosNewUsuario.equals(rol)) {
                        oldUnRolOfListadeusuariosNewUsuario.getListadeusuarios().remove(listadeusuariosNewUsuario);
                        oldUnRolOfListadeusuariosNewUsuario = em.merge(oldUnRolOfListadeusuariosNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = rol.getId();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<Usuario> listadeusuarios = rol.getListadeusuarios();
            for (Usuario listadeusuariosUsuario : listadeusuarios) {
                listadeusuariosUsuario.setUnRol(null);
                listadeusuariosUsuario = em.merge(listadeusuariosUsuario);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Rol findRol(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
