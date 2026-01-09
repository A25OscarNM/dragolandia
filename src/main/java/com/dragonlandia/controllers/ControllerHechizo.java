package com.dragonlandia.controllers;

import java.util.ArrayList;

import com.dragonlandia.modelo.Hechizo;
import com.dragonlandia.vista.EmFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ControllerHechizo {
    private static EntityManager gestorEntidades;
    private static EntityTransaction gestorTransaction;

    public ControllerHechizo() {
        gestorEntidades = EmFactory.getEntityManager();
        gestorTransaction = gestorEntidades.getTransaction();
    }

    // CREATE
    public Hechizo crearHechizo(String nombre) {

        gestorTransaction.begin();
        Hechizo hechizo = new Hechizo(0, nombre);
        gestorEntidades.persist(hechizo);

        gestorTransaction.commit();

        return hechizo;
    }

    // SELECT
    public ArrayList<Hechizo> getHechizos() {
        ArrayList<Hechizo> listaHechizos = (ArrayList<Hechizo>) gestorEntidades
                .createQuery("from Hechizo", Hechizo.class).getResultList();

        return listaHechizos;
    }

    // UPDATE
    // DELETE
}
