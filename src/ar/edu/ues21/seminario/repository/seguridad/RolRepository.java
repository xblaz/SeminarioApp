package ar.edu.ues21.seminario.repository.seguridad;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.model.seguridad.Permiso;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.repository.GenericRepository;
import ar.edu.ues21.seminario.utils.DatabaseConexion;
import ar.edu.ues21.seminario.utils.TemplateQueryLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RolRepository implements GenericRepository<Rol,Long> {
    public RolRepository() {
    }
    @Override
    public List<Rol> findAll() throws RepositoryException {
        return null;
    }
    @Override
    public Optional<Rol> findById(Long aLong) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public void save(Rol entity) throws RepositoryException {

    }

    @Override
    public void delete(Long aLong) throws RepositoryException {

    }

    @Override
    public List<Rol> findByCriteria(Map<String, Object> criteria) throws RepositoryException {
        return null;
    }

    public List<Rol> findByUsuario(String pNombre) throws RepositoryException {
        String sql = TemplateQueryLoader.get("rol", "roles_por_usuario");
        try (
            Connection conn = DatabaseConexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pNombre);
            try (ResultSet rs = stmt.executeQuery()) {
                return mapResultSetToRoles(rs);
            }
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Roles no encontradas para Usuario %s ", pNombre));
        }
    }

    private List<Rol> mapResultSetToRoles(ResultSet rs) throws SQLException {
        Map<Long, Rol> rolesMap = new HashMap<>();
        while (rs.next()) {
            Long rolId = rs.getLong("rol_id");
            Rol r = rolesMap.computeIfAbsent(rolId, id -> {
                Rol rol = new Rol();
                try {
                    rol.setNombre(rs.getString("rol_nombre"));
                    rol.setIdRol(rs.getLong("rol_id"));
                } catch (SQLException e) {
                    throw new RuntimeException("Error al mapear rol", e);
                }
                rol.setListaPermisos(new ArrayList<>());
                return rol;
            });
            Permiso permiso = new Permiso();
            permiso.setIdPermiso(rs.getLong("permiso_id"));
            permiso.setCodigo(rs.getString("permiso_codigo"));
            r.getListaPermisos().add(permiso);
        }
        return new ArrayList<>(rolesMap.values());
    }
}
