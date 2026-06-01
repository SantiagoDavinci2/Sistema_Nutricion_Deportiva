package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import BLL.Pedido;
import BLL.Platillo;
import DLL.PedidoController;
import DLL.ControllerPlatillo;

public class VentanaCrearPlatillo extends JFrame {

    private JComboBox<String> comboPedidos;
    private JTextField txtNombre, txtIngredientes, txtPorcion, txtTipoComida;
    private JTextArea txtDatosCliente;
    private List<Pedido> pedidos;

    public VentanaCrearPlatillo() {
        setTitle("Crear Platillo Personalizado");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        pedidos = PedidoController.obtenerPedidos();

        comboPedidos = new JComboBox<>();
        for (Pedido p : pedidos) {
            String resumen = p.getCliente().getNombre() + " - " + p.getCliente().getTipoDeporte();
            comboPedidos.addItem(resumen);
        }

        txtDatosCliente = new JTextArea(5, 30);
        txtDatosCliente.setEditable(false);
        comboPedidos.addActionListener(e -> mostrarDatosCliente(comboPedidos.getSelectedIndex()));

        JPanel panelCentro = new JPanel(new GridLayout(6, 2, 5, 5));
        txtNombre = new JTextField();
        txtIngredientes = new JTextField();
        txtPorcion = new JTextField();
        txtTipoComida = new JTextField();

        panelCentro.add(new JLabel("Nombre del platillo:"));
        panelCentro.add(txtNombre);
        panelCentro.add(new JLabel("Ingredientes:"));
        panelCentro.add(txtIngredientes);
        panelCentro.add(new JLabel("Porción:"));
        panelCentro.add(txtPorcion);
        panelCentro.add(new JLabel("Tipo de comida:"));
        panelCentro.add(txtTipoComida);

        JButton btnGuardar = new JButton("Guardar Platillo");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPlatillo();
            }
        });

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(new JLabel("Seleccionar Pedido:"), BorderLayout.NORTH);
        panelNorte.add(comboPedidos, BorderLayout.CENTER);
        panelNorte.add(new JScrollPane(txtDatosCliente), BorderLayout.SOUTH);

        add(panelNorte, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(btnGuardar, BorderLayout.SOUTH);

        if (!pedidos.isEmpty()) {
            comboPedidos.setSelectedIndex(0);
            mostrarDatosCliente(0);
        } else {
            JOptionPane.showMessageDialog(this, "No hay pedidos disponibles para crear platillos.");
            btnGuardar.setEnabled(false);
            comboPedidos.setEnabled(false);
        }

        setVisible(true);
    }

    private void mostrarDatosCliente(int index) {
        if (index >= 0 && index < pedidos.size()) {
            Pedido pedido = pedidos.get(index);
            String datos = "Cliente: " + pedido.getCliente().getNombre() + "\n"
                         + "Personas: " + pedido.getCliente().getCantidadPersonas() + "\n"
                         + "Restricciones: " + pedido.getCliente().getRestricciones() + "\n"
                         + "Deporte: " + pedido.getTipoDeporte();
            txtDatosCliente.setText(datos);
        } else {
            txtDatosCliente.setText("");
        }
    }

    private void guardarPlatillo() {
        int index = comboPedidos.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un pedido primero.");
            return;
        }

        Pedido pedido = pedidos.get(index);

        String nombre = txtNombre.getText().trim();
        String ingredientes = txtIngredientes.getText().trim();
        String porcion = txtPorcion.getText().trim();
        String tipoComida = txtTipoComida.getText().trim();

        // Validaciones de campos vacíos
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del platillo no puede estar vacío.");
            txtNombre.requestFocus();
            return;
        }
        if (ingredientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los ingredientes no pueden estar vacíos.");
            txtIngredientes.requestFocus();
            return;
        }
        if (porcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La porción no puede estar vacía.");
            txtPorcion.requestFocus();
            return;
        }
        if (tipoComida.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El tipo de comida no puede estar vacío.");
            txtTipoComida.requestFocus();
            return;
        }


        Platillo platillo = new Platillo(
                nombre,
                pedido.getTipoDeporte(),
                pedido.getCliente().getRestricciones(),
                ingredientes,
                porcion,
                tipoComida
        );

        ControllerPlatillo.agregarPlatillo(platillo, pedido.getCliente().getId());
        JOptionPane.showMessageDialog(this, "Platillo creado exitosamente.");
        dispose();
    }
}