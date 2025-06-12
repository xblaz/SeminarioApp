package ar.edu.ues21.seminario.model.aplicacion;

import ar.edu.ues21.seminario.model.seguridad.EstadoUsuario;

import java.util.HashMap;
import java.util.Map;

public enum EstadoEsquema {
    ACTIVO("Activo"), NO_ACTIVO("Desactivado"), ELIMINADO("Eliminado");
    private String descripcion;
    private static final Map<String, EstadoEsquema> CODIGO = new HashMap<>();

    EstadoEsquema(String descripcion) {
        this.descripcion = descripcion;
    }

    static {
        for (EstadoEsquema e: values()) {
            CODIGO.put(e.getDescripcion(), e);
        }
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public static EstadoEsquema valueOfCodigo(String codigo) {
        return CODIGO.get(codigo);
    }
}
