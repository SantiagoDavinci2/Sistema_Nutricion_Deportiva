package GUI;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Sistema de Nutrición Deportiva");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 255, 250)); 
      
        JLabel titulo = new JLabel("Bienvenido al Sistema de Nutrición Deportiva", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        titulo.setForeground(new Color(34, 70, 60));
        add(titulo, BorderLayout.NORTH);

       
        JPanel panelCentral = new JPanel();
        panelCentral.setOpaque(false);
        panelCentral.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); 

        JButton btnLoginCliente = crearBoton("Cliente");
        JButton btnLoginAdmin = crearBoton("Admin");
        JButton btnRegistroCliente = crearBoton("Registrarse");

        gbc.gridx = 0;
        panelCentral.add(btnLoginCliente, gbc);

        gbc.gridx = 1;
        panelCentral.add(btnLoginAdmin, gbc);

        gbc.gridx = 2;
        panelCentral.add(btnRegistroCliente, gbc);

        add(panelCentral, BorderLayout.CENTER);

   
        btnLoginCliente.addActionListener(e -> {
            new VentanaLoginCliente();
            dispose();
        });

        btnLoginAdmin.addActionListener(e -> {
            new VentanaLoginAdmin();
            dispose();
        });

        btnRegistroCliente.addActionListener(e -> {
            new VentanaRegistroCliente();
            dispose();
        });

        setVisible(true);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(150, 50));
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        boton.setBackground(new Color(76, 175, 80)); 
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(56, 142, 60)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return boton;
    }
}