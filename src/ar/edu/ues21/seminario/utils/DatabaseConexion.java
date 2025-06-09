package ar.edu.ues21.seminario.utils;

import ar.edu.ues21.seminario.config.Configuracion;
import ar.edu.ues21.seminario.exception.LogicaException;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConexion implements AutoCloseable {

    private final Connection conexion;

    public DatabaseConexion() {
        try {
            this.conexion = DriverManager.getConnection(
                    Configuracion.URL,
                    Configuracion.USERNAME,
                    Configuracion.PASSWORD
            );
        } catch (Exception e) {
            throw new LogicaException("Error al obtener conexión: " + e.getMessage());
        }
    }
    public Connection getConexion() {
        return conexion;
    }

    public void beginTransaction() {
        try {
            conexion.setAutoCommit(false);
        } catch (Exception e) {
            throw new LogicaException("Error al iniciar transacción: " + e.getMessage());
        }
    }

    public void commit() {
        try {
            conexion.commit();
            conexion.setAutoCommit(true);
        } catch (Exception e) {
            throw new LogicaException("Error al confirmar transacción: " + e.getMessage());
        }
    }

    public void rollback() {
        try {
            conexion.rollback();
            conexion.setAutoCommit(true);
        } catch (Exception e) {
            throw new LogicaException("Error al hacer rollback: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            if (!conexion.isClosed()) {
                conexion.close();
            }
        } catch (Exception e) {
            throw new LogicaException("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
