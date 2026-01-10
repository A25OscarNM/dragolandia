package com.dragonlandia.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * @param monstruo
     */
    public void lanzarHechizo(Monstruo monstruo) {
        monstruo.setVida(monstruo.getVida() - this.nivelMagia);
    }

    
    /** 
     * @param monstruo
     * @param nombreHechizo
     * @return ArrayList<String>
     */
    public ArrayList<String> lanzarHechizo(List<Monstruo> monstruos, String nombreHechizo) {

        Random rand = new Random();

        ArrayList<String> logs = new ArrayList<>();
        String log = "";

        switch (nombreHechizo) {
            case "Bola de fuego" -> {
                for (Monstruo monstruoSelected : monstruos) {
                    monstruoSelected.setVida(monstruoSelected.getVida() - (nivelMagia / 2));
                }
                log = "El mago " + nombre + " ataca a todos los monstruos causando " + (nivelMagia / 2) + " de daño.\n";
                logs.add(log);
            }
            case "Rayo" -> {
                int selected = rand.nextInt(monstruos.size());

                monstruos.get(selected).setVida(monstruos.get(selected).getVida() - this.nivelMagia);

                log = "El mago " + nombre + " ataca al monstruo " + monstruos.get(selected).getNombre() + " causando " + nivelMagia
                        + " de daño.\n";
                logs.add(log);
            }
            case "Bola de nieve" -> {
                int selected = rand.nextInt(monstruos.size());

                monstruos.get(selected).setVida(0);

                log = "El mago " + nombre + " ataca al monstruo " + monstruos.get(selected).getNombre() + " causando "
                        + (nivelMagia * 2)
                        + " de daño.\n";
                logs.add(log);
            }
            case "Misil arcano" -> {
                for (int i = 0; i < 3; i++) {
                    int numero = rand.nextInt(monstruos.size());
                    monstruos.get(numero).setVida(monstruos.get(numero).getVida() - (nivelMagia / 3));

                    log = "El mago " + nombre + " ataca al monstruo " + monstruos.get(numero).getNombre()
                            + " causando "
                            + (nivelMagia / 3)
                            + " de daño.\n";
                    logs.add(log);
                }
            }
            default -> this.vida -= 1;
        }

        return logs;
    }
}
