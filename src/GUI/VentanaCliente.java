package GUI;

import javax.swing.*;
import DLL.ClienteController;
import BLL.Cliente;
import BLL.Pedido;
import BLL.Administrador;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DLL.Conexion;

public class VentanaCliente extends JFrame {

    public VentanaCliente() {
        setTitle("Panel del Cliente");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane pestañas = new JTabbedPane();

     
        JPanel panelPedido = new JPanel(new GridBagLayout());
        panelPedido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCantidad = new JLabel("Cantidad de personas:");
        JTextField txtCantidad = new JTextField();

        JLabel lblRestricciones = new JLabel("Restricciones alimenticias:");
        String[] restricciones = {"Ninguna", "Vegano", "Celíaco"};
        JComboBox<String> cbRestricciones = new JComboBox<>(restricciones);

        JLabel lblDeporte = new JLabel("Tipo de deporte:");
        String[] deportes = {"Fútbol", "Boxeo", "Natación", "Ciclismo", "CrossFit"};
        JComboBox<String> cbDeportes = new JComboBox<>(deportes);

        JButton btnEnviar = new JButton("Enviar Pedido");

        gbc.gridx = 0; gbc.gridy = 0;
        panelPedido.add(lblCantidad, gbc);
        gbc.gridx = 1;
        panelPedido.add(txtCantidad, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelPedido.add(lblRestricciones, gbc);
        gbc.gridx = 1;
        panelPedido.add(cbRestricciones, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelPedido.add(lblDeporte, gbc);
        gbc.gridx = 1;
        panelPedido.add(cbDeportes, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        panelPedido.add(btnEnviar, gbc);

        pestañas.addTab("Realizar Pedido", panelPedido);

        // Panel de ver platillos personalizados
        JPanel panelPlatillos = new JPanel(new BorderLayout());
        JTextArea areaPlatillos = new JTextArea();
        areaPlatillos.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaPlatillos);
        panelPlatillos.add(scroll, BorderLayout.CENTER);

        JButton btnCargarPlatillos = new JButton("Ver mis platillos personalizados");
        panelPlatillos.add(btnCargarPlatillos, BorderLayout.SOUTH);

        pestañas.addTab("Ver Platillos", panelPlatillos);

        add(pestañas);

      
        btnEnviar.addActionListener(e -> {
            try {
                String cantidadText = txtCantidad.getText().trim();
                if (cantidadText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor ingresa la cantidad de personas.");
                    return;
                }
                int cantidad = Integer.parseInt(cantidadText);
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero.");
                    return;
                }

                String restriccion = (String) cbRestricciones.getSelectedItem();
                String deporte = (String) cbDeportes.getSelectedItem();

                if (restriccion == null || restriccion.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor selecciona una restricción alimenticia.");
                    return;
                }
                if (deporte == null || deporte.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor selecciona un tipo de deporte.");
                    return;
                }

                Cliente cliente = ClienteController.obtenerClientePorUserId(VentanaLoginCliente.userIdClienteLogueado);
                if (cliente != null) {
                    
                    cliente.setcantidadPersonas(cantidad);
                    cliente.setRestricciones(restriccion);
                    cliente.setTipoDeporte(deporte);
                    ClienteController.actualizarCliente(cliente);
                } else {
                    
                    cliente = new Cliente("NombreDesconocido", cantidad, restriccion, deporte);
                    cliente.setUser_id(VentanaLoginCliente.userIdClienteLogueado);
                    cliente.setId(ClienteController.guardarCliente(cliente));
                }

                Pedido pedido = new Pedido(cliente, deporte);
                Administrador admin = new Administrador();
                admin.recibirPedido(pedido);

                JOptionPane.showMessageDialog(this, "Pedido realizado correctamente.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado al realizar el pedido.");
                ex.printStackTrace();
            }
        });

       
        btnCargarPlatillos.addActionListener(e -> {
            areaPlatillos.setText("");
            try (Connection con = Conexion.getConexion()) {
                String sql = "SELECT nombre, ingredientes, porcion, tipoComida FROM platillo WHERE cliente_id = (SELECT id FROM cliente WHERE user_id = ? LIMIT 1)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, VentanaLoginCliente.userIdClienteLogueado);
                ResultSet rs = stmt.executeQuery();
                boolean hayDatos = false;
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String ingredientes = rs.getString("ingredientes");
                    String porcion = rs.getString("porcion");
                    String tipoComida = rs.getString("tipoComida");

                    areaPlatillos.append("🍽️ Platillo: " + nombre + "\n");
                    areaPlatillos.append("   🥦 Ingredientes: " + ingredientes + "\n");
                    areaPlatillos.append("   🍚 Porción: " + porcion + "\n");
                    areaPlatillos.append("   🕘 Tipo de comida: " + tipoComida + "\n\n");

                    hayDatos = true;
                }
                if (!hayDatos) {
                    areaPlatillos.setText("No se encontraron platillos personalizados aún.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al obtener platillos.");
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}