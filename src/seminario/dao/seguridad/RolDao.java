package seminario.dao.seguridad;

import java.util.List;
import seminario.model.seguridad.Rol;

public interface  RolDao {
    public Rol obtenerRolPorId(Long pId);
    public List<Rol> obtenerRolesPorUsuarioId(Long pId);
    public void crear(Rol uRol);
    public void actualizar(Rol uRol);
    public void eliminar(Rol uRol);
    public List<Rol> listar();
}
