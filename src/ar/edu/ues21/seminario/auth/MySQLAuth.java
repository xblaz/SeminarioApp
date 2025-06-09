package ar.edu.ues21.seminario.auth;

import ar.edu.ues21.seminario.exception.AuthException;
import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.model.seguridad.EstadoUsuario;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.service.UsuarioService;

public class MySQLAuth implements AuthMethod {
    private final UsuarioService service;
    public MySQLAuth(UsuarioService service) {
        this.service = service;
    }
    @Override
    public Usuario autenticar(String pUsuario, String pClave) throws AuthException {
        try {
            if (!service.login(pUsuario, pClave)) {
                throw new AuthException("Las credenciales ingresadas son inválidas");
            }
            Usuario usuario = service.buscarUsuarioPorNombre(pUsuario).orElseThrow(() -> new AuthException("Usuario no encontrado en el sistema"));
            if (!usuario.getEstado().equals(EstadoUsuario.ACTIVO)) {
                throw new AuthException("Tu usuario se encuentra deshabilitado. Contacta al administrador.");
            }
            return usuario;

        } catch (AuthException e) {
            throw new AuthException(String.format("%s", e.getMessage()));
        } catch (RepositoryException e) {
            throw new AuthException(String.format("%s", e.getMessage()));
        }
    }
}
