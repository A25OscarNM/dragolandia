package com.dragonlandia.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "conjuro")
public class Conjuro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Mago.class)
    private Mago mago;

    @ManyToOne(targetEntity = Hechizo.class)
    private Hechizo hechizo;

    public Conjuro() {
    }

    public Conjuro(int id, Mago mago, Hechizo hechizo) {
        this.id = id;
        this.mago = mago;
        this.hechizo = hechizo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mago getMago() {
        return mago;
    }

    public void setMago(Mago mago) {
        this.mago = mago;
    }

    public Hechizo getHechizo() {
        return hechizo;
    }

    public void setHechizo(Hechizo hechizo) {
        this.hechizo = hechizo;
    }
}
