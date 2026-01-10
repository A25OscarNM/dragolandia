package com.dragonlandia.controllers;

import java.util.ArrayList;
import java.util.List;

import com.dragonlandia.modelo.Monstruo;
import com.dragonlandia.modelo.TipoMonstruo;
import com.dragonlandia.vista.EmFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ControllerMonstruo {
    private static EntityManager gestorEntidades;
    private static EntityTransaction gestorTransaction;

    public ControllerMonstruo() {
        gestorEntidades = EmFactory.getEntityManager();
        gestorTransaction = gestorEntidades.getTransaction();
    }

    // CREATE
    public Monstruo crearMonstruo(String nombre, int vida, int tipo) {
        gestorTransaction.begin();
        Monstruo monstruo = new Monstruo(0, nombre, vida, TipoMonstruo.values()[tipo]);
        gestorEntidades.persist(monstruo);

        gestorTransaction.commit();

        return monstruo;
    }

    // SELECT
    public ArrayList<Monstruo> getMonstruos() {
        ArrayList<Monstruo> listaMonstruos = (ArrayList<Monstruo>) gestorEntidades
                .createQuery("from Monstruo", Monstruo.class)
                .getResultList();

        return listaMonstruos;
    }

    // Monstruos que no esten en otro bosque
    public ArrayList<Monstruo> getMonstruosPosibles() {
        String jpql = "SELECT m FROM Monstruo m WHERE m.id NOT IN "
                + "(SELECT lm.id FROM Bosque b JOIN b.listaMonstruos lm)";

        List<Monstruo> resultado = gestorEntidades.createQuery(jpql, Monstruo.class).getResultList();

        return new ArrayList<>(resultado);
    }

    // Buscar por id
    public Monstruo buscarMonstruo(int id) {

        Monstruo monstruo = gestorEntidades
                .createQuery("SELECT monstruo from Monstruo monstruo WHERE monstruo.id = :id", Monstruo.class)
                .setParameter("id", id)
                .getSingleResult();
        return monstruo;
    }

    // UPDATE
    public void modificarMonstruo(Monstruo monstruo) {
        gestorTransaction.begin();
        gestorEntidades.merge(monstruo);
        gestorTransaction.commit();
    }

    // DELETE
    public void eliminarMonstruo(int id) {
        gestorTransaction.begin();
        Monstruo m = gestorEntidades.find(Monstruo.class, id);
        if (m != null) gestorEntidades.remove(m);
        gestorTransaction.commit();
    }
}
