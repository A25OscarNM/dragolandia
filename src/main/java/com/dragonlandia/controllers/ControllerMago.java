package com.dragonlandia.controllers;

import java.util.ArrayList;

import com.dragonlandia.modelo.Mago;
import com.dragonlandia.vista.EmFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ControllerMago {

    private static EntityManager gestorEntidades;
    private static EntityTransaction gestorTransaction;

    public ControllerMago() {
        gestorEntidades = EmFactory.getEntityManager();
        gestorTransaction = gestorEntidades.getTransaction();
    }

    // CREATE
    public Mago crearMago(String nombre, int vida, int nivelMagia) {
        gestorTransaction.begin();
        Mago mago = new Mago(0, nombre, vida, nivelMagia);
        gestorEntidades.persist(mago);

        gestorTransaction.commit();
        return mago;
    }

    // SELECT
    public static ArrayList<Mago> getMagos() {
        ArrayList<Mago> listaMagos = (ArrayList<Mago>) gestorEntidades.createQuery("from Mago", Mago.class)
                .getResultList();
        return listaMagos;
    }

    // UPDATE
    public void modificarMago(Mago magoModificado) {
        gestorTransaction.begin();
        gestorEntidades.merge(magoModificado);
        gestorTransaction.commit();
    }

    // DELETE
    public void eliminarMago(int id) {
        gestorTransaction.begin();
        Mago mago = gestorEntidades.find(Mago.class, id);
        if (mago != null) gestorEntidades.remove(mago);
        gestorTransaction.commit();
    }
}