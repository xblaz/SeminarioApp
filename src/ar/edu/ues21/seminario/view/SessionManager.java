package ar.edu.ues21.seminario.view;

import ar.edu.ues21.seminario.model.seguridad.Usuario;
import javafx.stage.Stage;

public class SessionManager {

    private static Usuario usuario;
    private static Stage primaryStage;

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
        if (primaryStage != null) {
            primaryStage.close();
        }
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

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
}
