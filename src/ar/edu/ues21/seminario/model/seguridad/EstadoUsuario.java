package ar.edu.ues21.seminario.model.seguridad;

import java.util.HashMap;
import java.util.Map;

public enum EstadoUsuario {
    ACTIVO("Activo"), NO_ACTIVO("Desactivado");
    private final String estado;
    private static final Map<String, EstadoUsuario> CODIGO = new HashMap<>();
    EstadoUsuario(String estado) {
        this.estado = estado;
    }

    static {
        for (EstadoUsuario e: values()) {
            CODIGO.put(e.getEstado(), e);
        }
    }
    public String getEstado() {
        return estado;
    }
    public static EstadoUsuario valueOfCodigo(String codigo) {
        return CODIGO.get(codigo);
    }
}
