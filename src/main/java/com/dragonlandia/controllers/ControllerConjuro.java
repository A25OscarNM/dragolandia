package com.dragonlandia.controllers;

import java.util.List;

import com.dragonlandia.modelo.Conjuro;
import com.dragonlandia.modelo.Hechizo;
import com.dragonlandia.modelo.Mago;
import com.dragonlandia.vista.EmFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ControllerConjuro {
    private static EntityManager gestorEntidades;
    private static EntityTransaction gestorTransaction;

    public ControllerConjuro() {
        gestorEntidades = EmFactory.getEntityManager();
        gestorTransaction = gestorEntidades.getTransaction();
    }

    /** 
     * @param mago
     * @param hechizo
     */
    // CREATE
    public void crearConjuro(Mago mago, Hechizo hechizo) {
        gestorTransaction.begin();
        Conjuro conjuro = new Conjuro(0, mago, hechizo);
        gestorEntidades.persist(conjuro);

        gestorTransaction.commit();
    }

    /** 
     * @param id
     * @return Conjuro
     */
    // SELECT
    public Conjuro getConjuro(int id) {
        return gestorEntidades.find(Conjuro.class, id);
    }
    
    /** 
     * @param idMago
     * @return List<Hechizo>
     */
    public List<Hechizo> getHechizosPorMago(int idMago) {
        return gestorEntidades.createQuery(
            "SELECT c.hechizo FROM Conjuro c WHERE c.mago.id = :idMago", Hechizo.class)
            .setParameter("idMago", idMago)
            .getResultList();
    }

    /** 
     * @param conjuro
     */
    // UPDATE
    public void modificarConjuro(Conjuro conjuro) {
        gestorTransaction.begin();
        gestorEntidades.merge(conjuro);
        gestorTransaction.commit();
    }
    /** 
     * @param id
     */
    // DELETE
    public void eliminarConjuro(int id) {
        gestorTransaction.begin();
        Conjuro conjuro = gestorEntidades.find(Conjuro.class, id);
        if (conjuro != null) gestorEntidades.remove(conjuro);
        gestorTransaction.commit();
    }

    /** 
     * @param idMago
     */
    public void borrarConjurosPorMago(int idMago) {
        gestorTransaction.begin();
        gestorEntidades.createQuery("DELETE FROM Conjuro c WHERE c.mago.id = :idMago")
            .setParameter("idMago", idMago)
            .executeUpdate();
        gestorTransaction.commit();
    }
}
