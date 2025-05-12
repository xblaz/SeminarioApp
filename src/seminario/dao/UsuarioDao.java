package seminario.dao;

import seminario.model.seguridad.Usuario;

public interface UsuarioDao {
    public void crear(Usuario pUsuario);
	public void borrar(Usuario pUsuario);
	public void actualizar(Usuario pUsuario);
	public Usuario login(String pUsuario, String pClave);
	public boolean tienePermiso(String pPermiso);
}
