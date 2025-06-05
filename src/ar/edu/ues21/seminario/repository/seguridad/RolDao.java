package ar.edu.ues21.seminario.repository.seguridad;

import ar.edu.ues21.seminario.model.seguridad.Rol;

import java.util.List;

public interface RolDao {
    Rol obtenerRolPorId(Long pId);

    List<Rol> obtenerRolesPorUsuarioId(Long pId);

    void crear(Rol uRol);

    void actualizar(Rol uRol);

    void eliminar(Rol uRol);

    List<Rol> listar();
}
