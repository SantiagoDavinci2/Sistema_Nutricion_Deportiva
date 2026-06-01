package DLL;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import BLL.Pedido;
import BLL.Cliente;

public class PedidoController {

    public static void agregarPedido(Pedido pedido) {
        String sql = "INSERT INTO pedidos (cliente_id, tipoDeporte) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion(); 
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getCliente().getId());
            stmt.setString(2, pedido.getTipoDeporte());

            stmt.executeUpdate();
            System.out.println("Pedido agregado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Pedido> obtenerPedidos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";
        try (Connection con = Conexion.getConexion(); 
             Statement stmt = con.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int clienteId = rs.getInt("cliente_id");
                String tipoDeporte = rs.getString("tipoDeporte");

                Cliente cliente = ClienteController.obtenerClientePorId(clienteId);
                Pedido pedido = new Pedido(cliente, tipoDeporte);
                lista.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void actualizarPedido(int idPedido, String nuevoDeporte) {
        String sql = "UPDATE pedidos SET tipoDeporte = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion(); 
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nuevoDeporte);
            stmt.setInt(2, idPedido);
            stmt.executeUpdate();
            System.out.println("Pedido actualizado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarPedido(int idPedido) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (Connection con = Conexion.getConexion(); 
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
            System.out.println("Pedido eliminado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

