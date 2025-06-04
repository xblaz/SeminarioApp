package ar.edu.ues21.seminario.model.aplicacion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Cuota {
    private Long idCuota;
    private Integer numeroCuota;
    private BigDecimal montoCuota;
    private LocalDate fechaVencimiento;
    private LocalDate fechaPago;
    private BigDecimal intereses;
    private BigDecimal amortizacionCapital;
    private BigDecimal saldoPendiente;
    private EstadoCobro estado;

    public Cuota() {
        this.estado = EstadoCobro.NO_COBRADO;
    }

    public Cuota(Integer numeroCuota, LocalDate fechaVencimiento, BigDecimal monto,
                 BigDecimal intereses, BigDecimal amortizacionCapital, BigDecimal saldoPendiente) {
        this.numeroCuota = numeroCuota;
        this.fechaVencimiento = fechaVencimiento;
        this.montoCuota = monto;
        this.intereses = intereses;
        this.amortizacionCapital = amortizacionCapital;
        this.saldoPendiente = saldoPendiente;
    }

    public Long getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(Long idCuota) {
        this.idCuota = idCuota;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getIntereses() {
        return intereses;
    }

    public void setIntereses(BigDecimal interes) {
        this.intereses = interes;
    }

    public EstadoCobro getEstado() {
        return estado;
    }

    public void setEstado(EstadoCobro estado) {
        this.estado = estado;
    }

    public BigDecimal getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(BigDecimal montoCuota) {
        this.montoCuota = montoCuota;
    }

    public BigDecimal getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(BigDecimal saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public Integer getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public BigDecimal getAmortizacionCapital() {
        return amortizacionCapital;
    }

    public void setAmortizacionCapital(BigDecimal amortizacionCapital) {
        this.amortizacionCapital = amortizacionCapital;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuota cuota)) return false;
        return Objects.equals(idCuota, cuota.idCuota) && Objects.equals(numeroCuota, cuota.numeroCuota) && Objects.equals(montoCuota, cuota.montoCuota) && Objects.equals(fechaVencimiento, cuota.fechaVencimiento) && Objects.equals(fechaPago, cuota.fechaPago) && Objects.equals(intereses, cuota.intereses) && Objects.equals(amortizacionCapital, cuota.amortizacionCapital);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCuota, numeroCuota, montoCuota, fechaVencimiento, fechaPago, intereses, amortizacionCapital);
    }

    @Override
    public String toString() {
        String sb = "Cuota {" + "idCuota=" + idCuota +
                ", numeroCuota=" + numeroCuota +
                ", montoCuota=" + montoCuota +
                ", fechaVencimiento=" + fechaVencimiento +
                ", fechaPago=" + fechaPago +
                ", intereses=" + intereses +
                ", amortizacionCapital=" + amortizacionCapital +
                '}';
        return sb;
    }
}
