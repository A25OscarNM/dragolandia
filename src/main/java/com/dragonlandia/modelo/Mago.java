package com.dragonlandia.modelo;

import java.util.ArrayList;
import java.util.Random;

import com.dragonlandia.Controller;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mago")
public class Mago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private int vida;
    private int nivelMagia;

    public Mago() {
    }

    public Mago(int id, String nombre, int vida, int nivelMagia) {
        this.id = id;
        this.nombre = nombre;
        this.vida = vida;
        this.nivelMagia = nivelMagia;
    }

    /**
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return String
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return int
     */
    public int getVida() {
        return vida;
    }

    /**
     * @param vida
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * @return int
     */
    public int getNivelMagia() {
        return nivelMagia;
    }

    /**
     * @param nivelMagia
     */
    public void setNivelMagia(int nivelMagia) {
        this.nivelMagia = nivelMagia;
    }

    /**
     * @param monstruo
     */
    public void lanzarHechizo(Monstruo monstruo) {
        monstruo.setVida(monstruo.getVida() - this.nivelMagia);
    }

    public void lanzarHechizo(Monstruo monstruo, int hechizo) {

        Random rand = new Random();
        ArrayList<Monstruo> listaMonstruos = Controller.getMonstruos();

        switch (hechizo) {
            case 1 -> {
                System.out.println("Castea bola de fuego");

                for (Monstruo monstruoSelected : listaMonstruos) {
                    monstruoSelected.setVida(monstruo.getVida() - this.nivelMagia);
                }
            }
            case 2 -> {
                System.out.println("Castea rayo");
                monstruo.setVida(monstruo.getVida() - this.nivelMagia);
            }
            case 3 -> {
                System.out.println("Castea bola de nieve");
                System.out.println("El monstruo ahora esta congelado!");
                monstruo.setVida(0);
            }
            case 4 -> {
                System.out.println("Castea misil arcano");

                for (int i = 0; i < 3; i++) {
                    int numero = rand.nextInt(listaMonstruos.size());
                    listaMonstruos.get(numero).setVida(listaMonstruos.get(numero).getVida() - this.nivelMagia);
                }
            }
            default -> this.vida -= 1;
        }
    }
}
