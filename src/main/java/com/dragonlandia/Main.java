package com.dragonlandia;

import java.util.ArrayList;
import java.util.Random;

import com.dragonlandia.controllers.Controller;
import com.dragonlandia.modelo.Bosque;
import com.dragonlandia.modelo.Hechizo;
import com.dragonlandia.modelo.Mago;
import com.dragonlandia.modelo.Monstruo;
import com.dragonlandia.modelo.TipoMonstruo;

public class Main {

    public static void main(String[] args) {
        Controller app = new Controller();
        app.showAllMonstruos();

        // inicializar(app);
    }

    public static void inicializar(Controller app) {

        // Crear hechizos
        // app.crearHechizo("Bola de fuego");
        // app.crearHechizo("Rayo");
        // app.crearHechizo("Bola de nieve");
        // app.crearHechizo("Misil Arcano");

        // Crear magos
        Mago mago1 = app.crearMago("Jaina", 150, 40);
        Mago mago2 = app.crearMago("Mediev", 200, 30);

        agregarHechizos(mago1, app);
        agregarHechizos(mago2, app);

        // Crear monstruos

        ArrayList<Monstruo> listaMonstruos = new ArrayList<>();

        Monstruo monstruoJefe = app.crearMonstruo("Ogro Jefe", 150, TipoMonstruo.OGRO.ordinal());
        listaMonstruos.add(app.crearMonstruo("Ogro 2", 100, TipoMonstruo.OGRO.ordinal()));
        listaMonstruos.add(app.crearMonstruo("Ogro 3", 100, TipoMonstruo.OGRO.ordinal()));

        Bosque bosque = app.crearBosque("Bosque", 5, monstruoJefe, listaMonstruos);

        app.crearDragon("Dragonite", 20, 200, bosque);
    }

    public static void agregarHechizos(Mago mago, Controller app) {
        ArrayList<Hechizo> hechizos = app.getHechizos();
        Random rand = new Random();

        int nHechizo1 = rand.nextInt(hechizos.size());
        int nHechizo2 = rand.nextInt(hechizos.size());

        while (nHechizo1 == nHechizo2) {
            nHechizo2 = rand.nextInt(hechizos.size());
        }

        app.crearConjuro(mago, hechizos.get(nHechizo1));
        app.crearConjuro(mago, hechizos.get(nHechizo2));
    }
}