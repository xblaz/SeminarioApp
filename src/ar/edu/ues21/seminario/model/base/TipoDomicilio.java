package ar.edu.ues21.seminario.model.base;

import java.util.Objects;

public class TipoDomicilio {
    private Long codigo;
    private String descripcion;
    public TipoDomicilio(){}
    public TipoDomicilio(Long codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoDomicilio that)) return false;
        return getCodigo().equals(that.getCodigo()) && getDescripcion().equals(that.getDescripcion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getDescripcion());
    }

    @Override
    public String toString() {
        return "TipoDomicilio{" +
                "codigo=" + codigo +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
