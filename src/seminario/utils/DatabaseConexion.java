package seminario.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import seminario.config.Configuracion;
import seminario.model.AplicacionException;

public class DatabaseConexion {
    private static Connection con = null;

    static {
        try {
            Class.forName(Configuracion.DRIVER);
            con = DriverManager.getConnection(Configuracion.URL, Configuracion.USERNAME, Configuracion.PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new AplicacionException(String.format("Error conectado a la base de datos %s", e.getMessage()));
        }
    }

    public static Connection getConnection() {
        return con;
    }
}
