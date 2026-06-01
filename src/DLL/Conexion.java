package DLL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_nutricion";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    
    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la base de datos");
            e.printStackTrace();
            return null;
        }
    }
}