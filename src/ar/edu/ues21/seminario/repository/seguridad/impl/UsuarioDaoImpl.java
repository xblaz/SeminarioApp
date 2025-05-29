package ar.edu.ues21.seminario.repository.seguridad.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.ues21.seminario.repository.seguridad.UsuarioDao;
import ar.edu.ues21.seminario.model.LogicaException;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.repository.seguridad.RolDao;
import ar.edu.ues21.seminario.utils.DatabaseConexion;
import ar.edu.ues21.seminario.utils.TemplateLoader;

public class UsuarioDaoImpl extends TemplateLoader implements UsuarioDao {

    private final RolDao rolDao;
    
    public UsuarioDaoImpl(RolDao rolDao){
        this.rolDao = rolDao;
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
