package GUI;

import javax.swing.*;
import java.awt.*;
import BLL.Administrador;
import DLL.AdministradorController;

public class VentanaLoginAdmin extends JFrame {

    public VentanaLoginAdmin() {
        setTitle("Login Administrador");
        setSize(420, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255)); // azul muy claro

      
        JLabel titulo = new JLabel("Iniciar sesión como Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(new Color(25, 60, 100));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));
        add(titulo, BorderLayout.NORTH);

        
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtUsuario = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton btnLogin = crearBoton("Iniciar sesión");
        JButton btnVolver = crearBotonSecundario("Volver");

        // Agregar componentes
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(lblUsuario, gbc);
        gbc.gridy++;
        panelForm.add(txtUsuario, gbc);
        gbc.gridy++;
        panelForm.add(lblPass, gbc);
        gbc.gridy++;
        panelForm.add(txtPassword, gbc);
        gbc.gridy++;
        panelForm.add(btnLogin, gbc);
        gbc.gridy++;
        panelForm.add(btnVolver, gbc);

        add(panelForm, BorderLayout.CENTER);

       
        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (usuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresá el nombre de usuario.");
                txtUsuario.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresá la contraseña.");
                txtPassword.requestFocus();
                return;
            }

            if (AdministradorController.login(usuario, password)) {
                JOptionPane.showMessageDialog(this, "Login correcto.");
                new VentanaAdmin();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
                txtPassword.setText("");
            }
        });

        btnVolver.addActionListener(e -> {
            new VentanaPrincipal();
            dispose();
        });

        setVisible(true);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(160, 40));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setBackground(new Color(33, 150, 243));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        return boton;
    }

    private JButton crearBotonSecundario(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(160, 35));
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setBackground(new Color(220, 220, 220));
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        return boton;
    }
}
