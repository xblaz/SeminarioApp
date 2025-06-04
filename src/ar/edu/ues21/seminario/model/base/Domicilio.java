package ar.edu.ues21.seminario.model.base;

import java.util.Objects;

public class Domicilio {
    private Long idDomicilio;
    private String calle;
    private Integer numero;
    private String piso;
    private String codigoPostal;
    private Integer localidad;
    private Integer provincia;
    private TipoDomicilio tipoDomicilio;

    public Domicilio(){}
    public Domicilio(Long idDomicilio,String calle, Integer numero, String piso, String codigoPostal, Integer localidad, Integer provincia, TipoDomicilio tipoDomicilio) {
        this.idDomicilio = idDomicilio;
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.tipoDomicilio = tipoDomicilio;
    }

    public Long getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(Long idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Integer getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Integer localidad) {
        this.localidad = localidad;
    }

    public Integer getProvincia() {
        return provincia;
    }

    public void setProvincia(Integer provincia) {
        this.provincia = provincia;
    }

    public TipoDomicilio getTipoDomicilio() {
        return tipoDomicilio;
    }

    public void setTipoDomicilio(TipoDomicilio tipoDomicilio) {
        this.tipoDomicilio = tipoDomicilio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Domicilio)) return false;
        Domicilio domicilio = (Domicilio) o;
        return getIdDomicilio().equals(domicilio.getIdDomicilio()) && getCalle().equals(domicilio.getCalle()) && getNumero().equals(domicilio.getNumero()) && Objects.equals(getPiso(), domicilio.getPiso()) && Objects.equals(getCodigoPostal(), domicilio.getCodigoPostal()) && getLocalidad().equals(domicilio.getLocalidad()) && getProvincia().equals(domicilio.getProvincia()) && Objects.equals(getTipoDomicilio(), domicilio.getTipoDomicilio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdDomicilio(), getCalle(), getNumero(), getPiso(), getCodigoPostal(), getLocalidad(), getProvincia(), getTipoDomicilio());
    }

    @Override
    public String toString() {
        return "Domicilio{" +
                "idDomicilio=" + idDomicilio +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                ", piso='" + piso + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", localidad=" + localidad +
                ", provincia=" + provincia +
                ", tipoDomicilio=" + tipoDomicilio +
                '}';
    }
}
