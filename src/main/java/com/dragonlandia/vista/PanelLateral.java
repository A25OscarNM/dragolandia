package com.dragonlandia.vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

public class PanelLateral extends JPanel {

    public JTable table;

    public PanelLateral() {
        setBounds(575, 0, 550, 500);
        setLayout(null);

        JLabel lblTitle = new JLabel("Monstruos disponibles: ");

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

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 450, 250);
        panel.add(scrollPane);

        table = new JTable();
        table.setModel(
                new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nombre", "Vida", "Tipo", "Fuerza" }) {
                    Class[] columnTypes = new Class[] { Integer.class, String.class, Integer.class, String.class,
                            Integer.class };

                    @Override
                    public Class getColumnClass(int columnIndex) {
                        return columnTypes[columnIndex];
                    }
                });
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(30);
        scrollPane.setViewportView(table);
    }

    public void addMonstruo(int id, String nombre, int vida, int tipo, int fuerza) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addRow(new Object[] { id, nombre, vida, tipo, fuerza });
    }
}