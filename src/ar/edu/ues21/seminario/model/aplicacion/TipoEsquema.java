package ar.edu.ues21.seminario.model.aplicacion;

public enum TipoEsquema {
    FRANCES("Sistema Frances"), ALEMAN("Sistema Aleman"), AMERICANO("Sistema Americano");
    private String descripcion;

    TipoEsquema(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
