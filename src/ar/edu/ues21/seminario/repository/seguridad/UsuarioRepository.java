package ar.edu.ues21.seminario.repository.seguridad;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.model.seguridad.EstadoUsuario;
import ar.edu.ues21.seminario.model.seguridad.Permiso;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.repository.GenericRepository;
import ar.edu.ues21.seminario.utils.DatabaseConexion;
import ar.edu.ues21.seminario.utils.TemplateQueryLoader;
import ar.edu.ues21.seminario.utils.Util;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class UsuarioRepository implements GenericRepository<Usuario, Long> {
    private final RolRepository rolRepository;

    public UsuarioRepository() {
        rolRepository = new RolRepository();
    }

    @Override
    public List<Usuario> findAll() throws RepositoryException {
        String sql = TemplateQueryLoader.get("usuario", "find_all");
        try (Connection conn = DatabaseConexion.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            Map<String, Usuario> usuariosMap = new LinkedHashMap<>();
            Map<Long, Rol> rolesMap = new HashMap<>();

            while (rs.next()) {
                // Mapear usuario
                String nombreUsuario = rs.getString("nombre");
                Usuario usuario = usuariosMap.computeIfAbsent(nombreUsuario, k -> {
                    try {
                        Usuario u = new Usuario();
                        u.setId(rs.getLong("id"));
                        u.setNombre(nombreUsuario);
                        Date fechaAlta = rs.getDate("fecha_alta");
                        Date fechaBaja = rs.getDate("fecha_baja");
                        u.setFechaAlta(fechaAlta != null ? fechaAlta.toLocalDate() : null);
                        u.setFechaBaja(fechaBaja != null ? fechaBaja.toLocalDate() : null);
                        u.setEstado(EstadoUsuario.valueOfCodigo(rs.getString("estado")));
                        return u;
                    } catch (SQLException e) {
                        throw new RuntimeException("Error mapeando usuario", e);
                    }
                });
                // Mapear rol (si existe)
                Long rolId = rs.getLong("rol_id");
                if (!rs.wasNull()) {
                    Rol rol = rolesMap.computeIfAbsent(rolId, k -> {
                        try {
                            Rol r = new Rol();
                            r.setIdRol(rolId);
                            r.setNombre(rs.getString("rol_nombre"));
                            return r;
                        } catch (SQLException e) {
                            throw new RuntimeException("Error mapeando roles", e);
                        }
                    });
                    // Mapear permiso (si existe)
                    Long permisoId = rs.getLong("permiso_id");
                    if (!rs.wasNull()) {
                        Permiso permiso = new Permiso();
                        permiso.setIdPermiso(permisoId);
                        permiso.setCodigo(rs.getString("permiso_codigo"));
                        rol.getListaPermisos().add(permiso);
                    }
                    // Añadir rol al usuario (sin duplicados)
                    if (usuario.getListaRoles().stream().noneMatch(r -> r.getIdRol() == rolId)) {
                        usuario.getListaRoles().add(rol);
                    }
                }
            }
            return new ArrayList<>(usuariosMap.values());
        } catch (SQLException e) {
            throw new RepositoryException("Error al obtener usuarios", e);
        } catch (RuntimeException e) {
            throw new RepositoryException("Error en mapeo", e.getCause());
        }
    }

    @Override
    public Optional<Usuario> findById(Long aLong) throws RepositoryException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (
            Connection conn = DatabaseConexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, aLong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
               Usuario usuario = mapResultSetToUsuario(rs);
               return Optional.of(usuario);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Usuario con id %s no encontrado", aLong));
        }
    }

    @Override
    public void save(Usuario pUsuario) throws RepositoryException {
        try {
            int resultado = 0;
            if (pUsuario.getId() == null) {
              resultado = createUsuario(pUsuario);
            } else {
              resultado = updateUsuario(pUsuario);
            }
        } catch (SQLException e) {
            String error = e.getMessage();
            if (e.getMessage().contains("Duplicate entry")) {
                error = String.format("Usuario '%s' existe!", pUsuario.getNombre());
            }
            throw new RepositoryException(error);
        }
    }

    private int createUsuario(Usuario pUsuario) throws SQLException {
        String sql = TemplateQueryLoader.get("usuario", "save_insertar");
        try (Connection conn = DatabaseConexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setString(2, pUsuario.getClave());
            stmt.setString(3, pUsuario.getEstado().getEstado());
            stmt.setString(4, pUsuario.getNombre());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(String.format("Error persistiendo datos de usuario: %s", e.getMessage()));
        }
    }

    private int updateUsuario(Usuario pUsuario) throws SQLException {
        String sql = TemplateQueryLoader.get("usuario", "update_insertar");
        try (Connection conn = DatabaseConexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            Date fechaBaja = pUsuario != null && pUsuario.getEstado().equals(EstadoUsuario.NO_ACTIVO) ? Date.valueOf(LocalDate.now()) : null;
            stmt.setDate(1,  fechaBaja);
            stmt.setString(2, pUsuario.getEstado().getEstado());
            stmt.setLong(3, pUsuario.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(String.format("Error actualizando datos de usuario: %s", e.getMessage()));
        }
    }

    @Override
    public void delete(Long aLong) throws RepositoryException {

    }

    @Override
    public List<Usuario> findByCriteria(Map<String, Object> criteria) throws RepositoryException {
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

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException, RepositoryException {
        // Obtener o crear el usuario
        Usuario u = new Usuario();
        u.setNombre(rs.getString("nombre"));
        Date fechaAlta = rs.getDate("fecha_alta") != null ? rs.getDate("fecha_alta") : null;
        Date fechaBaja = rs.getDate("fecha_baja") != null ? rs.getDate("fecha_baja") : null;
        u.setFechaAlta(fechaAlta != null ? fechaAlta.toLocalDate() : null);
        u.setFechaBaja(fechaBaja != null ? fechaBaja.toLocalDate() : null);
        u.setEstado(EstadoUsuario.valueOfCodigo(rs.getString("estado")));
        return u;
    }

}
