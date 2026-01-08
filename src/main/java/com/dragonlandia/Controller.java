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
    private Mago magoCombate;
    private Monstruo jefeCombate;

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

    public static ArrayList<Mago> getMagos() {
        ArrayList<Mago> listaMagos = (ArrayList<Mago>) gestorEntidades.createQuery("from Mago", Mago.class)
                .getResultList();
        return listaMagos;
    }

    public static ArrayList<Bosque> getBosques() {
        ArrayList<Bosque> listaBosques = (ArrayList<Bosque>) gestorEntidades.createQuery("from Bosque", Bosque.class)
                .getResultList();
        return listaBosques;
    }

    public static ArrayList<Monstruo> getMonstruos() {

        ArrayList<Monstruo> listaMonstruos = (ArrayList<Monstruo>) gestorEntidades
                .createQuery("from Monstruo", Monstruo.class)
                .getResultList();

        return listaMonstruos;
    }

    public static ArrayList<Hechizo> getHechizos() {

        ArrayList<Hechizo> listaHechizos = (ArrayList<Hechizo>) gestorEntidades
                .createQuery("from Hechizo", Hechizo.class).getResultList();

        return listaHechizos;
    }

    public static ArrayList<Monstruo> getMonstruosPosibles() {
        String jpql = "SELECT m FROM Monstruo m WHERE m.id NOT IN "
                + "(SELECT lm.id FROM Bosque b JOIN b.listaMonstruos lm)";

        List<Monstruo> resultado = gestorEntidades.createQuery(jpql, Monstruo.class).getResultList();

        return new ArrayList<>(resultado);
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

    // pruebas
    public void prepararCombate(Mago m, Bosque b) {
        this.magoCombate = m;
        this.jefeCombate = b.getMonstruoJefe();

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

            if (hechizoElegido.getNombre() == "Bola de nieve") {
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