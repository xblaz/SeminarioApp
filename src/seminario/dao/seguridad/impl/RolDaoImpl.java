package seminario.dao.seguridad.impl;

import java.util.List;
import seminario.dao.seguridad.PermisoDao;
import seminario.dao.seguridad.RolDao;
import seminario.model.seguridad.Rol;

public class RolDaoImpl implements RolDao {
    
    private final PermisoDao permisoDao;

    public RolDaoImpl(PermisoDao permiso) {
        this.permisoDao = permiso;
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
