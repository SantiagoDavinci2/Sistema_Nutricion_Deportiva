package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

import BLL.Platillo;

public class ControllerPlatillo {



	public static void agregarPlatillo(Platillo platillo, int clienteId) {
	    String sql = "INSERT INTO platillo (nombre, tipoDeporte, restricciones, ingredientes, porcion, tipoComida, cliente_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (Connection con = Conexion.getConexion();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setString(1, platillo.getNombre());
	        stmt.setString(2, platillo.getTipoDeporte());
	        stmt.setString(3, platillo.getRestricciones());
	        stmt.setString(4, platillo.getIngredientes());
	        stmt.setString(5, platillo.getPorcion());
	        stmt.setString(6, platillo.getTipoComida());
	        stmt.setInt(7, clienteId); 

	        stmt.executeUpdate();
	        System.out.println("Platillo agregado correctamente.");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static List<Platillo> obtenerPlatillos() {
	    List<Platillo> platillos = new ArrayList<>();

	    String sql = "SELECT * FROM platillo";

	    try (Connection con = Conexion.getConexion();
	         PreparedStatement stmt = con.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            Platillo p = new Platillo(
	                rs.getString("nombre"),
	                rs.getString("tipoDeporte"),
	                rs.getString("restricciones"),
	                rs.getString("ingredientes"),
	                rs.getString("porcion"),
	                rs.getString("tipoComida")
	            );
	            platillos.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return platillos;
	}



	public static void actualizarPlatillo(String nombreOriginal, Platillo nuevo) {
	    String sql = "UPDATE platillo SET nombre=?, tipoDeporte=?, restricciones=?, ingredientes=?, porcion=?, tipoComida=? WHERE nombre=?";

	    try (Connection con = Conexion.getConexion();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setString(1, nuevo.getNombre());
	        stmt.setString(2, nuevo.getTipoDeporte());
	        stmt.setString(3, nuevo.getRestricciones());
	        stmt.setString(4, nuevo.getIngredientes());
	        stmt.setString(5, nuevo.getPorcion());
	        stmt.setString(6, nuevo.getTipoComida());
	        stmt.setString(7, nombreOriginal);
	        stmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static void eliminarPlatillo(String nombre) {
	    String sql = "DELETE FROM platillo WHERE nombre = ?";

	    try (Connection con = Conexion.getConexion();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setString(1, nombre);
	        stmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}