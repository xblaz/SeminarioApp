package ar.edu.ues21.seminario.repository.seguridad;

import ar.edu.ues21.seminario.exception.LogicaException;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDao {
    void crear(Usuario pUsuario) throws LogicaException;

    void borrar(Usuario pUsuario) throws LogicaException;

    void actualizar(Usuario pUsuario) throws LogicaException;

    List<Rol> listarRolesPorUsuarioId(Long pId) throws LogicaException;

    Optional<Usuario> buscarPorNombre(String pNombre) throws LogicaException;

    boolean validarCredenciales(String pNombre, String pClave) throws LogicaException;

    List<Usuario> listar() throws LogicaException;
}
