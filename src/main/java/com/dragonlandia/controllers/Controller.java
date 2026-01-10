package com.dragonlandia.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dragonlandia.modelo.Bosque;
import com.dragonlandia.modelo.Conjuro;
import com.dragonlandia.modelo.Dragon;
import com.dragonlandia.modelo.Hechizo;
import com.dragonlandia.modelo.Mago;
import com.dragonlandia.modelo.Monstruo;
import com.dragonlandia.vista.DragoVista;
import com.dragonlandia.vista.EmFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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
    /** 
     * @param nombre
     * @param vida
     * @param nivelMagia
     * @return Mago
     */
    public Mago crearMago(String nombre, int vida, int nivelMagia) {
        return controllerMago.crearMago(nombre, vida, nivelMagia);
    }

    /** 
     * @return ArrayList<Mago>
     */
    public static ArrayList<Mago> getMagos() {
        return controllerMago.getMagos();
    }

    /** 
     * @param mago
     */
    public void modificarMago(Mago mago) {
        controllerMago.modificarMago(mago);
    }

    /** 
     * @param id
     */
    public void eliminarMago(int id) {
        controllerMago.eliminarMago(id);
    }

    // MONSTRUO
    /** 
     * @param nombre
     * @param vida
     * @param tipo
     * @return Monstruo
     */
    public Monstruo crearMonstruo(String nombre, int vida, int tipo) {
        return controllerMonstruo.crearMonstruo(nombre, vida, tipo);
    }

    /** 
     * @return ArrayList<Monstruo>
     */
    public static ArrayList<Monstruo> getMonstruos() {
        return controllerMonstruo.getMonstruos();
    }

    /** 
     * @return ArrayList<Monstruo>
     */
    public static ArrayList<Monstruo> getMonstruosPosibles() {
        return controllerMonstruo.getMonstruosPosibles();
    }

    /** 
     * @param id
     * @return Monstruo
     */
    public Monstruo buscarMonstruo(int id) {
        return controllerMonstruo.buscarMonstruo(id);
    }

    /** 
     * @param monstruo
     */
    public void modificarMonstruo(Monstruo monstruo) {
        controllerMonstruo.modificarMonstruo(monstruo);
    }

    /** 
     * @param id
     */
    public void eliminarMonstruo(int id) {
        controllerMonstruo.eliminarMonstruo(id);
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
    /** 
     * @param nombre
     * @param nivelPeligro
     * @param monstruoJefe
     * @param listaMonstruos
     * @return Bosque
     */
    public Bosque crearBosque(String nombre, int nivelPeligro, Monstruo monstruoJefe, List<Monstruo> listaMonstruos) {
        return controllerBosque.crearBosque(nombre, nivelPeligro, monstruoJefe, listaMonstruos);
    }

    /** 
     * @return ArrayList<Bosque>
     */
    public static ArrayList<Bosque> getBosques() {
        return controllerBosque.getBosques();
    }

    /** 
     * @param bosque
     */
    public void modificarBosque(Bosque bosque) {
        controllerBosque.modificarBosque(bosque);
    }

    /** 
     * @param id
     */
    public void eliminarBosque(int id) {
        controllerBosque.eliminarBosque(id);
    }

    // DRAGÓN
    /** 
     * @param nombre
     * @param intensidadFuego
     * @param resistencia
     * @param bosque
     */
    public void crearDragon(String nombre, int intensidadFuego, int resistencia, Bosque bosque) {
        controllerDragon.crearDragon(nombre, intensidadFuego, resistencia, bosque);
    }

    /** 
     * @param id
     * @return Dragon
     */
    public Dragon getDragon(int id) {
        return controllerDragon.getDragon(id);
    }

    /** 
     * @param dragon
     */
    public void modificarDragon(Dragon dragon) {
        controllerDragon.modificarDragon(dragon);
    }

    /** 
     * @param id
     */
    public void eliminarDragon(int id) {
        controllerDragon.eliminarDragon(id);
    }

    // HECHIZO
    /** 
     * @param nombre
     * @return Hechizo
     */
    public Hechizo crearHechizo(String nombre) {
        return controllerHechizo.crearHechizo(nombre);
    }

    /** 
     * @return ArrayList<Hechizo>
     */
    public ArrayList<Hechizo> getHechizos() {
        return controllerHechizo.getHechizos();
    }

    /** 
     * @param hechizo
     */
    public void modificarHechizo(Hechizo hechizo) {
        controllerHechizo.modificarHechizo(hechizo);
    }

    /** 
     * @param id
     */
    public void eliminarHechizo(int id) {
        controllerHechizo.eliminarHechizo(id);
    }

    // CONJURO
    /** 
     * @param mago
     * @param hechizo
     */
    public void crearConjuro(Mago mago, Hechizo hechizo) {
        controllerConjuro.crearConjuro(mago, hechizo);
    }

    /** 
     * @param id
     * @return Conjuro
     */
    public Conjuro getConjuro(int id) {
        return controllerConjuro.getConjuro(id);
    }

    /** 
     * @param idMago
     * @return List<Hechizo>
     */
    public List<Hechizo> getHechizosPorMago(int idMago) {
        return controllerConjuro.getHechizosPorMago(idMago);
    }

    /** 
     * @param conjuro
     */
    public void modificarConjuro(Conjuro conjuro) {
        controllerConjuro.modificarConjuro(conjuro);
    }

    /** 
     * @param id
     */
    public void eliminarConjuro(int id) {
        controllerConjuro.eliminarConjuro(id);
    }
    /** 
     * @param idMago
     */
    public void borrarConjurosPorMago(int idMago) {
        controllerConjuro.borrarConjurosPorMago(idMago);
    }

    private List<Mago> magosEnCombate;
    private List<Monstruo> monstruosEnCombate;
    private Bosque bosqueActual;
    private int indiceMagoActual = 0;
    private StringBuilder logAcumulado = new StringBuilder();
    private Random random = new Random();

    /** 
     * @param b
     */
    public void prepararCombate(Bosque b) {
        this.bosqueActual = b;
        this.magosEnCombate = new ArrayList<>(getMagos());
        this.monstruosEnCombate = new ArrayList<>(b.getListaMonstruos());

        // Añadimos al jefe si no está en la lista general de monstruos
        if (!monstruosEnCombate.contains(b.mostrarJefe())) {
            monstruosEnCombate.add(b.mostrarJefe());
        }

        this.indiceMagoActual = 0;
        this.logAcumulado = new StringBuilder("--- INICIO DEL COMBATE ---\n");

        mostrarTurnoActual();
        actualizarInterfazCombate("");
    }

    /** 
     * @param nombreHechizo
     */
    public void ejecutarTurno(String nombreHechizo) {

        if (magosEnCombate.isEmpty() || monstruosEnCombate.isEmpty()) {
            return;
        }

        Mago magoQueAtaca = magosEnCombate.get(indiceMagoActual);

        List<Hechizo> conocidos = getHechizosPorMago(magoQueAtaca.getId());

        Hechizo hechizoEncontrado = null;
        for (Hechizo h : conocidos) {
            if (h.getNombre().equals(nombreHechizo)) {
                hechizoEncontrado = h;
            }
        }

        if (hechizoEncontrado != null) {
            List<String> logsHechizo = magoQueAtaca.lanzarHechizo(monstruosEnCombate, hechizoEncontrado.getNombre());

            for (String log : logsHechizo) {
                logAcumulado.append(log);
            }

        } else {
            magoQueAtaca.setVida(magoQueAtaca.getVida() - 1);
            logAcumulado.append(magoQueAtaca.getNombre()).append(" no conoce ").append(nombreHechizo).append(" (-1 vida).\n");
        }

        // Comprobamos si el mago ha muerto por la penalización
        if (magoQueAtaca.getVida() <= 0) {
            logAcumulado.append(magoQueAtaca.getNombre()).append(" murió.\n");
            magosEnCombate.remove(magoQueAtaca);
        } else {
            indiceMagoActual++;
        }

        actualizarMuertos();

        // Gestión de fin de ronda
        if (indiceMagoActual >= magosEnCombate.size() || magosEnCombate.isEmpty()) {
            ejecutarFaseEnemigos();
            actualizarInterfazCombate(logAcumulado.toString());
            logAcumulado = new StringBuilder("--- NUEVA RONDA ---\n");
            indiceMagoActual = 0;
        }

        if (!magosEnCombate.isEmpty()) {
            mostrarTurnoActual();
        }
    }

    private void ejecutarFaseEnemigos() {
        logAcumulado.append("\n--- TURNO DE LOS MONSTRUOS ---\n");

        for (int i = 0; i < monstruosEnCombate.size(); i++) {
            Monstruo m = monstruosEnCombate.get(i);

            if (!magosEnCombate.isEmpty()) {
                Mago victima = magosEnCombate.get(random.nextInt(magosEnCombate.size()));
                m.atacar(victima);
                logAcumulado.append(m.getNombre()).append(" ataca a ").append(victima.getNombre()).append(".\n");

                actualizarMuertos();
            }
        }
        atacarConDragon();
    }

    private void atacarConDragon() {
        Dragon d = controllerDragon.getDragonPorIdBosque(bosqueActual.getId());
        Monstruo jefe = bosqueActual.mostrarJefe();

        if (d != null && jefe != null && jefe.getVida() > 0) {
            jefe.setVida(jefe.getVida() - d.getIntensidadFuego());
            logAcumulado.append("Dragón ").append(d.getNombre()).append(" quema al jefe haciendo ").append(d.getIntensidadFuego()).append(" de daño.\n");
            actualizarMuertos();
        }
    }

    /** 
     * @param log
     */
    private void actualizarInterfazCombate(String log) {
        StringBuilder sb = new StringBuilder(log);
        sb.append("\n--- VIVOS ---\n");

        for (Mago m : magosEnCombate) {
            sb.append("Mago: ").append(m.getNombre()).append(" | Vida: ").append(m.getVida()).append("\n");
        }

        if (!monstruosEnCombate.isEmpty()) {
            sb.append("Jefe actual: ").append(bosqueActual.mostrarJefe().getNombre()).append("\n");
            for (Monstruo m : monstruosEnCombate) {
                sb.append("Enemigo: ").append(m.getNombre()).append(" | Vida: ").append(m.getVida()).append("\n");
            }
        }

        vista.getPanelCombate().logCombate.setText(sb.toString());

        if (magosEnCombate.isEmpty()) {
            vista.showMessage("¡GAME OVER! Los magos han muerto.");
        }
        if (monstruosEnCombate.isEmpty()) {
            vista.showMessage("¡VICTORIA! El bosque es seguro.");
        }
    }

    private void mostrarTurnoActual() {
        if (!magosEnCombate.isEmpty()) {
            Mago actual = magosEnCombate.get(indiceMagoActual);
            vista.getPanelCombate().lblMago.setText("Turno de: " + actual.getNombre() + " (" + (indiceMagoActual + 1) + "/" + magosEnCombate.size() + ")");
        }
    }

    private void actualizarMuertos() {

        for (Mago m : new ArrayList<>(magosEnCombate)) {
            if (m.getVida() <= 0) {
                logAcumulado.append("Mago ").append(m.getNombre()).append(" murió.\n");
                borrarConjurosPorMago(m.getId());
                eliminarMago(m.getId());
                magosEnCombate.remove(m);
            }
        }

        if (bosqueActual.mostrarJefe() != null && bosqueActual.mostrarJefe().getVida() <= 0) {

            if (!monstruosEnCombate.isEmpty()) {
                Monstruo nuevoJefe = monstruosEnCombate.get(0);
                bosqueActual.setMonstruoJefe(nuevoJefe);

                controllerBosque.modificarBosque(bosqueActual);
            } else {
                bosqueActual.setMonstruoJefe(null);
            }
        }

        for (Monstruo m : new ArrayList<>(monstruosEnCombate)) {
            if (m.getVida() <= 0) {
                logAcumulado.append("Monstruo ").append(m.getNombre()).append(" murió.\n");

                
                bosqueActual.getListaMonstruos().remove(m);
                modificarBosque(bosqueActual);

                eliminarMonstruo(m.getId());
                monstruosEnCombate.remove(m);
            }
        }

    }
}
