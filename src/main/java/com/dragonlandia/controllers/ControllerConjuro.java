package com.dragonlandia.controllers;

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

    // CREATE
    public void crearConjuro(Mago mago, Hechizo hechizo) {
        gestorTransaction.begin();
        Conjuro conjuro = new Conjuro(0, mago, hechizo);
        gestorEntidades.persist(conjuro);

        gestorTransaction.commit();
    }

    // SELECT
    // UPDATE
    // DELETE
}
