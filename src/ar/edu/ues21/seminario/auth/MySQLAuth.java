package ar.edu.ues21.seminario.auth;

import ar.edu.ues21.seminario.dao.seguridad.UsuarioDao;
import ar.edu.ues21.seminario.dao.seguridad.impl.UsuarioDaoMySQLImpl;
import ar.edu.ues21.seminario.model.LogicaException;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.utils.DatabaseConexion;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySQLAuth implements AuthMethod {
    private final UsuarioDao usuarioDao;

    public MySQLAuth(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    public Usuario autenticar(String pUsuario, String pClave) throws AuthException {
        try {
            if (!usuarioDao.validarCredenciales(pUsuario, pClave)) {
                throw new AuthException("Las credenciales ingresadas son inválidas");
            }
            Usuario usuario = usuarioDao.buscarPorNombre(pUsuario).orElseThrow(() -> new AuthException("Usuario no encontrado en el sistema"));
            List<Rol> roles = usuarioDao.listarRolesPorUsuarioId(usuario.getId());
            usuario.getListaRoles().addAll(roles);
            return usuario;

        } catch ( LogicaException e) {
            throw new AuthException("Error en autenticación MySQL: " + e.getMessage());
        }
    }
}
