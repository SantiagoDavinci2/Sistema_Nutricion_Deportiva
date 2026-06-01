package GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import DLL.Conexion;

public class VentanaLoginCliente extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    public static int userIdClienteLogueado = -1;

    public VentanaLoginCliente() {
        setTitle("Login de Cliente");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 255, 250)); // verde claro

       
        JLabel titulo = new JLabel("Iniciar sesión como Cliente", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));
        titulo.setForeground(new Color(34, 70, 60));
        add(titulo, BorderLayout.NORTH);

        
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsername = new JLabel("Usuario:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername = new JTextField();

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword = new JPasswordField();

        JButton btnLogin = crearBoton("Iniciar sesión");
        JButton btnVolver = crearBotonSecundario("Volver");

       
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(lblUsername, gbc);
        gbc.gridy++;
        panelForm.add(txtUsername, gbc);
        gbc.gridy++;
        panelForm.add(lblPassword, gbc);
        gbc.gridy++;
        panelForm.add(txtPassword, gbc);
        gbc.gridy++;
        panelForm.add(btnLogin, gbc);
        gbc.gridy++;
        panelForm.add(btnVolver, gbc);

        add(panelForm, BorderLayout.CENTER);

       
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

          
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresá tu usuario.");
                txtUsername.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresá tu contraseña.");
                txtPassword.requestFocus();
                return;
            }

            if (username.length() < 3) {
                JOptionPane.showMessageDialog(this, "El usuario debe tener al menos 3 caracteres.");
                txtUsername.requestFocus();
                return;
            }

            if (password.length() < 4) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres.");
                txtPassword.requestFocus();
                return;
            }

            if (validarCliente(username, password)) {
                JOptionPane.showMessageDialog(this, "¡Bienvenido " + username + "!");
                new VentanaCliente();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
                txtPassword.setText("");
                txtPassword.requestFocus();
            }
        });

        btnVolver.addActionListener(e -> {
            new VentanaPrincipal();
            dispose();
        });

        setVisible(true);
    }

    private boolean validarCliente(String username, String password) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userIdClienteLogueado = rs.getInt("id");
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.");
            e.printStackTrace();
        }
        return false;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(150, 40));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setBackground(new Color(76, 175, 80));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        return boton;
    }

    private JButton crearBotonSecundario(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(150, 35));
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setBackground(new Color(220, 220, 220));
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        return boton;
    }
}