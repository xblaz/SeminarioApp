package seminario.utils;

import seminario.model.seguridad.Usuario;


public class Session {
    
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
        DatabaseConexion.cerrarConexion();
    }
}
