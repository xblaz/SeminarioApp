package ar.edu.ues21.seminario.auth;

import ar.edu.ues21.seminario.dao.seguridad.impl.UsuarioDaoMemoriaImpl;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoriaAuth implements AuthMethod {

    private final UsuarioDaoMemoriaImpl usuarioDao;

    private static final Map<String, Usuario> USUARIOS_PREDEFINIDOS;

    private static final Rol ROL_ADMINISTRADOR = new Rol(-1L, "Administrador");
    private static final Rol ROL_OPERADOR = new Rol(-2L, "Operador");

    static {
        Map<String, Usuario> usuariosTemp = new ConcurrentHashMap<>();

        // Usuario operador
        Usuario operador = new Usuario(1L, "operador", hashPassword("123"));
        operador.getListaRoles().add(ROL_OPERADOR);
        usuariosTemp.put(operador.getNombre(), operador);

        // Usuario administrador
        Usuario admin = new Usuario(2L, "administrador", hashPassword("123"));
        admin.getListaRoles().add(ROL_ADMINISTRADOR);
        usuariosTemp.put(admin.getNombre(), admin);

        Usuario superUser = new Usuario(3L, "super", hashPassword("123"));
        superUser.getListaRoles().add(ROL_OPERADOR);
        superUser.getListaRoles().add(ROL_ADMINISTRADOR);
        usuariosTemp.put(superUser.getNombre(), superUser);

        USUARIOS_PREDEFINIDOS = Collections.unmodifiableMap(usuariosTemp);
    }

    public MemoriaAuth(UsuarioDaoMemoriaImpl impl){
        this.usuarioDao = impl;
    }

    @Override
    public Usuario autenticar(String username, String password) throws AuthException {
        if (username == null || username.trim().isEmpty()) {
            throw new AuthException("El nombre de usuario no puede estar vacío");
        }

        if (password == null || password.isEmpty()) {
            throw new AuthException("La contraseña no puede estar vacía");
        }

        Usuario usuario = USUARIOS_PREDEFINIDOS.get(username.toLowerCase());

        if (usuario == null || !usuario.getClave().equals(hashPassword(password))) {
            throw new AuthException("Credenciales inválidas");
        }

        // Devolvemos una copia para evitar modificaciones accidentales
        return clonarUsuario(usuario);
    }

    /**
     * Método para obtener todos los usuarios (solo para desarrollo/diagnóstico)
     * @return Lista inmutable de usuarios
     */
    public List<Usuario> getUsuariosDePrueba() {
        return List.copyOf(USUARIOS_PREDEFINIDOS.values());
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
