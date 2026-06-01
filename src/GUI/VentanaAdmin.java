package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import BLL.Pedido;
import BLL.Platillo;
import BLL.Cliente;
import DLL.PedidoController;
import DLL.ControllerPlatillo;
import DLL.ClienteController;

public class VentanaAdmin extends JFrame {

    public VentanaAdmin() {
        setTitle("Panel de Administrador");
        setSize(520, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 250, 255)); // Fondo claro

       
        JLabel titulo = new JLabel("Panel de Control - Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        titulo.setForeground(new Color(30, 60, 90));
        add(titulo, BorderLayout.NORTH);

        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        panel.setBackground(new Color(245, 250, 255));

        
        JButton btnVerPedidos = crearBoton("📋 Ver Pedidos");
        JButton btnCrearPlatillo = crearBoton("🍽️ Crear Platillo Personalizado");
        JButton btnVerPlatillos = crearBoton("👀 Ver Platillos");
        JButton btnEditarPlatillo = crearBoton("✏️ Editar Platillo");
        JButton btnEliminarPlatillo = crearBoton("❌ Eliminar Platillo");
        JButton btnEditarCliente = crearBoton("✏️ Editar Cliente");
        JButton btnEliminarCliente = crearBoton("🗑️ Eliminar Cliente");
        JButton btnCerrarSesion = crearBotonSecundario("🔙 Cerrar Sesión");


        panel.add(btnVerPedidos);
        panel.add(btnCrearPlatillo);
        panel.add(btnVerPlatillos);
        panel.add(btnEditarPlatillo);
        panel.add(btnEliminarPlatillo);
        panel.add(btnEditarCliente);
        panel.add(btnEliminarCliente);
        panel.add(btnCerrarSesion);

        add(panel, BorderLayout.CENTER);

       
        btnVerPedidos.addActionListener(e -> {
            List<Pedido> pedidos = PedidoController.obtenerPedidos();
            if (pedidos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay pedidos disponibles.");
                return;
            }
            StringBuilder mensaje = new StringBuilder("📦 Pedidos registrados:\n\n");
            for (Pedido p : pedidos) {
                mensaje.append("👤 Cliente: ").append(p.getCliente().getNombre()).append("\n");
                mensaje.append("🏋️‍♂️ Deporte: ").append(p.getTipoDeporte()).append("\n\n");
            }
            JOptionPane.showMessageDialog(this, mensaje.toString());
        });

        btnCrearPlatillo.addActionListener(e -> {
            new VentanaCrearPlatillo(); // Usás tu ventana bonita
        });

        btnVerPlatillos.addActionListener(e -> {
            List<Platillo> platillos = ControllerPlatillo.obtenerPlatillos();
            if (platillos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay platillos registrados.");
                return;
            }
            StringBuilder sb = new StringBuilder("🍽️ Platillos registrados:\n\n");
            for (Platillo p : platillos) {
                sb.append("📛 ").append(p.getNombre())
                  .append(" | 🏋️ ").append(p.getTipoDeporte())
                  .append(" | 🍽️ ").append(p.getTipoComida()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        btnEditarPlatillo.addActionListener(e -> {
            List<Platillo> platillos = ControllerPlatillo.obtenerPlatillos();
            if (platillos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay platillos.");
                return;
            }
            String[] opciones = platillos.stream().map(Platillo::getNombre).toArray(String[]::new);
            String nombreOriginal = (String) JOptionPane.showInputDialog(this, "Seleccioná platillo:", "Editar",
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (nombreOriginal != null) {
                Platillo p = platillos.stream().filter(pl -> pl.getNombre().equals(nombreOriginal)).findFirst().get();
                String nuevoNombre = JOptionPane.showInputDialog("Nombre:", p.getNombre());
                String deporte = JOptionPane.showInputDialog("Deporte:", p.getTipoDeporte());
                String restriccion = JOptionPane.showInputDialog("Restricción:", p.getRestricciones());
                String ingredientes = JOptionPane.showInputDialog("Ingredientes:", p.getIngredientes());
                String porcion = JOptionPane.showInputDialog("Porción:", p.getPorcion());
                String tipoComida = JOptionPane.showInputDialog("Tipo de comida:", p.getTipoComida());

                if (nuevoNombre == null || nuevoNombre.trim().isEmpty()
                        || deporte == null || deporte.trim().isEmpty()
                        || restriccion == null || restriccion.trim().isEmpty()
                        || ingredientes == null || ingredientes.trim().isEmpty()
                        || porcion == null || porcion.trim().isEmpty()
                        || tipoComida == null || tipoComida.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                    return;
                }

                Platillo actualizado = new Platillo(nuevoNombre.trim(), deporte.trim(), restriccion.trim(),
                        ingredientes.trim(), porcion.trim(), tipoComida.trim());
                ControllerPlatillo.actualizarPlatillo(nombreOriginal, actualizado);
                JOptionPane.showMessageDialog(this, "Platillo actualizado correctamente.");
            }
        });

        btnEliminarPlatillo.addActionListener(e -> {
            List<Platillo> platillos = ControllerPlatillo.obtenerPlatillos();
            if (platillos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay platillos.");
                return;
            }
            String[] opciones = platillos.stream().map(Platillo::getNombre).toArray(String[]::new);
            String elegido = (String) JOptionPane.showInputDialog(this, "Seleccioná un platillo:", "Eliminar",
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (elegido != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que querés eliminar \"" + elegido + "\"?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    ControllerPlatillo.eliminarPlatillo(elegido);
                    JOptionPane.showMessageDialog(this, "Platillo eliminado.");
                }
            }
        });

        btnEditarCliente.addActionListener(e -> {
            List<Cliente> clientes = ClienteController.obtenerClientes();
            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay clientes.");
                return;
            }
            String[] opciones = clientes.stream().map(c -> c.getId() + " - " + c.getNombre()).toArray(String[]::new);
            String seleccionado = (String) JOptionPane.showInputDialog(this, "Seleccioná cliente:", "Editar",
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (seleccionado != null) {
                int id = Integer.parseInt(seleccionado.split(" - ")[0]);
                Cliente c = ClienteController.obtenerClientePorId(id);

                String nuevoNombre = JOptionPane.showInputDialog("Nombre:", c.getNombre());
                String cantStr = JOptionPane.showInputDialog("Cantidad de personas:", String.valueOf(c.getCantidadPersonas()));
                String nuevaRestr = JOptionPane.showInputDialog("Restricciones:", c.getRestricciones());
                String nuevoDeporte = JOptionPane.showInputDialog("Deporte:", c.getTipoDeporte());

                if (nuevoNombre == null || cantStr == null || nuevaRestr == null || nuevoDeporte == null ||
                    nuevoNombre.trim().isEmpty() || cantStr.trim().isEmpty() || nuevaRestr.trim().isEmpty() || nuevoDeporte.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                    return;
                }

                try {
                    int cantidad = Integer.parseInt(cantStr.trim());
                    if (cantidad <= 0) {
                        JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero.");
                        return;
                    }
                    Cliente nuevoCliente = new Cliente(id, nuevoNombre.trim(), cantidad, nuevaRestr.trim(), nuevoDeporte.trim());
                    ClienteController.actualizarCliente(nuevoCliente);
                    JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Cantidad inválida.");
                }
            }
        });

        btnEliminarCliente.addActionListener(e -> {
            List<Cliente> clientes = ClienteController.obtenerClientes();
            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay clientes.");
                return;
            }
            String[] opciones = clientes.stream().map(c -> c.getId() + " - " + c.getNombre()).toArray(String[]::new);
            String seleccionado = (String) JOptionPane.showInputDialog(this, "Seleccioná cliente:", "Eliminar",
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (seleccionado != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que querés eliminar a " + seleccionado + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(seleccionado.split(" - ")[0]);
                    ClienteController.eliminarClienteYUsuario(id);
                    JOptionPane.showMessageDialog(this, "Cliente eliminado.");
                }
            }
        });

        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new VentanaPrincipal();
        });

        setVisible(true);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setBackground(new Color(33, 150, 243));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setBackground(new Color(220, 220, 220));
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }
}