package DLL;

import java.sql.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import BLL.Cliente;


public class ClienteController {
	
	public static int guardarCliente(Cliente cliente) {
	    
	    Cliente existente = obtenerClientePorUserId(cliente.getUser_id());
	    if (existente != null) {
	        System.out.println("Ya existe un cliente con ese user_id. No se guarda nuevamente.");
	        return existente.getId(); 
	    }

	    String sql = "INSERT INTO cliente(nombre, cantidadPersonas, restricciones, tipoDeporte, user_id) VALUES (?, ?, ?, ?, ?)";
	    try (Connection conn = Conexion.getConexion();
	         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        stmt.setString(1, cliente.getNombre());
	        stmt.setInt(2, cliente.getCantidadPersonas());
	        stmt.setString(3, cliente.getRestricciones());
	        stmt.setString(4, cliente.getTipoDeporte());
	        stmt.setInt(5, cliente.getUser_id());

	        stmt.executeUpdate();

	        ResultSet rs = stmt.getGeneratedKeys();
	        if (rs.next()) {
	            return rs.getInt(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return -1;
	}


    public static Cliente obtenerClientePorUserId(int userId) {
        String sql = "SELECT * FROM cliente WHERE user_id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Cliente encontrado para user_id=" + userId);
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidadPersonas");
                String restr = rs.getString("restricciones");
                String deporte = rs.getString("tipoDeporte");

                return new Cliente(id, nombre, cantidad, restr, deporte, userId);
            } else {
                System.out.println("No se encontró cliente para user_id=" + userId);
            }

        } catch (Exception e) {
            System.out.println("Error al buscar cliente por user_id: " + e.getMessage());
        }
        return null;
    }
    
    public static Cliente obtenerClientePorId(int id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidadPersonas");
                String restr = rs.getString("restricciones");
                String deporte = rs.getString("tipoDeporte");
                int userId = rs.getInt("user_id");

                return new Cliente(id, nombre, cantidad, restr, deporte, userId);
            }

        } catch (Exception e) {
            System.out.println("Error al obtener cliente por ID: " + e.getMessage());
        }
        return null;
    }

    public static List<Cliente> obtenerClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("cantidadPersonas"),
                    rs.getString("restricciones"),
                    rs.getString("tipoDeporte"),
                    rs.getInt("user_id")
                );
                lista.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener lista de clientes: " + e.getMessage());
        }
        return lista;
    }

    public static void actualizarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre = ?, cantidadPersonas = ?, restricciones = ?, tipoDeporte = ? WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setInt(2, cliente.getCantidadPersonas());
            stmt.setString(3, cliente.getRestricciones());
            stmt.setString(4, cliente.getTipoDeporte());
            stmt.setInt(5, cliente.getId());

            stmt.executeUpdate();
            System.out.println("Cliente actualizado.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    public static void eliminarClienteYUsuario(int clienteId) {
        try (Connection con = Conexion.getConexion()) {

            // 1. Eliminar pedidos del cliente
            String sqlPedidos = "DELETE FROM pedidos WHERE cliente_id = ?";
            try (PreparedStatement stmtPedidos = con.prepareStatement(sqlPedidos)) {
                stmtPedidos.setInt(1, clienteId);
                stmtPedidos.executeUpdate();
            }

            // 2. Obtener el user_id del cliente
            int userId = -1;
            String obtenerUserId = "SELECT user_id FROM cliente WHERE id = ?";
            try (PreparedStatement stmtUser = con.prepareStatement(obtenerUserId)) {
                stmtUser.setInt(1, clienteId);
                ResultSet rs = stmtUser.executeQuery();
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                }
            }

            // 3. Eliminar cliente
            String sqlCliente = "DELETE FROM cliente WHERE id = ?";
            try (PreparedStatement stmtCliente = con.prepareStatement(sqlCliente)) {
                stmtCliente.setInt(1, clienteId);
                stmtCliente.executeUpdate();
            }

            // 4. Eliminar usuario
            if (userId != -1) {
                String sqlUsuario = "DELETE FROM usuarios WHERE id = ?";
                try (PreparedStatement stmtUsuario = con.prepareStatement(sqlUsuario)) {
                    stmtUsuario.setInt(1, userId);
                    stmtUsuario.executeUpdate();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cliente y usuario.");
            e.printStackTrace();
        }
    }
    
    
public static boolean loginCliente(String username, String password) {
    String sql = "SELECT * FROM cliente WHERE username = ? AND password = ?";
    try (Connection conn = Conexion.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.setString(2, password); 
        ResultSet rs = stmt.executeQuery();
        return rs.next(); 
    } catch (SQLException e) {
        System.out.println("Error en login cliente: " + e.getMessage());
    }
    return false;
}
}





