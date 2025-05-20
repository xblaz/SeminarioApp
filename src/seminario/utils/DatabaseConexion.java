package seminario.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import seminario.config.Configuracion;
import seminario.model.LogicaException;

public class DatabaseConexion {
    
    private static Connection con = null;

    public synchronized static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(Configuracion.URL, Configuracion.USERNAME, Configuracion.PASSWORD);
            }
        } catch (SQLException e) {
            throw new LogicaException(String.format("Error conectado a la base de datos: %s", e.getMessage()));
        }
        return con;
    }

    public static void cerrarConexion() {
    try {
        if (con != null && !con.isClosed()) {
            con.close();
        }
    } catch (SQLException e) {
        throw new LogicaException(String.format("Error cerrando conexión: %s", e.getMessage()));
    }
}
}
