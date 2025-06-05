package ar.edu.ues21.seminario.repository.seguridad.impl;

import ar.edu.ues21.seminario.repository.seguridad.PermisoDao;
import ar.edu.ues21.seminario.model.seguridad.Permiso;

import java.sql.Connection;
import java.util.List;

public class PermisoDaoImpl implements PermisoDao {

    private final Connection conn;

    public PermisoDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Permiso obtenerPermisoPorId(Long pId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerPermisoPorId'");
    }

    @Override
    public List<Permiso> obtenerPermisosPorId(Long pId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerPermisosPorId'");
    }

    @Override
    public void crear(Permiso pPermiso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crear'");
    }

    @Override
    public void actualizar(Permiso pPermiso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void eliminar(Permiso pPermiso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    @Override
    public List<Permiso> listar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }

}
