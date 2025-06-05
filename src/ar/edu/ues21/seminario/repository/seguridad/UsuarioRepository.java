package ar.edu.ues21.seminario.repository.seguridad;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.model.seguridad.EstadoUsuario;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.repository.GenericRepository;
import ar.edu.ues21.seminario.utils.DatabaseConexion;
import ar.edu.ues21.seminario.utils.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsuarioRepository implements GenericRepository<Usuario, Long> {
    public UsuarioRepository() {
    }
    @Override
    public List<Usuario> findAll() throws RepositoryException {
        return null;
    }

    @Override
    public Optional<Usuario> findById(Long aLong) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public Usuario save(Usuario entity) throws RepositoryException {
        return null;
    }

    @Override
    public void delete(Long aLong) throws RepositoryException {

    }

    @Override
    public List<Usuario> findByCriteria(Map<String, Object> criteria) throws RepositoryException {
        List<Usuario> usuarios = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT id, clave, nombre, estado FROM usuarios WHERE 1=1");
        List<Object> parametros = new ArrayList<>();

        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            String campo = entry.getKey();
            Object valor = entry.getValue();
            sql.append(" AND ").append(campo).append(" = ?");
            parametros.add(valor);
        }

        return null;
    }


    public boolean login(String pUsuario, String pClave) throws RepositoryException {
        try (Connection conn = DatabaseConexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM usuarios WHERE nombre = ? AND clave = ?")) {
            stmt.setString(1, pUsuario);
            stmt.setString(2, Util.hashSHA256(pClave));
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Error de login: %s", e.getMessage()));
        }
    }

    public Optional<Usuario> findByNombre(String pNombre) throws RepositoryException {
        String sql = "SELECT * FROM usuarios WHERE nombre = ?";
        try (
                Connection conn = DatabaseConexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, pNombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = mapResultSetToUsuario(rs);
                return Optional.of(usuario);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Usuario %s no encontrado", pNombre));
        }
    }

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario p = new Usuario();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        Date fechaAlta = rs.getDate("fecha_alta") != null ? rs.getDate("fecha_alta") : null;
        Date fechaBaja = rs.getDate("fecha_baja") != null ? rs.getDate("fecha_baja") : null;
        p.setFechaAlta(fechaAlta != null ? fechaAlta.toLocalDate() : null);
        p.setEstado(EstadoUsuario.valueOfCodigo(rs.getString("estado")));
        return p;
    }

}
