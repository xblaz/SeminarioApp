package ar.edu.ues21.seminario.model.base;

import java.util.Objects;

public class Telefono {
    private Long id;
    private Integer numero;
    private Integer codArea;
    private TipoTelefono tipo;
    public Telefono(){}
    public Telefono(Integer numero, Integer codArea, TipoTelefono tipo) {
        this.numero = numero;
        this.codArea = codArea;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof Telefono)) return false;
        Telefono telefono = (Telefono) o;
        return Objects.equals(getId(), telefono.getId()) && Objects.equals(getNumero(), telefono.getNumero()) && Objects.equals(getCodArea(), telefono.getCodArea()) && getTipo() == telefono.getTipo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumero(), getCodArea(), getTipo());
    }

    @Override
    public String toString() {
        return "Telefono{" +
                "id=" + id +
                ", numero=" + numero +
                ", codArea=" + codArea +
                ", tipo=" + tipo +
                '}';
    }

}
