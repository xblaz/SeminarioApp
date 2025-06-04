package ar.edu.ues21.seminario.auth;

import ar.edu.ues21.seminario.dao.seguridad.impl.UsuarioDaoMemoriaImpl;
import ar.edu.ues21.seminario.model.seguridad.Usuario;

import java.util.List;
import java.util.Optional;

public class MemoriaAuth implements AuthMethod {
    private final UsuarioDaoMemoriaImpl usuarioDao;

    public MemoriaAuth(UsuarioDaoMemoriaImpl impl) {
        this.usuarioDao = impl;
    }

    @Override
    public Usuario autenticar(String pUsuario, String pClave) throws AuthException {
        if (pUsuario == null || pUsuario.trim().isEmpty()) {
            throw new AuthException("El nombre de usuario no puede estar vacío");
        }

        if (pClave == null || pClave.isEmpty()) {
            throw new AuthException("La clave no puede estar vacía");
        }

        Optional<Usuario> usuario = usuarioDao.buscarPorNombre(pUsuario);

        if (usuario.isEmpty() || !usuario.get().getClave().equals(hashPassword(pClave))) {
            throw new AuthException("Las credenciales ingresadas son inválidas");
        }
        return clonarUsuario(usuario.get());
    }

    /**
     * Método para obtener todos los usuarios (solo para desarrollo/diagnóstico)
     *
     * @return Lista inmutable de usuarios
     */
    public List<Usuario> getUsuariosDePrueba() {
        return List.copyOf(usuarioDao.listar());
    }

    /**
     * Simula hashing de contraseña
     */
    private static String hashPassword(String plainPassword) {
        return String.valueOf(plainPassword.hashCode());
    }

    /**
     * Crea una copia defensiva del usuario para evitar modificaciones
     */
    private Usuario clonarUsuario(Usuario original) {
        Usuario copia = new Usuario(
                original.getId(),
                original.getNombre(),
                original.getClave()
        );
        original.getListaRoles().forEach(copia.getListaRoles()::add);
        return copia;
    }
}
