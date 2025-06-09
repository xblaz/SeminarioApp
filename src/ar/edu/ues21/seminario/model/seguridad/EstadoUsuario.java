package ar.edu.ues21.seminario.model.seguridad;

import java.util.HashMap;
import java.util.Map;

public enum EstadoUsuario {

    ACTIVO("Activo"), NO_ACTIVO("Desactivado"), ELIMINADO("Eliminado");
    private final String descripcion;
    private static final Map<String, EstadoUsuario> CODIGO = new HashMap<>();

    EstadoUsuario(String descripcion) {
        this.descripcion = descripcion;
    }

    static {
        for (EstadoUsuario e: values()) {
            CODIGO.put(e.getDescripcion(), e);
        }
    }
    public String getDescripcion() {
        return descripcion;
    }
    public static EstadoUsuario valueOfCodigo(String codigo) {
        return CODIGO.get(codigo);
    }
}
