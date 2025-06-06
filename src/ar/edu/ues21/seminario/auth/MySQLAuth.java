package ar.edu.ues21.seminario.auth;

import ar.edu.ues21.seminario.exception.AuthException;
import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.repository.seguridad.RolRepository;
import ar.edu.ues21.seminario.repository.seguridad.UsuarioDao;
import ar.edu.ues21.seminario.exception.LogicaException;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.repository.seguridad.UsuarioRepository;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;

public class MySQLAuth implements AuthMethod {
    //private final UsuarioDao usuarioDao;
    private final UsuarioRepository repository;
    private final RolRepository rolRepository;

    public MySQLAuth(UsuarioRepository repository) {
        this.repository = repository;
        this.rolRepository = new RolRepository();
    }

    @Override
    public Usuario autenticar(String pUsuario, String pClave) throws AuthException {
        try {
            if (!repository.login(pUsuario, pClave)) {
                throw new AuthException("Las credenciales ingresadas son inválidas");
            }
            Usuario usuario = repository.findByNombre(pUsuario).orElseThrow(() -> new AuthException("Usuario no encontrado en el sistema"));
            List<Rol> roles = rolRepository.findByUsuario(usuario.getNombre());
            usuario.getListaRoles().addAll(roles);
            return usuario;

        } catch (AuthException e) {
            throw new AuthException(String.format("%s", e.getMessage()));
        } catch (RepositoryException e) {
            throw new AuthException(String.format("%s", e.getMessage()));
        }
    }
}
