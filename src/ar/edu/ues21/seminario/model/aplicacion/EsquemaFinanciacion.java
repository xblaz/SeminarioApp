package ar.edu.ues21.seminario.model.aplicacion;

import java.math.BigDecimal;
import java.util.List;

public abstract class EsquemaFinanciacion {
    private Long idEsquema;
    private String descripcion;
    private Double tasaInteres;
    private Integer cantidadCuotas;
    private Boolean requiereGarante;
    private EstadoEsquema estado;
    public EsquemaFinanciacion() {
    }
    public EsquemaFinanciacion(String descripcion, Double tasaInteres, Integer cantidadCuotas, Boolean requiereGarante, EstadoEsquema estado) {
        this.descripcion = descripcion;
        this.tasaInteres = tasaInteres;
        this.cantidadCuotas = cantidadCuotas;
        this.requiereGarante = requiereGarante;
        this.estado = estado;
    }

    public Long getIdEsquema() {
        return idEsquema;
    }

    public void setIdEsquema(Long idEsquema) {
        this.idEsquema = idEsquema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(Double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public Integer getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(Integer cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public Boolean getRequiereGarante() {
        return requiereGarante;
    }

    public void setRequiereGarante(Boolean requiereGarante) {
        this.requiereGarante = requiereGarante;
    }

    public EstadoEsquema getEstado() {
        return estado;
    }

    public void setEstado(EstadoEsquema estado) {
        this.estado = estado;
    }

    public abstract BigDecimal calcularCuota(BigDecimal montoAFinanciar, Integer periodo);

    public abstract List<Cuota> generarPrestamo(Prestamo prestamo);
}
