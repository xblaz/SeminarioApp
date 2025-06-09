package ar.edu.ues21.seminario.service;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.exception.ValidacionException;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.repository.seguridad.RolRepository;
import ar.edu.ues21.seminario.repository.seguridad.UsuarioRepository;
import ar.edu.ues21.seminario.utils.Util;

import java.util.List;
import java.util.Optional;

public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;

    public UsuarioService(UsuarioRepository repoUsuario, RolRepository rolRepo) {
        this.usuarioRepo = repoUsuario;
        this.rolRepo = rolRepo;
    }

    public List<Usuario> buscarTodos() throws RepositoryException {
        return usuarioRepo.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long pLong) throws RepositoryException {
            Optional<Usuario> usuario = this.usuarioRepo.findById(pLong);
            if (usuario.isPresent()) {
                List<Rol> roles = rolRepo.findByUsuario(usuario.get().getNombre());
                usuario.get().getListaRoles().addAll(roles);
            }
            return usuario;
    }

    public Optional<Usuario> buscarUsuarioPorNombre(String pNombre) throws RepositoryException {
            Optional<Usuario> usuario = this.usuarioRepo.findByNombre(pNombre);
            if (usuario.isPresent()) {
                List<Rol> roles = rolRepo.findByUsuario(usuario.get().getNombre());
                usuario.get().getListaRoles().addAll(roles);
            }
            return usuario;

    }

    public boolean login(String pNombre, String pClave) throws RepositoryException {
        return this.usuarioRepo.login(pNombre, pClave);
    }

    public void crearUsuario(Usuario usuario, String pClave, String pClaveConfirmar) throws RepositoryException, ValidacionException {
        validarNuevoUsuario(usuario.getNombre(), pClave, pClaveConfirmar,usuario.getListaRoles());
        usuario.setClave(Util.hashSHA256(pClave));
        this.usuarioRepo.save( usuario);
    }

    private void validarNuevoUsuario(String pNombre, String pClave, String pClaveConfirmar, List<Rol> pRoles) throws ValidacionException {
        if (pNombre == null || pNombre.trim().isEmpty()) {
            throw new ValidacionException("Nombre de usuario no puede estar vacío.");
        }
        if (pRoles == null || pRoles.isEmpty()) {
            throw new ValidacionException("Al menos debe seleccionar un rol.");
        }
        if (pClave == null || pClave.trim().isEmpty()) {
            throw new ValidacionException("El campo clave no puede estar vacío.");
        }
        if (pClaveConfirmar == null || pClaveConfirmar.trim().isEmpty()) {
            throw new ValidacionException("El campo clave confirmación no puede estar vacío.");
        }
        if (!pClave.equals(pClaveConfirmar)) {
            throw new ValidacionException("Las claves no coiciden.");
        }
    }

    public List<Rol> buscarRolesTodos() throws RepositoryException {
        return rolRepo.findAll();
    }

    private boolean isCambiaClave(String pClave, String pClaveConfirmar) {
        return (pClave != null && !pClave.trim().isEmpty()) ||
                (pClaveConfirmar != null && !pClaveConfirmar.trim().isEmpty());
    }

    private boolean clavesValidas(String pClave, String pClaveConfirmar) {
        return pClave != null && !pClave.trim().isEmpty()
                && pClave.equals(pClaveConfirmar);
    }

    public void actualizarUsuario(Usuario usuario, String pClave, String pClaveConfirmar) throws ValidacionException, RepositoryException {
        validarActualizarUsuario(usuario, pClave, pClaveConfirmar,usuario.getListaRoles());
        if (isCambiaClave(pClave, pClaveConfirmar)){
            this.usuarioRepo.changePassowrd(usuario.getId(), pClave);
        } else {
            this.usuarioRepo.clearUserRoles(usuario.getId());
            this.usuarioRepo.save( usuario);
        }
    }

    private void validarActualizarUsuario(Usuario pUsuario, String pClave, String pClaveConfirmar,  List<Rol> pRoles) throws ValidacionException {
        if (pRoles == null || pRoles.isEmpty()) {
            throw new ValidacionException("Al menos debe seleccionar un rol.");
        }
        if (isCambiaClave(pClave, pClaveConfirmar)) {
            if (!clavesValidas(pClave, pClaveConfirmar)) {
                throw new ValidacionException("Las claves ingresadas no coiciden!");
            }
        }
    }

    public void eliminarUsuario(Long pUsuarioId) throws RepositoryException {
        this.usuarioRepo.delete(pUsuarioId);
    }

}
