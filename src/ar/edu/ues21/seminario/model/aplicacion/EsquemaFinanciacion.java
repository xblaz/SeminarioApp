package ar.edu.ues21.seminario.model.aplicacion;

import ar.edu.ues21.seminario.model.Columna;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public abstract class EsquemaFinanciacion {
    @Columna(nombre = "id")
    private Long idEsquema;
    @Columna(nombre = "descripcion")
    private String descripcion;
    @Columna(nombre = "tasa_interes_anual")
    private Double tasaInteres;
    @Columna(nombre = "cantidad_cuotas")
    private Integer cantidadCuotas;
    @Columna(nombre = "requiere_garante")
    private Boolean requiereGarante;
    @Columna(nombre = "fecha_creacion")
    private LocalDate fechaCreacion;
    @Columna(nombre = "estado")
    private EstadoEsquema estado;
    @Columna(nombre = "tipo")
    private TipoEsquema tipoEsquema;


    public EsquemaFinanciacion() {
    }

    public EsquemaFinanciacion(Long idEsquema, String descripcion, Double tasaInteres, Integer cantidadCuotas, Boolean requiereGarante, LocalDate fechaCreacion, EstadoEsquema estado, TipoEsquema tipoEsquema) {
        this.idEsquema = idEsquema;
        this.descripcion = descripcion;
        this.tasaInteres = tasaInteres;
        this.cantidadCuotas = cantidadCuotas;
        this.requiereGarante = requiereGarante;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.tipoEsquema = tipoEsquema;
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

    public EstadoEsquema getEstado() {return estado; }
    public void setEstado(EstadoEsquema estado) {
        this.estado = estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public TipoEsquema getTipoEsquema() {
        return tipoEsquema;
    }

    public void setTipoEsquema(TipoEsquema tipoEsquema) {
        this.tipoEsquema = tipoEsquema;
    }


    public abstract BigDecimal calcularCuota(BigDecimal montoAFinanciar, Integer periodo);

    public abstract List<Cuota> generarPrestamo(Prestamo prestamo);

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EsquemaFinanciacion{");
        sb.append("descripcion='").append(descripcion).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
