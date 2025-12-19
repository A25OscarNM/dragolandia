package com.dragonlandia;

import java.util.ArrayList;
import java.util.List;

import com.dragonlandia.modelo.Bosque;
import com.dragonlandia.modelo.Conjuro;
import com.dragonlandia.modelo.Dragon;
import com.dragonlandia.modelo.Hechizo;
import com.dragonlandia.modelo.Mago;
import com.dragonlandia.modelo.Monstruo;
import com.dragonlandia.modelo.TipoMonstruo;
import com.dragonlandia.vista.DragoVista;
import com.dragonlandia.vista.EmFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class Controller {

    private DragoVista vista;
    private static EntityManager gestorEntidades;
    private static EntityTransaction gestorTransaction;

    public Controller() {
        gestorEntidades = EmFactory.getEntityManager();
        gestorTransaction = gestorEntidades.getTransaction();

        this.vista = new DragoVista(this);
        this.vista.setVisible(true);
    }

    public Mago crearMago(String nombre, int vida, int nivelMagia) {
        gestorTransaction.begin();
        Mago mago = new Mago(0, nombre, vida, nivelMagia);
        gestorEntidades.persist(mago);

        gestorTransaction.commit();
        return mago;
    }

    public Monstruo crearMonstruo(String nombre, int vida, int tipo) {

        gestorTransaction.begin();
        Monstruo monstruo = new Monstruo(0, nombre, vida, TipoMonstruo.values()[tipo]);
        gestorEntidades.persist(monstruo);

        gestorTransaction.commit();

        return monstruo;
    }

    public Bosque crearBosque(String nombre, int nivelPeligro, Monstruo monstruoJefe, List<Monstruo> listaMonstruos) {

        gestorTransaction.begin();
        Bosque bosque = new Bosque(0, nombre, nivelPeligro, monstruoJefe, listaMonstruos);
        gestorEntidades.persist(bosque);

        gestorTransaction.commit();

        return bosque;
    }

    public void crearDragon(String nombre, int intensidadFuego, int resistencia, Bosque bosque) {

        gestorTransaction.begin();
        Dragon dragon = new Dragon(0, nombre, intensidadFuego, resistencia, bosque);
        gestorEntidades.persist(dragon);

        gestorTransaction.commit();
    }

    public void crearConjuro(Mago mago, Hechizo hechizo) {

        gestorTransaction.begin();
        Conjuro conjuro = new Conjuro(0, mago, hechizo);
        gestorEntidades.persist(conjuro);

        gestorTransaction.commit();
    }

    public Hechizo crearHechizo(String nombre) {

        gestorTransaction.begin();
        Hechizo hechizo = new Hechizo(0, nombre);
        gestorEntidades.persist(hechizo);

        gestorTransaction.commit();

        return hechizo;
    }

    public static ArrayList<Monstruo> getMonstruos() {

        ArrayList<Monstruo> listaMonstruos = (ArrayList<Monstruo>) gestorEntidades
                .createQuery("from Monstruo", Monstruo.class)
                .getResultList();

        return listaMonstruos;
    }

    public static ArrayList<Bosque> getBosques() {
        ArrayList<Bosque> listaBosques = (ArrayList<Bosque>) gestorEntidades.createQuery("from Bosque", Bosque.class)
                .getResultList();

        return listaBosques;
    }

    public static ArrayList<Hechizo> getHechizos() {

        ArrayList<Hechizo> listaHechizos = (ArrayList<Hechizo>) gestorEntidades
                .createQuery("from Hechizo", Hechizo.class).getResultList();

        return listaHechizos;
    }

    public static ArrayList<Monstruo> getMonstruosPosibles() {

        ArrayList<Monstruo> monstruos = getMonstruos();
        ArrayList<Monstruo> monstruosDevolver = new ArrayList<>();
        Monstruo mon = null;

        for (Monstruo monstruo : monstruos) {
            try {
                mon = (Monstruo) gestorEntidades
                        .createQuery("SELECT a from bosque_monstruo a WHERE a.listaMonstuos.id = :id", Monstruo.class)
                        .setParameter("id", monstruo.getId())
                        .getSingleResult();
            } catch (NoResultException ex) {
                monstruosDevolver.add(mon);
            }
        }

        return monstruosDevolver;
    }

    // Manda los monstruos a la vista
    public void showAllMonstruos() {

        ArrayList<Monstruo> monstruos = getMonstruosPosibles();
        if (!monstruos.isEmpty()) {
            for (Monstruo monstruo : monstruos) {
                vista.getPanelLateral().addMonstruo(monstruo.getId(), monstruo.getNombre(), monstruo.getVida(),
                        monstruo.getTipo().ordinal(),
                        monstruo.getFuerza());
            }
        }
    }

    public Monstruo buscarMonstruo(int id) {

        Monstruo monstruo = gestorEntidades
                .createQuery("SELECT monstruo from Monstruo monstruo WHERE monstruo.id = :id", Monstruo.class)
                .setParameter("id", id)
                .getSingleResult();
        return monstruo;
    }
}
