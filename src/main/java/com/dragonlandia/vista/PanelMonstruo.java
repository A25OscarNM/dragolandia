package com.dragonlandia.vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class PanelMonstruo extends JPanel {

    public JTextField txtNombre;
    public JTextField txtVida;
    public JButton btnSave;
    public JComboBox<String> cbTipo;

    public PanelMonstruo() {

        setBounds(0, 0, 900, 500);
        setLayout(null);

        JLabel lblTitle = new JLabel("Agrega un monstruo:");

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

        JLabel lblTipo = new JLabel("Tipo");

        lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTipo.setBounds(21, 154, 150, 24);
        panel.add(lblTipo);

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

        cbTipo = new JComboBox<>(new String[] { "Ogro", "Troll", "Espectro" });
        cbTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cbTipo.setBounds(180, 155, 263, 24);
        panel.add(cbTipo);

        btnSave = new JButton("Agregar");

        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSave.setBounds(200, 230, 100, 24);
        panel.add(btnSave);
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtVida.setText("");
        cbTipo.setSelectedIndex(0);
    }
}