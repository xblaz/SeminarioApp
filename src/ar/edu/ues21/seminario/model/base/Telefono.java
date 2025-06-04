package ar.edu.ues21.seminario.model.base;

import java.util.Objects;

public class Telefono {
    private Long idTelefono;
    private Integer numero;
    private Integer codArea;
    private TipoTelefono tipo;

    public Telefono() {
    }
    public Telefono(Integer numero, Integer codArea, TipoTelefono tipo) {
        this.numero = numero;
        this.codArea = codArea;
        this.tipo = tipo;
    }

    public Long getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(Long idTelefono) {
        this.idTelefono = idTelefono;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getCodArea() {
        return codArea;
    }

    public void setCodArea(Integer codArea) {
        this.codArea = codArea;
    }

    public TipoTelefono getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefono tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Telefono telefono)) return false;
        return Objects.equals(getIdTelefono(), telefono.getIdTelefono()) && Objects.equals(getNumero(), telefono.getNumero()) && Objects.equals(getCodArea(), telefono.getCodArea()) && getTipo() == telefono.getTipo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdTelefono(), getNumero(), getCodArea(), getTipo());
    }

    @Override
    public String toString() {
        return "Telefono{" +
                "id=" + idTelefono +
                ", numero=" + numero +
                ", codArea=" + codArea +
                ", tipo=" + tipo +
                '}';
    }

}
