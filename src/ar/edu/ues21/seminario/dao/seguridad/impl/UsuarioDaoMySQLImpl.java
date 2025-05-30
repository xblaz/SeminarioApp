package ar.edu.ues21.seminario.dao.seguridad.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import ar.edu.ues21.seminario.auth.AuthException;
import ar.edu.ues21.seminario.dao.seguridad.RolDao;
import ar.edu.ues21.seminario.dao.seguridad.UsuarioDao;
import ar.edu.ues21.seminario.model.LogicaException;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.utils.DatabaseConexion;
import ar.edu.ues21.seminario.utils.ResourceQueryLoader;

public class UsuarioDaoMySQLImpl extends ResourceQueryLoader implements UsuarioDao {

    private final RolDao rolDao;
    private final Connection conexionDb;

    public UsuarioDaoMySQLImpl(){
        this.conexionDb = DatabaseConexion.getConnection();
        this.rolDao = new RolDaoImpl(conexionDb);
    }

    @Override
    public void crear(Usuario pUsuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crear'");
    }

    @Override
    public void borrar(Usuario pUsuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'borrar'");
    }

    @Override
    public void actualizar(Usuario pUsuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public List<Rol> listarRolesPorUsuarioId(Long pId) throws LogicaException {
        return rolDao.obtenerRolesPorUsuarioId(pId);
    }

    @Override
    public Optional<Usuario> buscarPorNombre(String pNombre) throws LogicaException {
        String sql = "SELECT id, nombre FROM usuarios WHERE username = ?";
        try (PreparedStatement stmt = conexionDb.prepareStatement(sql)) {
            stmt.setString(1, pNombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNombre(rs.getString("username"));
                return Optional.of(usuario);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new LogicaException(String.format("Usuario %s no encontrado", pNombre));
        }
    }

    @Override
    public boolean validarCredenciales(String pUsuario, String pClave) throws LogicaException {
        try (Connection conn = DatabaseConexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM usuarios WHERE nombre = ? AND clave = ?")) {
            stmt.setString(1, pUsuario);
            stmt.setString(2, hashSHA256(pClave));
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new LogicaException("Error en autenticación MySQL: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> listar() throws LogicaException {
        return null;
    }

    /**
     * 
     * @param password
     * @return
     */
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

    private boolean verificarPassword(String passwordIngresada, String hashAlmacenado) {
        String hashCalculado = hashSHA256(passwordIngresada);
        return hashAlmacenado.equals(hashCalculado);
    }
  
}
