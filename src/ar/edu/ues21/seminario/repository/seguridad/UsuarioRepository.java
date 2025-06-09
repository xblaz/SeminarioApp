package ar.edu.ues21.seminario.repository.seguridad;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.model.seguridad.EstadoUsuario;
import ar.edu.ues21.seminario.model.seguridad.Permiso;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.repository.GenericRepository;
import ar.edu.ues21.seminario.utils.TemplateQueryLoader;
import ar.edu.ues21.seminario.utils.Util;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class UsuarioRepository implements GenericRepository<Usuario, Long> {

    private final Connection conexion;

    public UsuarioRepository(Connection conn) {
        this.conexion = conn;
    }

    @Override
    public List<Usuario> findAll() throws RepositoryException {
        String sql = TemplateQueryLoader.get("usuario", "find_all");
        try (PreparedStatement stmt = this.conexion.prepareStatement(sql);
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
                            r.setDescripcion(rs.getString("rol_descripcion"));
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
            throw new RepositoryException("Error en mapeo el objeto usuario", e.getCause());
        }
    }

    @Override
    public Optional<Usuario> findById(Long aLong) throws RepositoryException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (
            PreparedStatement stmt = this.conexion.prepareStatement(sql)) {
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
            if (pUsuario.getId() == null) {
             Long idUsuario = createUsuario(pUsuario);
                addRolesUsuario(idUsuario, pUsuario.getListaRoles());
            } else {
              Usuario actualizado = updateUsuario(pUsuario);
                 addRolesUsuario(actualizado.getId(), pUsuario.getListaRoles());

            }
        } catch (SQLException e) {
            String error = e.getMessage();
            if (e.getMessage().contains("Duplicate entry")) {
                error = String.format("Ya existe un usuario con el nombre '%s'", pUsuario.getNombre());
            }
            throw new RepositoryException(error);
        }
    }

    private Long createUsuario(Usuario pUsuario) throws SQLException {
        String sql = TemplateQueryLoader.get("usuario", "create_usuario");
        long usuarioId;
        try (
             PreparedStatement stmt = this.conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setString(2, pUsuario.getClave());
            stmt.setString(3, pUsuario.getEstado().getDescripcion());
            stmt.setString(4, pUsuario.getNombre());
            int result = stmt.executeUpdate();
            if (result == 0) {
                throw new SQLException("No se pudo crear el usuario");
            }
            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuarioId = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("No se obtuvo ID del usuario creado");
                }
            }
           return usuarioId;
        } catch (SQLException e) {
            throw new SQLException(String.format("Error persistiendo datos de usuario: %s", e.getMessage()));
        }
    }

    private Usuario updateUsuario(Usuario pUsuario) throws RepositoryException {
        String sql = TemplateQueryLoader.get("usuario", "update_usuario");
        try (PreparedStatement stmt = this.conexion.prepareStatement(sql)) {
            stmt.setString(1, pUsuario.getEstado().getDescripcion());
            stmt.setLong(2, pUsuario.getId());
            int result = stmt.executeUpdate();
            if (result == 0) {
                throw new RepositoryException("No se pudo actualizar el usuario");
            }
            return pUsuario;
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Error actualizando datos de usuario: %s", e.getMessage()));
        }
    }

    public void clearUserRoles(Long usuarioId) throws RepositoryException {
        String sql = TemplateQueryLoader.get("usuario", "clear_user_roles");
        try (
            PreparedStatement stmt = this.conexion.prepareStatement(sql)) {
            stmt.setLong(1, usuarioId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Error borrando roles de usuario: %s", e.getMessage()));
        }
    }

    public void changePassowrd(Long usuarioId, String pClave) throws RepositoryException {
        String sql = TemplateQueryLoader.get("usuario", "change_password");
        try (PreparedStatement stmt = this.conexion.prepareStatement(sql)) {
            stmt.setString(1, Util.hashSHA256(pClave));
            stmt.setLong(2, usuarioId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Error cambiando contraseña usuario: %s", e.getMessage()));
        }
    }

    @Override
    public void delete(Long usuarioId) throws RepositoryException {
        String sql = TemplateQueryLoader.get("usuario", "delete");
        try (PreparedStatement stmt = this.conexion.prepareStatement(sql)) {
            stmt.setLong(1, usuarioId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Error eliminado usuario: %s", e.getMessage()));
        }
    }

    @Override
    public List<Usuario> findByCriteria(Map<String, Object> criteria) throws RepositoryException {
        return null;
    }

    public boolean login(String pUsuario, String pClave) throws RepositoryException {
        String sql = TemplateQueryLoader.get("usuario", "login");
        try (
             PreparedStatement stmt = this.conexion.prepareStatement(sql)) {
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
                PreparedStatement stmt = this.conexion.prepareStatement(sql)) {
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

    public void addRolesUsuario(Long pUsuarioId, List<Rol> pRoles) throws RepositoryException {
        String sql = TemplateQueryLoader.get("usuario", "add_rol_usuario");
        try (
             PreparedStatement stmt = this.conexion.prepareStatement(sql)) {
            for (Rol rol : pRoles) {
                stmt.setLong(1, pUsuarioId);
                stmt.setLong(2, rol.getIdRol());
                stmt.addBatch(); // Agregamos a batch para ejecución masiva
            }
            int[] results = stmt.executeBatch();
            // Verificar que todos los inserts fueron exitosos
            for (int result : results) {
                if (result == Statement.EXECUTE_FAILED) {
                    throw new RepositoryException("Error al asignar roles al usuario");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Error agregando roles a usuario: %s", e.getMessage()));
        }
    }

}
