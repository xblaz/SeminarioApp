package ar.edu.ues21.seminario.repository.seguridad;

import ar.edu.ues21.seminario.model.seguridad.Permiso;

import java.util.List;

public interface PermisoDao {
    Permiso obtenerPermisoPorId(Long pId);

    List<Permiso> obtenerPermisosPorId(Long pId);

    void crear(Permiso pPermiso);

    void actualizar(Permiso pPermiso);

    void eliminar(Permiso pPermiso);

    List<Permiso> listar();
}
