package com.dragonlandia.controllers;

import com.dragonlandia.modelo.Bosque;
import com.dragonlandia.modelo.Dragon;
import com.dragonlandia.vista.EmFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ControllerDragon {
    private static EntityManager gestorEntidades;
    private static EntityTransaction gestorTransaction;

    public ControllerDragon() {
        gestorEntidades = EmFactory.getEntityManager();
        gestorTransaction = gestorEntidades.getTransaction();
    }

    // CREATE
    /** 
     * @param nombre
     * @param intensidadFuego
     * @param resistencia
     * @param bosque
     */
    public void crearDragon(String nombre, int intensidadFuego, int resistencia, Bosque bosque) {
        gestorTransaction.begin();
        Dragon dragon = new Dragon(0, nombre, intensidadFuego, resistencia, bosque);
        gestorEntidades.persist(dragon);

        gestorTransaction.commit();
    }

    // SELECT
    /** 
     * @param id
     * @return Dragon
     */
    public Dragon getDragon(int id) {
        return gestorEntidades.find(Dragon.class, id);
    }

    /** 
     * @param idBosque
     * @return Dragon
     */
    public Dragon getDragonPorIdBosque(int idBosque) {
    return gestorEntidades
        .createQuery(
            "SELECT d FROM Dragon d WHERE d.bosque.id = :idBosque",
            Dragon.class
        )
        .setParameter("idBosque", idBosque)
        .getSingleResult();
}

    // UPDATE
    /** 
     * @param dragon
     */
    public void modificarDragon(Dragon dragon) {
        gestorTransaction.begin();
        gestorEntidades.merge(dragon);
        gestorTransaction.commit();
    }

    // DELETE
    /** 
     * @param id
     */
    public void eliminarDragon(int id) {
        gestorTransaction.begin();
        Dragon dragon = gestorEntidades.find(Dragon.class, id);
        if (dragon != null) gestorEntidades.remove(dragon);
        gestorTransaction.commit();
    }
}
