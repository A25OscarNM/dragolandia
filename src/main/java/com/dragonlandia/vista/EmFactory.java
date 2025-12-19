package com.dragonlandia.vista;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EmFactory {

    private static final EntityManagerFactory xestorEntidades = Persistence
            .createEntityManagerFactory("dragolandiaServizo");

    public static EntityManager getEntityManager() {
        return xestorEntidades.createEntityManager();
    }

    public static void close() {
        if (xestorEntidades.isOpen()) {
            xestorEntidades.close();
        }
    }
}