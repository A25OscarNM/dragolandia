package com.dragonlandia.vista;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.dragonlandia.modelo.Monstruo;

public class PanelBosque extends JPanel {

    public JTextField txtNombre;
    public JTextField txtNivelPeligro;
    public JComboBox<Monstruo> cbMonstruoJefe;
    public JButton btnSave;
    public ArrayList<Monstruo> monstruos = new ArrayList<>();

    public PanelBosque() {

        setBounds(0, 0, 900, 500);
        setLayout(null);

        JLabel lblTitle = new JLabel("Agregar bosque");

        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setForeground(new Color(0, 0, 0));
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

        JLabel lblNivel = new JLabel("Nivel de peligro");

        lblNivel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNivel.setBounds(21, 116, 150, 24);
        panel.add(lblNivel);

        JLabel lblJefe = new JLabel("Monstruo jefe");

        lblJefe.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblJefe.setBounds(21, 154, 150, 24);
        panel.add(lblJefe);

        txtNombre = new JTextField();

        txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNombre.setBounds(180, 81, 263, 24);
        panel.add(txtNombre);

        txtNivelPeligro = new JTextField();

        txtNivelPeligro.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNivelPeligro.setBounds(180, 120, 263, 24);
        panel.add(txtNivelPeligro);

        cbMonstruoJefe = new JComboBox<>();

        cbMonstruoJefe.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cbMonstruoJefe.setBounds(180, 155, 263, 24);
        panel.add(cbMonstruoJefe);

        for (Monstruo m : monstruos) {
            cbMonstruoJefe.addItem(m);
        }

        btnSave = new JButton("Agregar");
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSave.setBounds(200, 230, 100, 23);
        panel.add(btnSave);
    }

    public void recargarMonstruos(ArrayList<Monstruo> monstruos) {
        cbMonstruoJefe.removeAllItems();

        for (Monstruo m : monstruos) {
            cbMonstruoJefe.addItem(m);
        }
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtNivelPeligro.setText("");
        cbMonstruoJefe.setSelectedIndex(0);
    }
}
