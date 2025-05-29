package ar.edu.ues21.seminario.view;

import ar.edu.ues21.seminario.model.seguridad.Usuario;

public class SessionManager {
    
    private static Usuario usuario;

    public static void setUsuario(Usuario u) {
        usuario = u;
    }
    public static Usuario getUsuario() {
        return usuario;
    }

    public static boolean estaAutenticado() {
        return usuario != null;
    }

    public static void cerrarSesion() {
        usuario = null;
    }

    public static boolean tieneRol(String rol) {
        return usuario != null &&
                usuario.getListaRoles().stream()
                        .anyMatch(r -> r.getNombre().equalsIgnoreCase(rol));
    }

    public static boolean tienePermiso(String permiso) {
        return usuario != null &&
                usuario.tienePermiso(permiso);
    }
}
