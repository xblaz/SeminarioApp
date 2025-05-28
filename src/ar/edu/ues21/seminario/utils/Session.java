package ar.edu.ues21.seminario.utils;

import ar.edu.ues21.seminario.model.seguridad.Usuario;


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
