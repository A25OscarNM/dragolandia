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
    public JTextField txtFuerza;
    public JButton btnSave;
    public JComboBox<String> cbTipo;
    private int optSelected = 1;

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
        lblNombre.setBounds(21, 57, 150, 24);
        panel.add(lblNombre);

        JLabel lblVida = new JLabel("Vida");

        lblVida.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblVida.setBounds(21, 98, 150, 24);
        panel.add(lblVida);

        JLabel lblFuerza = new JLabel("Fuerza");

        lblFuerza.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFuerza.setBounds(21, 130, 150, 24);
        panel.add(lblFuerza);

        JLabel lblTipo = new JLabel("Tipo");

        lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTipo.setBounds(21, 166, 150, 24);
        panel.add(lblTipo);

        txtNombre = new JTextField();

        txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNombre.setColumns(10);
        txtNombre.setBounds(180, 57, 263, 24);
        panel.add(txtNombre);

        txtVida = new JTextField();

        txtVida.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtVida.setColumns(10);
        txtVida.setBounds(180, 98, 263, 24);
        panel.add(txtVida);

        txtFuerza = new JTextField();

        txtFuerza.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtFuerza.setColumns(10);
        txtFuerza.setBounds(180, 130, 263, 24);
        panel.add(txtFuerza);

        cbTipo = new JComboBox<>(new String[]{"Elemental", "Bestia", "Robot"});
        cbTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cbTipo.setBounds(180, 166, 263, 24);
        panel.add(cbTipo);

        btnSave = new JButton("Agregar");

        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSave.setBounds(200, 0xd8, 100, 24);
        panel.add(btnSave);
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtVida.setText("");
        txtFuerza.setText("");
        cbTipo.setSelectedIndex(0);
    }
}
