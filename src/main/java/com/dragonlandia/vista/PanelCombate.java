package com.dragonlandia.vista;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelCombate extends JPanel {
    public JLabel lblMago;
    public JButton btnBolaFuego, btnRayo, btnBolaNieve, btnMisil;
    public JTextArea logCombate;

    public PanelCombate() {
        setBounds(0, 0, 1150, 450);
        setLayout(null);

        lblMago = new JLabel("Mago: -");
        lblMago.setBounds(50, 10, 200, 30);
        add(lblMago);

        // Botones de hechizos
        btnBolaFuego = new JButton("Bola de Fuego");
        btnBolaFuego.setBounds(50, 350, 150, 40);
        add(btnBolaFuego);

        btnRayo = new JButton("Rayo");
        btnRayo.setBounds(210, 350, 150, 40);
        add(btnRayo);

        btnBolaNieve = new JButton("Bola de Nieve");
        btnBolaNieve.setBounds(370, 350, 150, 40);
        add(btnBolaNieve);

        btnMisil = new JButton("Misil Arcano");
        btnMisil.setBounds(530, 350, 150, 40);
        add(btnMisil);

        logCombate = new JTextArea();
        logCombate.setEditable(false);
        JScrollPane scroll = new JScrollPane(logCombate);
        scroll.setBounds(50, 50, 1050, 260);
        add(scroll);
    }
}