package ar.edu.ues21.seminario.repository.seguridad;

import java.util.List;

import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;

public interface UsuarioDao {
    void crear(Usuario pUsuario);
	void borrar(Usuario pUsuario);
	void actualizar(Usuario pUsuario);
	boolean tienePermiso(String pPermiso);
	List<Rol> listarRoles(Usuario pUsuario);
}
