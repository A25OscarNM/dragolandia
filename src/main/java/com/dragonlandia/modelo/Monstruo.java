package com.dragonlandia.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mostruo")
public class Monstruo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private int vida;

    @Enumerated(EnumType.STRING)
    private TipoMonstruo tipo;

    private int fuerza;

    public Monstruo() {
    }

    public Monstruo(int id, String nombre, int vida, TipoMonstruo tipo, int fuerza) {
        this.id = id;
        this.nombre = nombre;
        this.vida = vida;
        this.tipo = tipo;
        this.fuerza = fuerza;
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
     * @return TipoMonstruo
     */
    public TipoMonstruo getTipo() {
        return tipo;
    }

    /**
     * @param tipo
     */
    public void setTipo(TipoMonstruo tipo) {
        this.tipo = tipo;
    }

    /**
     * @return int
     */
    public int getFuerza() {
        return fuerza;
    }

    /**
     * @param fuerza
     */
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    /**
     * @param mago
     */
    public void atacar(Mago mago) {
        mago.setVida(mago.getVida() - this.fuerza);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
