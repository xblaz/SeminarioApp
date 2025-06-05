package ar.edu.ues21.seminario.repository.seguridad.impl;

import ar.edu.ues21.seminario.repository.seguridad.PermisoDao;
import ar.edu.ues21.seminario.repository.seguridad.RolDao;
import ar.edu.ues21.seminario.model.seguridad.Rol;

import java.sql.Connection;
import java.util.List;

public class RolDaoImpl implements RolDao {

    private final PermisoDao permisoDao;
    private final Connection conexionDb;

    public RolDaoImpl(Connection conn) {
        this.conexionDb = conn;
        this.permisoDao = new PermisoDaoImpl(conn);
    }

    @Override
    public Rol obtenerRolPorId(Long pId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerRolPorId'");
    }

    @Override
    public List<Rol> obtenerRolesPorUsuarioId(Long pId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerRolesPorId'");
    }

    @Override
    public void crear(Rol uRol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crear'");
    }

    @Override
    public void actualizar(Rol uRol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void eliminar(Rol uRol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    @Override
    public List<Rol> listar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }

}
