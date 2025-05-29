package ar.edu.ues21.seminario.repository.seguridad;

import java.util.List;

import ar.edu.ues21.seminario.model.seguridad.Rol;

public interface  RolDao {
    public Rol obtenerRolPorId(Long pId);
    public List<Rol> obtenerRolesPorUsuarioId(Long pId);
    public void crear(Rol uRol);
    public void actualizar(Rol uRol);
    public void eliminar(Rol uRol);
    public List<Rol> listar();
}
