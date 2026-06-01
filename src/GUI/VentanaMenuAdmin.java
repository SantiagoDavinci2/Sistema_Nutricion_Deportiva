package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import BLL.Pedido;
import BLL.Platillo;
import DLL.PedidoController;
import DLL.ControllerPlatillo;
import java.util.List;

public class VentanaMenuAdmin extends JFrame {
    public VentanaMenuAdmin() {
        setTitle("Menú Administrador");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JButton btnVerPedidos = new JButton("Ver Pedidos");
        JButton btnCrearPlatillo = new JButton("Crear Platillo");
        JButton btnVerPlatillos = new JButton("Ver Platillos");

        btnVerPedidos.addActionListener(e -> {
            List<Pedido> pedidos = PedidoController.obtenerPedidos();
            StringBuilder sb = new StringBuilder();
            for (Pedido p : pedidos) {
                sb.append("Cliente: ").append(p.getCliente().getNombre())
                  .append(" - Deporte: ").append(p.getTipoDeporte()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        btnCrearPlatillo.addActionListener(e -> new VentanaCrearPlatillo());

        btnVerPlatillos.addActionListener(e -> {
            List<Platillo> lista = ControllerPlatillo.obtenerPlatillos();
            StringBuilder sb = new StringBuilder();
            for (Platillo p : lista) {
                sb.append(p.getNombre()).append(" - ").append(p.getTipoComida()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(btnVerPedidos);
        panel.add(btnCrearPlatillo);
        panel.add(btnVerPlatillos);

        add(panel);
        setVisible(true);
    }
}