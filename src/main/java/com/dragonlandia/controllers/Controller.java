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
    public Mago crearMago(String nombre, int vida, int nivelMagia) {
        return controllerMago.crearMago(nombre, vida, nivelMagia);
    }

    public static ArrayList<Mago> getMagos() {
        return controllerMago.getMagos();
    }

    public void modificarMago(Mago mago) {
        controllerMago.modificarMago(mago);
    }

    public void eliminarMago(int id) {
        controllerMago.eliminarMago(id);
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

    public void modificarMonstruo(Monstruo monstruo) {
        controllerMonstruo.modificarMonstruo(monstruo);
    }

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
    public Bosque crearBosque(String nombre, int nivelPeligro, Monstruo monstruoJefe, List<Monstruo> listaMonstruos) {
        return controllerBosque.crearBosque(nombre, nivelPeligro, monstruoJefe, listaMonstruos);
    }

    public static ArrayList<Bosque> getBosques() {
        return controllerBosque.getBosques();
    }

    public void modificarBosque(Bosque bosque) {
        controllerBosque.modificarBosque(bosque);
    }

    public void eliminarBosque(int id) {
        controllerBosque.eliminarBosque(id);
    }

    // DRAGÓN
    public void crearDragon(String nombre, int intensidadFuego, int resistencia, Bosque bosque) {
        controllerDragon.crearDragon(nombre, intensidadFuego, resistencia, bosque);
    }

    public Dragon getDragon(int id) {
        return controllerDragon.getDragon(id);
    }

    public void modificarDragon(Dragon dragon) {
        controllerDragon.modificarDragon(dragon);
    }

    public void eliminarDragon(int id) {
        controllerDragon.eliminarDragon(id);
    }

    // HECHIZO
    public Hechizo crearHechizo(String nombre) {
        return controllerHechizo.crearHechizo(nombre);
    }

    public ArrayList<Hechizo> getHechizos() {
        return controllerHechizo.getHechizos();
    }

    public void modificarHechizo(Hechizo hechizo) {
        controllerHechizo.modificarHechizo(hechizo);
    }

    public void eliminarHechizo(int id) {
        controllerHechizo.eliminarHechizo(id);
    }

    // CONJURO
    public void crearConjuro(Mago mago, Hechizo hechizo) {
        controllerConjuro.crearConjuro(mago, hechizo);
    }

    public Conjuro getConjuro(int id) {
        return controllerConjuro.getConjuro(id);
    }

    public List<Hechizo> getHechizosPorMago(int idMago) {
        return controllerConjuro.getHechizosPorMago(idMago);
    }

    public void modificarConjuro(Conjuro conjuro) {
        controllerConjuro.modificarConjuro(conjuro);
    }

    public void eliminarConjuro(int id) {
        controllerConjuro.eliminarConjuro(id);
    }
    public void borrarConjurosPorMago(int idMago) {
        controllerConjuro.borrarConjurosPorMago(idMago);
    }

    private List<Mago> magosEnCombate;
    private List<Monstruo> monstruosEnCombate;
    private Bosque bosqueActual;
    private int indiceMagoActual = 0;
    private StringBuilder logAcumulado = new StringBuilder();
    private Random random = new Random();

    // pruebas
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

    public void ejecutarTurno(String nombreHechizo) {

        if (magosEnCombate.isEmpty() || monstruosEnCombate.isEmpty()) {
            return;
        }

        Mago magoQueAtaca = magosEnCombate.get(indiceMagoActual);

        // Obtener hechizos conocidos por el mago desde ControllerConjuro
        List<Hechizo> conocidos = controllerConjuro.getHechizosPorMago(magoQueAtaca.getId());

        // Buscar si el nombreHechizo (botón pulsado) está en su lista
        Hechizo hechizoEncontrado = null;
        for (Hechizo h : conocidos) {
            if (h.getNombre().equals(nombreHechizo)) {
                hechizoEncontrado = h;
            }
        }

        if (hechizoEncontrado != null) {
            // Uso del método de tu clase Mago
            List<String> logsHechizo = magoQueAtaca.lanzarHechizo(monstruosEnCombate, hechizoEncontrado.getNombre());

            for (String log : logsHechizo) {
                logAcumulado.append(log);
            }

        } else {
            // No lo sabe: -1 de vida
            magoQueAtaca.setVida(magoQueAtaca.getVida() - 1);
            logAcumulado.append(magoQueAtaca.getNombre()).append(" no conoce ").append(nombreHechizo).append(" (-1 vida).\n");
        }

        // Comprobamos si el mago ha muerto por la penalización
        if (magoQueAtaca.getVida() <= 0) {
            logAcumulado.append(magoQueAtaca.getNombre()).append(" murió.\n");
            magosEnCombate.remove(magoQueAtaca);
            // Al removerlo, no incrementamos el índice para que el siguiente pase a ser el actual
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
