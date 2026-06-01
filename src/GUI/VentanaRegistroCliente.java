package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import DLL.Conexion;

public class VentanaRegistroCliente extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public VentanaRegistroCliente() {
        setTitle("Registro de Cliente");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 248, 255)); // Fondo azul claro suave

       
        JLabel titulo = new JLabel("Crear nueva cuenta", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(33, 60, 90));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

      
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsername = new JLabel("Nombre de usuario:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername = new JTextField();

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword = new JPasswordField();

        JButton btnRegistrar = crearBotonPrincipal("Registrarse");
        JButton btnVolver = crearBotonSecundario("Volver");

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(lblUsername, gbc);
        gbc.gridy++;
        panelForm.add(txtUsername, gbc);
        gbc.gridy++;
        panelForm.add(lblPassword, gbc);
        gbc.gridy++;
        panelForm.add(txtPassword, gbc);
        gbc.gridy++;
        panelForm.add(btnRegistrar, gbc);
        gbc.gridy++;
        panelForm.add(btnVolver, gbc);

        add(panelForm, BorderLayout.CENTER);

       
        btnRegistrar.addActionListener(e -> registrarCliente());

        btnVolver.addActionListener(e -> {
            new VentanaPrincipal();
            dispose();
        });

        setVisible(true);
    }

    private void registrarCliente() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completá todos los campos.");
            return;
        }

        try (Connection con = Conexion.getConexion()) {
            String sqlUsuario = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
            PreparedStatement stmtUsuario = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            stmtUsuario.setString(1, username);
            stmtUsuario.setString(2, password);
            stmtUsuario.executeUpdate();

            ResultSet rs = stmtUsuario.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);

                String sqlCliente = "INSERT INTO cliente (user_id, nombre, cantidadPersonas, restricciones, tipoDeporte) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmtCliente = con.prepareStatement(sqlCliente);
                stmtCliente.setInt(1, userId);
                stmtCliente.setString(2, username);
                stmtCliente.setInt(3, 0);              // por defecto
                stmtCliente.setString(4, "Ninguna");   // por defecto
                stmtCliente.setString(5, "");          // por defecto
                stmtCliente.executeUpdate();

                JOptionPane.showMessageDialog(this, "✅ Registro exitoso. Ahora podés iniciar sesión.");
                new VentanaLoginCliente();
                dispose();
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                JOptionPane.showMessageDialog(this, "⚠️ Ese nombre de usuario ya existe. Probá con otro.");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar cliente.");
                e.printStackTrace();
            }
        }
    }

    private JButton crearBotonPrincipal(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(150, 40));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setBackground(new Color(76, 175, 80)); // verde
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        return boton;
    }

    private JButton crearBotonSecundario(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(150, 35));
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setBackground(new Color(220, 220, 220)); // gris claro
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        return boton;
    }
}