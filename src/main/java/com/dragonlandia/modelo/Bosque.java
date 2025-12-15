package com.dragonlandia.modelo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bosque")
public class Bosque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private int nivelPeligro;

    @OneToOne
    private Monstruo monstruoJefe;

    @OneToMany(targetEntity = Monstruo.class)
    private List<Monstruo> listaMonstruos;

    public Bosque() {
    }

    public Bosque(int id, String nombre, int nivelPeligro, Monstruo monstruoJefe, List<Monstruo> listaMonstruos) {
        this.id = id;
        this.nombre = nombre;
        this.nivelPeligro = nivelPeligro;
        this.monstruoJefe = monstruoJefe;
        this.listaMonstruos = listaMonstruos;
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
    public int getNivelPeligro() {
        return nivelPeligro;
    }

    /**
     * @param nivelPeligro
     */
    public void setNivelPeligro(int nivelPeligro) {
        this.nivelPeligro = nivelPeligro;
    }

    public void setMonstruoJefe(Monstruo monstruoJefe) {
        this.monstruoJefe = monstruoJefe;
    }

    /**
     * @return Monstruo
     */
    public Monstruo getMonstruoJefe() {
        return this.monstruoJefe;
    }

    public List<Monstruo> getListaMonstruos() {
        return listaMonstruos;
    }

    public void setListaMonstruos(List<Monstruo> listaMonstruos) {
        this.listaMonstruos = listaMonstruos;
    }

    /**
     * @param monstruoJefe
     */
    public void cambiarJefe(Monstruo monstruoJefe) {
        this.monstruoJefe = monstruoJefe;
    }

    /**
     * @return Monstruo
     */
    public Monstruo mostrarJefe() {
        return this.monstruoJefe;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
