package ar.edu.ues21.seminario.model.base;

public enum TipoTelefono {
    PARTICULAR("Particular"), LABORAL("Laboral");
    private final String tipo;
    TipoTelefono(String tipo) {
        this.tipo = tipo;
    }
    public String getTipo() {
        return tipo;
    }
}
