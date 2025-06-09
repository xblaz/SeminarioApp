package ar.edu.ues21.seminario.repository.seguridad;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.model.seguridad.Permiso;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.repository.GenericRepository;
import ar.edu.ues21.seminario.utils.TemplateQueryLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RolRepository implements GenericRepository<Rol,Long> {

    private final Connection conexion;
    public RolRepository(Connection conn) {
        this.conexion = conn;
    }
    @Override
    public List<Rol> findAll() throws RepositoryException {
        String sql = TemplateQueryLoader.get("rol", "find_all");
        List<Rol> roles = new ArrayList<Rol>();
        try (

                PreparedStatement stmt = this.conexion.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Rol unRol = new Rol();
                    unRol.setDescripcion(rs.getString("descripcion"));
                    unRol.setIdRol(rs.getLong("id"));
                    roles.add(unRol);
                }
                return roles;
            }
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Buscando roles %s", e.getMessage()));
        }
    }
    @Override
    public Optional<Rol> findById(Long aLong) throws RepositoryException {
        String sql = TemplateQueryLoader.get("rol", "find_by_id");
        try (

                PreparedStatement stmt = this.conexion.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                Rol unRol = new Rol();
                unRol.setDescripcion(rs.getString("descripcion"));
                unRol.setIdRol(rs.getLong("id"));
                return Optional.of(unRol);
            }
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Buscando roles"));
        }
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

                PreparedStatement stmt = this.conexion.prepareStatement(sql)) {
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
                    rol.setDescripcion(rs.getString("rol_descripcion"));
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
