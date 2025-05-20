package seminario.dao.seguridad.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import seminario.dao.seguridad.PermisoDao;
import seminario.dao.seguridad.RolDao;
import seminario.dao.seguridad.UsuarioDao;
import seminario.model.LogicaException;
import seminario.model.seguridad.Rol;
import seminario.model.seguridad.Usuario;
import seminario.utils.DatabaseConexion;
import seminario.utils.TemplateLoader;

public class UsuarioDaoImpl extends TemplateLoader implements UsuarioDao {

    private final RolDao rolDao;
    private final PermisoDao permisoDao;

    public UsuarioDaoImpl(RolDao rolDao, PermisoDao permisoDao){
        this.rolDao = rolDao;
        this.permisoDao = permisoDao;
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
    public Usuario login(String pUsuario, String pClave) {
        String sql = "SELECT id, nombre, clave FROM usuarios WHERE nombre = ?";
         try (PreparedStatement stmt = DatabaseConexion.getConnection().prepareStatement(sql)) {
        stmt.setString(1, pUsuario);
        ResultSet rs = stmt.executeQuery();

        Usuario usuario = null;
        List<Rol> roles = new ArrayList<>();

        while (rs.next()) {
            if (usuario == null) {
                String hash = rs.getString("clave");
                if (!verificarPassword(pClave, hash)) return null;

                usuario = new Usuario(
                    rs.getLong("usuario_id"),
                    rs.getString("nombre")                    
                );
            }

            Rol rol = new Rol(rs.getLong("rol_id"), rs.getString("rol_nombre"));
            roles.add(rol);
        }

        if (usuario != null) {
            usuario.setListaRoles(roles);
        }

        return usuario;
    } catch (SQLException e) {
        throw new LogicaException("Error durante login: " + e.getMessage());
    }
    }

    @Override
    public boolean tienePermiso(String pPermiso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tienePermiso'");
    }

    @Override
    public List<Rol> listarRoles(Usuario pUsuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarRoles'");
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
            throw new LogicaException(String.format("Error al calcular hash SHA-256: %s",e.getMessage()));
        }
    }

    private boolean verificarPassword(String passwordIngresada, String hashAlmacenado) {
        String hashCalculado = hashSHA256(passwordIngresada);
        return hashAlmacenado.equals(hashCalculado);
    }
  
}
