package com.dragonlandia.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.dragonlandia.controllers.Controller;
import com.dragonlandia.modelo.Bosque;
import com.dragonlandia.modelo.Hechizo;
import com.dragonlandia.modelo.Mago;
import com.dragonlandia.modelo.Monstruo;

public class DragoVista extends JFrame {

    JMenuBar menuBar;
    JMenu menu1;
    JMenuItem menu2, menu3, menu4, menu5, menu6;
    private PanelMago panelMago;
    private PanelMonstruo panelMonstruo;
    private PanelBosque panelBosque;
    private PanelDragon panelDragon;
    private PanelLateral panelLateral;
    private PanelCombate panelCombate;
    public Controller app;

    public DragoVista(Controller app) {
        this.app = app;

        setTitle("Dragolandia");
        setBounds(500, 500, 1200, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Crear paneles
        panelMago = new PanelMago();
        panelMonstruo = new PanelMonstruo();
        panelBosque = new PanelBosque();
        panelDragon = new PanelDragon();
        panelLateral = new PanelLateral();
        panelCombate = new PanelCombate();

        add(panelMago);
        add(panelMonstruo);
        add(panelBosque);
        add(panelDragon);
        add(panelLateral);
        add(panelCombate);

        // Mostrar solo Mago por defecto
        panelMago.setVisible(true);
        panelMonstruo.setVisible(false);
        panelBosque.setVisible(false);
        panelBosque.recargarMonstruos(app.getMonstruos());
        panelDragon.setVisible(false);
        panelDragon.recargarBosques(app.getBosques());
        panelLateral.setVisible(false);
        panelCombate.setVisible(false);

        panelMago.btnSave.addActionListener(e -> {
            String nombre = panelMago.txtNombre.getText();
            int vida = Integer.parseInt(panelMago.txtVida.getText());
            int nivelMagia = Integer.parseInt(panelMago.txtNivelMagia.getText());

            Mago mago = app.crearMago(nombre, vida, nivelMagia);
            panelMago.limpiarCampos();

            ArrayList<Hechizo> hechizos = app.getHechizos();
            Random rand = new Random();

            int nHechizo1 = rand.nextInt(hechizos.size());
            int nHechizo2 = rand.nextInt(hechizos.size());

            while (nHechizo1 == nHechizo2) {
                nHechizo2 = rand.nextInt(hechizos.size());
            }

            app.crearConjuro(mago, hechizos.get(nHechizo1));
            app.crearConjuro(mago, hechizos.get(nHechizo2));

        });

        panelMonstruo.btnSave.addActionListener(e -> {
            String nombre = panelMonstruo.txtNombre.getText();
            int vida = Integer.parseInt(panelMonstruo.txtVida.getText());
            int tipo = panelMonstruo.cbTipo.getSelectedIndex();

            app.crearMonstruo(nombre, vida, tipo);
            panelMonstruo.limpiarCampos();
            panelBosque.recargarMonstruos(app.getMonstruos());
        });

        panelBosque.btnSave.addActionListener(e -> {
            String nombre = panelBosque.txtNombre.getText();
            int nivelPeligro = Integer.parseInt(panelBosque.txtNivelPeligro.getText());
            Monstruo jefe = (Monstruo) panelBosque.cbMonstruoJefe.getSelectedItem();
            ArrayList<Monstruo> listaMonstruos = new ArrayList<>();

            for (int i = 0; i < PanelLateral.table.getRowCount(); i++) {
                if ((boolean) PanelLateral.table.getModel().getValueAt(i, 5)) {
                    listaMonstruos.add(app.buscarMonstruo((int) PanelLateral.table.getModel().getValueAt(i, 0)));
                }
            }

            app.crearBosque(nombre, nivelPeligro, jefe, listaMonstruos);
            panelBosque.limpiarCampos();
            panelLateral.resetTable();
            app.showAllMonstruos();
            panelDragon.recargarBosques(app.getBosques());
        });

        panelDragon.btnSave.addActionListener(e -> {
            String nombre = panelDragon.txtNombre.getText();
            int resistencia = Integer.parseInt(panelDragon.txtResistencia.getText());
            int intensidadFuego = Integer.parseInt(panelDragon.txtIntensidadFuego.getText());
            Bosque bosque = (Bosque) panelDragon.cbBosque.getSelectedItem();

            app.crearDragon(nombre, resistencia, intensidadFuego, bosque);
            panelDragon.limpiarCampos();
        });

        panelCombate.btnBolaFuego.addActionListener(e -> {
            app.ejecutarTurno("Bola de fuego");
        });

        panelCombate.btnRayo.addActionListener(e -> {
            app.ejecutarTurno("Rayo");
        });

        panelCombate.btnBolaNieve.addActionListener(e -> {
            app.ejecutarTurno("Bola de nieve");
        });

        panelCombate.btnMisil.addActionListener(e -> {
            app.ejecutarTurno("Misil arcano");
        });

        // Menú
        crearMenu();
    }

    /** 
     * @param msg
     */
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void crearMenu() {

        /* Creamos el JMenuBar y lo asociamos con el JFrame */
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        /*
         * Creamos el primer JMenu y lo pasamos como parámetro al JMenuBar mediante el
         * método add
         */
        menu1 = new JMenu("Crear");
        menu2 = new JMenuItem("Jugar");
        menuBar.add(menu1);
        menuBar.add(menu2);

        menu2.addActionListener(e -> {

            Bosque bosqueSelect = (Bosque) JOptionPane.showInputDialog(this, "Selecciona el Bosque", "Partida",
                    JOptionPane.QUESTION_MESSAGE, null, app.getBosques().toArray(), null);

            if (bosqueSelect != null) {
                cambioVista();
                panelCombate.setVisible(true);
                app.prepararCombate(bosqueSelect);
                validate();
                repaint();
            }
        });

        menu3 = new JMenuItem("Mago");
        menu1.add(menu3);
        menu4 = new JMenuItem("Monstruo");
        menu1.add(menu4);
        menu5 = new JMenuItem("Bosque");
        menu1.add(menu5);
        menu6 = new JMenuItem("Dragon");
        menu1.add(menu6);

        menu3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                cambioVista();
                panelMago.setVisible(true);
                validate();
                repaint();
            }
        });

        menu4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                cambioVista();
                panelMonstruo.setVisible(true);
                validate();
                repaint();
            }
        });

        menu5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                cambioVista();
                panelBosque.setVisible(true);
                panelLateral.setVisible(true);
                panelLateral.resetTable();
                app.showAllMonstruos();
                validate();
                repaint();
            }
        });

        menu6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                cambioVista();
                panelDragon.setVisible(true);
                validate();
                repaint();
            }
        });
    }

    /** 
     * @return PanelLateral
     */
    public PanelLateral getPanelLateral() {
        return panelLateral;
    }

    /** 
     * @return PanelCombate
     */
    public PanelCombate getPanelCombate() {
        return panelCombate;
    }

    public void load() {
        app.showAllMonstruos();
    }

    public void cambioVista() {
        panelMago.setVisible(false);
        panelMonstruo.setVisible(false);
        panelBosque.setVisible(false);
        panelDragon.setVisible(false);
        panelLateral.setVisible(false);
        panelCombate.setVisible(false);
    }

}
