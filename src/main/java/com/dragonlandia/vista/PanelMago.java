package com.dragonlandia.vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class PanelMago extends JPanel {

    public JTextField txtNombre;
    public JTextField txtVida;
    public JTextField txtNivelMagia;
    public JButton btnSave;
    private int optSelected = 1;

    public PanelMago() {

        setBounds(0, 0, 900, 500);
        setLayout(null);

        JLabel lblTitle = new JLabel("Agrega un mago:");

        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setForeground(new Color(0, 0, 0));
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setBounds(20, 11, 500, 60);
        add(lblTitle);

        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel.setBounds(20, 71, 500, 284);
        panel.setLayout(null);
        add(panel);

        JLabel lblNombre = new JLabel("Nombre");

        lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNombre.setBounds(21, 81, 150, 24);
        panel.add(lblNombre);

        JLabel lblVida = new JLabel("Vida");

        lblVida.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblVida.setBounds(21, 116, 150, 24);
        panel.add(lblVida);

        JLabel lblNivelMagia = new JLabel("Nivel de magia");

        lblNivelMagia.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNivelMagia.setBounds(21, 154, 150, 24);
        panel.add(lblNivelMagia);

        txtNombre = new JTextField();

        txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNombre.setColumns(10);
        txtNombre.setBounds(180, 81, 263, 24);
        panel.add(txtNombre);

        txtVida = new JTextField();

        txtVida.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtVida.setColumns(10);
        txtVida.setBounds(180, 120, 263, 24);
        panel.add(txtVida);

        txtNivelMagia = new JTextField();

        txtNivelMagia.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNivelMagia.setColumns(10);
        txtNivelMagia.setBounds(180, 155, 263, 24);
        panel.add(txtNivelMagia);

        btnSave = new JButton("Agregar");

        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSave.setBounds(200, 230, 100, 23);
        panel.add(btnSave);
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtVida.setText("");
        txtNivelMagia.setText("");
    }
}
