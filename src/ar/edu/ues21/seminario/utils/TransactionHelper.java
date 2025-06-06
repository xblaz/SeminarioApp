package ar.edu.ues21.seminario.utils;

import ar.edu.ues21.seminario.exception.RepositoryException;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHelper {
    public static void executeInTransaction(TransactionBlock block) throws RepositoryException {
        Connection conn = null;
        try {
            conn = DatabaseConexion.getConnection();
            conn.setAutoCommit(false);
            block.execute(conn);
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* Log */ }
            }
            throw new RepositoryException("Transacción fallida", e);
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { /* Log */ }
            }
        }
    }

    @FunctionalInterface
    public interface TransactionBlock {
        void execute(Connection conn) throws SQLException, RepositoryException;
    }
}
