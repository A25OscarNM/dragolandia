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
    public void crearDragon(String nombre, int intensidadFuego, int resistencia, Bosque bosque) {
        gestorTransaction.begin();
        Dragon dragon = new Dragon(0, nombre, intensidadFuego, resistencia, bosque);
        gestorEntidades.persist(dragon);

        gestorTransaction.commit();
    }

    // SELECT

    // UPDATE
    // DELETE
}
