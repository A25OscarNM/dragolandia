package com.dragonlandia.vista;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.dragonlandia.modelo.Bosque;

public class PanelDragon extends JPanel {

    public JTextField txtNombre;
    public JTextField txtIntensidadFuego;
    public JTextField txtResistencia;
    public JComboBox<Bosque> cbBosque;
    public JButton btnSave;
    public ArrayList<Bosque> bosques = new ArrayList<>();

    public PanelDragon() {

        setBounds(0, 0, 900, 500);
        setLayout(null);

        JLabel lblTitle = new JLabel("Agregar dragon");
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
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

        JLabel lblFuego = new JLabel("Intensidad fuego");

        lblFuego.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFuego.setBounds(21, 98, 150, 24);
        panel.add(lblFuego);

        JLabel lblResistencia = new JLabel("Resistencia");

        lblResistencia.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblResistencia.setBounds(21, 130, 150, 24);
        panel.add(lblResistencia);

        JLabel lblBosque = new JLabel("Bosque");

        lblBosque.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblBosque.setBounds(21, 166, 150, 24);
        panel.add(lblBosque);

        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNombre.setBounds(180, 57, 263, 24);
        panel.add(txtNombre);

        txtIntensidadFuego = new JTextField();
        txtIntensidadFuego.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtIntensidadFuego.setBounds(180, 98, 263, 24);
        panel.add(txtIntensidadFuego);

        txtResistencia = new JTextField();
        txtResistencia.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtResistencia.setBounds(180, 130, 263, 24);
        panel.add(txtResistencia);

        cbBosque = new JComboBox<>();
        cbBosque.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cbBosque.setBounds(180, 166, 263, 24);
        panel.add(cbBosque);

        for (Bosque b : bosques) {
            cbBosque.addItem(b);
        }

        btnSave = new JButton("Agregar");
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSave.setBounds(200, 230, 100, 23);
        panel.add(btnSave);
    }

    public void recargarBosques(ArrayList<Bosque> bosques) {
        cbBosque.removeAllItems();

        for (Bosque b : bosques) {
            cbBosque.addItem(b);
        }
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtIntensidadFuego.setText("");
        txtResistencia.setText("");
        cbBosque.setSelectedIndex(0);
    }
}