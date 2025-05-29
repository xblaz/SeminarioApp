package ar.edu.ues21.seminario.auth;

import ar.edu.ues21.seminario.model.LogicaException;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.utils.DatabaseConexion;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLAuth implements AuthMethod {
    @Override
    public Usuario autenticar(String pUsuario, String pClave) throws AuthException {
        try (Connection conn = DatabaseConexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, nombre, clave FROM usuarios WHERE nombre = ? AND clave = ?")) {
            stmt.setString(1, pUsuario);
            stmt.setString(2, hashSHA256(pClave));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getLong("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            throw new AuthException("Error en autenticación MySQL: " + e.getMessage());
        }
        throw new AuthException("Usuario y contraseña inválidos");
    }

    private String hashSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new LogicaException(String.format("Error al calcular hash SHA-256: %s", e.getMessage()));
        }
    }
}
