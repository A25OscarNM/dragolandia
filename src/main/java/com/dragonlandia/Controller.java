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
        return new ArrayList<>(gestorEntidades.createQuery("SELECT m FROM Mago m", Mago.class).getResultList());
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

    //pruebas
    public void prepararCombate(Mago m, Bosque b) {
        this.magoCombate = m;
        this.jefeCombate = b.getMonstruoJefe();

        // Actualizamos la interfaz con los nombres y vidas iniciales
        vista.getPanelCombate().lblMago.setText("Mago: " + m.getNombre());
        vista.getPanelCombate().lblMonstruo.setText("Jefe: " + jefeCombate.getNombre());
        actualizarLabelsVida();
        vista.getPanelCombate().logCombate.setText("--- INICIO DEL COMBATE ---\n");
    }

    public void ejecutarTurno(String nombreHechizo) {
        if (magoCombate.getVida() <= 0 || jefeCombate.getVida() <= 0) {
            return;
        }

        // Buscamos el hechizo que el usuario quiere lanzar dentro de los que el mago conoce
        // Esta consulta verifica la relación Mago-Hechizo (tu tabla de conjuros)
        Hechizo hechizoElegido = null;
        try {
            // Buscamos en la tabla 'Conjuro' donde el mago coincida y el nombre del hechizo también
            hechizoElegido = gestorEntidades.createQuery(
                    "SELECT c.hechizo FROM Conjuro c WHERE c.mago.id = :magoId AND c.hechizo.nombre = :nombreH",
                    Hechizo.class)
                    .setParameter("magoId", magoCombate.getId())
                    .setParameter("nombreH", nombreHechizo)
                    .getSingleResult();
        } catch (NoResultException e) {
            hechizoElegido = null; // El mago no tiene ese conjuro asignado
        }

        StringBuilder log = new StringBuilder();

        if (hechizoElegido != null) {
            // EL MAGO LANZA EL HECHIZO
            int dañoBase = magoCombate.getNivelMagia();

            // Lógica según el tipo de hechizo (Requisitos del trabajo)
            if (nombreHechizo.toLowerCase().contains("nieve")) {
                jefeCombate.setVida(0);
                log.append("¡BOLA DE NIEVE! El jefe ha quedado congelado y derrotado.\n");
            } else {
                // Se le resta puntos de vida en función del nivel de magia + efecto del hechizo
                int dañoTotal = dañoBase + magoCombate.getNivelMagia();
                jefeCombate.setVida(Math.max(0, jefeCombate.getVida() - dañoTotal));
                log.append("Lanzas ").append(nombreHechizo)
                        .append(" causando ").append(dañoTotal).append(" de daño.\n");
            }
        } else {
            // PENALIZACIÓN: Si el hechizo no es de sus conjuros, se le resta 1 al mago
            magoCombate.setVida(magoCombate.getVida() - 1);
            log.append("Intentas lanzar ").append(nombreHechizo)
                    .append(" pero no lo conoces. ¡Te debilitas! (-1 vida)\n");
        }

        // CONTRAATAQUE DEL JEFE
        if (jefeCombate.getVida() > 0) {
            int vidaAnterior = magoCombate.getVida();
            jefeCombate.atacar(magoCombate); // Este método usa la fuerza del monstruo
            log.append("El jefe ").append(jefeCombate.getNombre())
                    .append(" ataca. Pierdes ").append(vidaAnterior - magoCombate.getVida()).append(" de vida.\n");
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
        // 1. Actualizamos los números de vida en los JLabels
        // Usamos Math.max(0, ...) para que no muestre vida negativa si muere
        vista.getPanelCombate().lblVidaMago.setText("Vida: " + Math.max(0, magoCombate.getVida()));
        vista.getPanelCombate().lblVidaMonstruo.setText("Vida: " + Math.max(0, jefeCombate.getVida()));

        // 2. Añadimos el texto de lo que ha pasado al JTextArea
        // .append() añade el texto al final de lo que ya había
        vista.getPanelCombate().logCombate.append(mensajeLog + "\n");

        // 3. (Opcional) Hacer scroll automático hacia abajo para ver el último mensaje
        vista.getPanelCombate().logCombate.setCaretPosition(
                vista.getPanelCombate().logCombate.getDocument().getLength()
        );
    }

}
