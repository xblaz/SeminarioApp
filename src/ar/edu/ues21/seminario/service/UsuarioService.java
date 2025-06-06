package ar.edu.ues21.seminario.service;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.exception.ValidacionException;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.repository.seguridad.RolRepository;
import ar.edu.ues21.seminario.repository.seguridad.UsuarioRepository;
import ar.edu.ues21.seminario.utils.TransactionHelper;
import ar.edu.ues21.seminario.utils.Util;

import java.util.List;
import java.util.Optional;

public class UsuarioService {
    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;

    public UsuarioService() {
        this.usuarioRepo = new UsuarioRepository();
        this.rolRepo = new RolRepository();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long pLong) throws RepositoryException {
        Optional<Usuario> usuario = this.usuarioRepo.findById(pLong);
        if (usuario.isPresent()) {
            List<Rol> roles = rolRepo.findByUsuario(usuario.get().getNombre());
            if (!roles.isEmpty()){
                usuario.get().getListaRoles().addAll(roles);
            }
        }
        return usuario;
    }

    public Optional<Usuario> buscarUsuarioPorNombre(String pNombre) throws RepositoryException {
        Optional<Usuario> usuario = this.usuarioRepo.findByNombre(pNombre);
        if (usuario.isPresent()) {
            List<Rol> roles = rolRepo.findByUsuario(usuario.get().getNombre());
            if (!roles.isEmpty()){
                usuario.get().getListaRoles().addAll(roles);
            }
        }
        return usuario;
    }

    public boolean login(String pNombre, String pClave) throws RepositoryException {
        return this.usuarioRepo.login(pNombre,pClave);
    }

    public void crearUsuario(String pNombre, String pClave, String roles) throws RepositoryException, ValidacionException {
        validarNuevoUsuario(pNombre, pClave);
        Usuario usuario = new Usuario();
        usuario.setNombre(pNombre);
        usuario.setClave(Util.hashSHA256(pClave));
        this.usuarioRepo.save(usuario);
    }

    private void validarNuevoUsuario(String pNombre, String pClave) throws ValidacionException {
        if (pNombre == null || pNombre.trim().isEmpty()) {
            throw new ValidacionException("Nombre de usuario no puede estar vacio.");
        }
        if (pClave == null || pClave.trim().isEmpty()) {
            throw new ValidacionException("El campo clave no puede estar vacio.");
        }
    }
}
