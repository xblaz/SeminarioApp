package ar.edu.ues21.seminario.dao.seguridad.impl;

import ar.edu.ues21.seminario.dao.seguridad.UsuarioDao;
import ar.edu.ues21.seminario.model.LogicaException;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UsuarioDaoMemoriaImpl implements UsuarioDao {
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

        // Usuario superUser
        Usuario superUser = new Usuario(3L, "super", hashPassword("123"));
        superUser.getListaRoles().add(ROL_OPERADOR);
        superUser.getListaRoles().add(ROL_ADMINISTRADOR);
        usuariosTemp.put(superUser.getNombre(), superUser);

        USUARIOS_PREDEFINIDOS = Collections.unmodifiableMap(usuariosTemp);
    }

    public UsuarioDaoMemoriaImpl() {
    }

    @Override
    public void crear(Usuario pUsuario) throws LogicaException {
    }

    @Override
    public void borrar(Usuario pUsuario) throws LogicaException {
    }

    @Override
    public void actualizar(Usuario pUsuario) throws LogicaException {
    }

    @Override
    public List<Rol> listarRolesPorUsuarioId(Long pId) throws LogicaException {
        return Collections.emptyList();
    }

    @Override
    public Optional<Usuario> buscarPorNombre(String pNombre) throws LogicaException {
        return Optional.ofNullable(USUARIOS_PREDEFINIDOS.get(pNombre.toLowerCase()));
    }

    @Override
    public boolean validarCredenciales(String pNombre, String pClave) throws LogicaException {
        return USUARIOS_PREDEFINIDOS.containsKey(pNombre.toLowerCase()) &&
                USUARIOS_PREDEFINIDOS.get(pNombre.toLowerCase()).getClave().equals(pClave);
    }

    @Override
    public List<Usuario> listar() throws LogicaException {
        final List<Usuario> usuarios = new ArrayList<>(USUARIOS_PREDEFINIDOS.values());
        return Collections.unmodifiableList(usuarios);
    }

    private static String hashPassword(String plainPassword) {
        return String.valueOf(plainPassword.hashCode());
    }


}
