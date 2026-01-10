package com.dragonlandia.controllers;

import java.util.ArrayList;
import java.util.List;

import com.dragonlandia.modelo.Bosque;
import com.dragonlandia.modelo.Monstruo;
import com.dragonlandia.vista.EmFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ControllerBosque {
    private static EntityManager gestorEntidades;
    private static EntityTransaction gestorTransaction;

    public ControllerBosque() {
        gestorEntidades = EmFactory.getEntityManager();
        gestorTransaction = gestorEntidades.getTransaction();
    }

    // CREATE
    public Bosque crearBosque(String nombre, int nivelPeligro, Monstruo monstruoJefe, List<Monstruo> listaMonstruos) {
        gestorTransaction.begin();
        Bosque bosque = new Bosque(0, nombre, nivelPeligro, monstruoJefe, listaMonstruos);
        gestorEntidades.persist(bosque);

        gestorTransaction.commit();

        return bosque;
    }

    // SELECT
    public ArrayList<Bosque> getBosques() {
        ArrayList<Bosque> listaBosques = (ArrayList<Bosque>) gestorEntidades.createQuery("from Bosque", Bosque.class)
                .getResultList();
        return listaBosques;
    }

    // UPDATE
    public void modificarBosque(Bosque bosqueModificar) {
        gestorTransaction.begin();
        gestorEntidades.merge(bosqueModificar);
        gestorTransaction.commit();
    }

    // DELETE
    public void eliminarBosque(int id) {
        gestorTransaction.begin();
        Bosque bosque = gestorEntidades.find(Bosque.class, id);
        if (bosque != null) {
            gestorEntidades.remove(bosque);
        }
        gestorTransaction.commit();
    }
}
