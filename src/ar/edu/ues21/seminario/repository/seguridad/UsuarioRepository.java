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

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class UsuarioRepository implements GenericRepository<Usuario, Long> {
    public UsuarioRepository() {
    }

    @Override
    public List<Usuario> findAll() throws RepositoryException {
        String sql = TemplateQueryLoader.get("usuario", "find_all");
        List<Usuario> usuarios = new ArrayList<>();
        Map<Long, Usuario> usuariosMap = new HashMap<>();
        try (
                Connection conn = DatabaseConexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapResultSetToUsuario(rs, usuariosMap);
            }
            usuarios.addAll(usuariosMap.values());
            return usuarios;
        } catch (SQLException e) {
            throw new RepositoryException("Error obteniendo todos los usuarios", e);
        }
    }

    @Override
    public Optional<Usuario> findById(Long aLong) throws RepositoryException {
        String sql = TemplateQueryLoader.get("usuario", "find_by_id");
        Map<Long, Usuario> usuariosMap = new HashMap<>();
        try (
            Connection conn = DatabaseConexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, aLong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
               mapResultSetToUsuario(rs, usuariosMap);
               return usuariosMap.values().stream().findFirst();
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Usuario con id %s no encontrado", aLong));
        }
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
        Map<Long, Usuario> usuariosMap = new HashMap<>();
        try (
                Connection conn = DatabaseConexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, pNombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mapResultSetToUsuario(rs, usuariosMap);
                return usuariosMap.values().stream().findFirst();
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Usuario %s no encontrado", pNombre));
        }
    }
    private void mapResultSetToUsuario(ResultSet rs){

    }
    private void mapResultSetToUsuario(ResultSet rs, Map<Long, Usuario> usuariosMap) throws SQLException {
        Long usuarioId = rs.getLong("id");

        // Obtener o crear el usuario
        Usuario usuario = usuariosMap.computeIfAbsent(usuarioId, id -> {
            Usuario u = new Usuario();
            try {
                u.setId(id);
                u.setNombre(rs.getString("nombre"));

                Date fechaAlta = rs.getDate("fecha_alta") != null ? rs.getDate("fecha_alta") : null;
                Date fechaBaja = rs.getDate("fecha_baja") != null ? rs.getDate("fecha_baja") : null;
                u.setFechaAlta(fechaAlta != null ? fechaAlta.toLocalDate() : null);
                u.setFechaBaja(fechaBaja != null ? fechaBaja.toLocalDate() : null);
                u.setEstado(EstadoUsuario.valueOfCodigo(rs.getString("estado")));
                u.setListaRoles(new ArrayList<>());
            } catch (SQLException e) {
                throw new RuntimeException("Error al mapear usuario", e);
            }
            return u;
        });

        // Procesar roles y permisos solo si existen en el resultset
        if (rs.getObject("rol_id") != null) {
            Long rolId = rs.getLong("rol_id");

            // Buscar si el rol ya existe en el usuario
            Optional<Rol> rolExistente = usuario.getListaRoles().stream()
                    .filter(r -> r.getIdRol().equals(rolId))
                    .findFirst();

            Rol rol = rolExistente.orElseGet(() -> {
                Rol r = new Rol();
                try {
                    r.setIdRol(rolId);
                    r.setNombre(rs.getString("rol_nombre"));
                    r.setListaPermisos(new ArrayList<>());
                    usuario.getListaRoles().add(r);
                } catch (SQLException e) {
                    throw new RuntimeException("Error al mapear rol", e);
                }
                return r;
            });

            // Agregar permiso si existe
            if (rs.getObject("permiso_id") != null) {
                Permiso permiso = new Permiso();
                permiso.setIdPermiso(rs.getLong("permiso_id"));
                permiso.setCodigo(rs.getString("permiso_codigo"));
                rol.getListaPermisos().add(permiso);
            }
        }
    }


}
