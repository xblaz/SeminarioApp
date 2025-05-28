package ar.edu.ues21.seminario.dao.seguridad;

import java.util.List;

import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;

public interface UsuarioDao {
    public void crear(Usuario pUsuario);
	public void borrar(Usuario pUsuario);
	public void actualizar(Usuario pUsuario);
	public Usuario login(String pUsuario, String pClave);
	public boolean tienePermiso(String pPermiso);
	public List<Rol> listarRoles(Usuario pUsuario);
}
