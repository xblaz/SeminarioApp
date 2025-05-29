package ar.edu.ues21.seminario.repository.seguridad;

import java.util.List;

import ar.edu.ues21.seminario.model.seguridad.Permiso;

public interface PermisoDao {
    public Permiso obtenerPermisoPorId(Long pId);
    public List<Permiso> obtenerPermisosPorId(Long pId);
    public void crear(Permiso pPermiso);
    public void actualizar(Permiso pPermiso);
    public void eliminar(Permiso pPermiso);
    public List<Permiso> listar();
}
