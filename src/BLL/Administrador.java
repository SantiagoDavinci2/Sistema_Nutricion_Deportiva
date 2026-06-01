
package BLL;
import javax.swing.*;
import java.util.List;
import DLL.PedidoController;
import DLL.ControllerPlatillo;
import DLL.ClienteController;
import BLL.Platillo;
import BLL.Pedido;

public class Administrador {

    public void recibirPedido(Pedido pedido) {
        
        Cliente cliente = pedido.getCliente();
        ClienteController.guardarCliente(cliente);

       
        PedidoController.agregarPedido(pedido);

        JOptionPane.showMessageDialog(null, "¡Pedido recibido!");
    }

    public void mostrarPedidos() {
        List<Pedido> pedidos = PedidoController.obtenerPedidos();
        if (pedidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay pedidos aún.");
        } else {
            StringBuilder sb = new StringBuilder("PEDIDOS:\n");
            for (Pedido p : pedidos) {
                sb.append(p.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }


    public void mostrarPlatillos() {
        List<Platillo> platillos = ControllerPlatillo.obtenerPlatillos();
        if (platillos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay platillos cargados.");
        } else {
            StringBuilder sb = new StringBuilder("PLATILLOS:\n");
            for (Platillo p : platillos) {
                sb.append(p.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }
}
