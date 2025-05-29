package ar.edu.ues21.seminario.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ar.edu.ues21.seminario.config.Configuracion;
import ar.edu.ues21.seminario.model.LogicaException;

public class DatabaseConexion {
    
    private static Connection connexion = null;

    public synchronized static Connection getConnection() {
        try {
            if (connexion == null || connexion.isClosed()) {
                connexion = DriverManager.getConnection(Configuracion.URL, Configuracion.USERNAME, Configuracion.PASSWORD);
            }
        } catch (SQLException e) {
            throw new LogicaException(String.format("Error conectado a la base de datos: %s", e.getMessage()));
        }
        return connexion;
    }

    public static void cerrarConexion() {
    try {
        if (connexion != null && !connexion.isClosed()) {
            connexion.close();
        }
    } catch (SQLException e) {
        throw new LogicaException(String.format("Error cerrando conexión: %s", e.getMessage()));
    }
}
}
