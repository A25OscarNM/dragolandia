package com.dragonlandia.controllers;

import java.util.ArrayList;
import java.util.List;

import com.dragonlandia.modelo.Bosque;
import com.dragonlandia.modelo.Hechizo;
import com.dragonlandia.modelo.Mago;
import com.dragonlandia.modelo.Monstruo;
import com.dragonlandia.vista.DragoVista;
import com.dragonlandia.vista.EmFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class Controller {

    private DragoVista vista;
    private static EntityManager gestorEntidades;
    private static EntityTransaction gestorTransaction;
    private Mago magoCombate;
    private Monstruo jefeCombate;

    private static ControllerMago controllerMago;
    private static ControllerMonstruo controllerMonstruo;
    private static ControllerBosque controllerBosque;
    private static ControllerHechizo controllerHechizo;
    private static ControllerDragon controllerDragon;
    private static ControllerConjuro controllerConjuro;

    public Controller() {
        gestorEntidades = EmFactory.getEntityManager();
        gestorTransaction = gestorEntidades.getTransaction();

        controllerMago = new ControllerMago();
        controllerMonstruo = new ControllerMonstruo();
        controllerBosque = new ControllerBosque();
        controllerHechizo = new ControllerHechizo();
        controllerDragon = new ControllerDragon();
        controllerConjuro = new ControllerConjuro();

        this.vista = new DragoVista(this);
        this.vista.setVisible(true);
    }

    // MAGO
    public Mago crearMago(String nombre, int vida, int nivelMagia) {
        return controllerMago.crearMago(nombre, vida, nivelMagia);
    }

    public static ArrayList<Mago> getMagos() {
        return controllerMago.getMagos();
    }

    // MONSTRUO
    public Monstruo crearMonstruo(String nombre, int vida, int tipo) {
        return controllerMonstruo.crearMonstruo(nombre, vida, tipo);
    }

    public static ArrayList<Monstruo> getMonstruos() {
        return controllerMonstruo.getMonstruos();
    }

    public static ArrayList<Monstruo> getMonstruosPosibles() {
        return controllerMonstruo.getMonstruosPosibles();
    }

    public Monstruo buscarMonstruo(int id) {
        return controllerMonstruo.buscarMonstruo(id);
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

    // BOSQUE
    public Bosque crearBosque(String nombre, int nivelPeligro, Monstruo monstruoJefe, List<Monstruo> listaMonstruos) {
        return controllerBosque.crearBosque(nombre, nivelPeligro, monstruoJefe, listaMonstruos);
    }

    public static ArrayList<Bosque> getBosques() {
        return controllerBosque.getBosques();
    }

    // HECHIZO

    public Hechizo crearHechizo(String nombre) {
        return controllerHechizo.crearHechizo(nombre);
    }

    public ArrayList<Hechizo> getHechizos() {
        return controllerHechizo.getHechizos();
    }

    public void crearDragon(String nombre, int intensidadFuego, int resistencia, Bosque bosque) {
        controllerDragon.crearDragon(nombre, intensidadFuego, resistencia, bosque);
    }

    public void crearConjuro(Mago mago, Hechizo hechizo) {
        controllerConjuro.crearConjuro(mago, hechizo);
    }

    // pruebas
    public void prepararCombate(Mago m, Bosque b) {
        this.magoCombate = m;
        this.jefeCombate = b.mostrarJefe();

        vista.getPanelCombate().lblMago.setText("Mago: " + m.getNombre());
        vista.getPanelCombate().lblMonstruo.setText("Jefe: " + jefeCombate.getNombre());
        actualizarLabelsVida();
        vista.getPanelCombate().logCombate.setText("--- INICIO DEL COMBATE ---\n");
    }

    public void ejecutarTurno(String nombreHechizo) {

        ArrayList<Monstruo> congelados = new ArrayList<>();

        if (magoCombate.getVida() <= 0 || jefeCombate.getVida() <= 0) {
            return;
        }

        Hechizo hechizoElegido = null;
        try {
            hechizoElegido = gestorEntidades.createQuery(
                    "SELECT c.hechizo FROM Conjuro c WHERE c.mago.id = :magoId AND c.hechizo.nombre = :nombre",
                    Hechizo.class)
                    .setParameter("magoId", magoCombate.getId())
                    .setParameter("nombre", nombreHechizo)
                    .getSingleResult();
        } catch (NoResultException e) {
            hechizoElegido = null;
        }

        StringBuilder log = new StringBuilder();

        if (hechizoElegido != null) {
            if (hechizoElegido.getNombre().equals("Bola de nieve")) {
                congelados.add(jefeCombate);
                log.append("El mago " + magoCombate.getNombre() + " ataca al monstruo congelandolo.\n");
            } else {
                ArrayList<String> logHechizos = magoCombate.lanzarHechizo(jefeCombate, hechizoElegido.getId());

                for (String logHechizo : logHechizos) {
                    log.append(logHechizo);
                }
            }

        } else {
            magoCombate.setVida(magoCombate.getVida() - 1);
            log.append("Intentas lanzar ").append(nombreHechizo)
                    .append(" pero no lo conoces. ¡Te debilitas! (-1 vida)\n");
        }

        if (jefeCombate.getVida() > 0) {

            if (congelados.contains(jefeCombate)) {
                log.append("El jefe esta congelado y no podra atacar.\n");
                congelados.remove(jefeCombate);
            } else {
                jefeCombate.atacar(magoCombate);
                log.append("El jefe ").append(jefeCombate.getNombre())
                        .append(" ataca. Pierdes ").append(jefeCombate.getFuerza()).append(" de vida.\n");
            }
        } else {
            log.append("--- ¡VICTORIA! El jefe ha caído ---\n");
        }

        actualizarInterfazCombate(log.toString());
    }

    private void actualizarLabelsVida() {
        vista.getPanelCombate().lblVidaMago.setText("Vida: " + Math.max(0, magoCombate.getVida()));
        vista.getPanelCombate().lblVidaMonstruo.setText("Vida: " + Math.max(0, jefeCombate.getVida()));
    }

    private void actualizarInterfazCombate(String mensajeLog) {
        vista.getPanelCombate().lblVidaMago.setText("Vida: " + Math.max(0, magoCombate.getVida()));
        vista.getPanelCombate().lblVidaMonstruo.setText("Vida: " + Math.max(0, jefeCombate.getVida()));
        vista.getPanelCombate().logCombate.append(mensajeLog + "\n");
        vista.getPanelCombate().logCombate.setCaretPosition(
                vista.getPanelCombate().logCombate.getDocument().getLength());
    }
}