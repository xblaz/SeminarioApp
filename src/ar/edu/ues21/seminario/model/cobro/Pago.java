package ar.edu.ues21.seminario.model.cobro;

import ar.edu.ues21.seminario.model.aplicacion.Cuota;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Pago {
    private Long idPago;
    private BigDecimal montoPagado;
    private Cuota cuota;
    private LocalDate fechaPago;
    private MedioPago medioPago;
    private BigDecimal recargos;
    private EstadoPago estadoPago;

    public Pago() {
    }

    public Pago(BigDecimal montoPagado, Cuota cuota, LocalDate fechaPago, MedioPago medioPago, BigDecimal recargos, EstadoPago estadoPago) {
        this.montoPagado = montoPagado;
        this.cuota = cuota;
        this.fechaPago = fechaPago;
        this.medioPago = medioPago;
        this.recargos = recargos;
        this.estadoPago = estadoPago;
    }

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    public Cuota getCuota() {
        return cuota;
    }

    public void setCuota(Cuota cuota) {
        this.cuota = cuota;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public MedioPago getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(MedioPago medioPago) {
        this.medioPago = medioPago;
    }

    public BigDecimal getRecargos() {
        return recargos;
    }

    public void setRecargos(BigDecimal recargos) {
        this.recargos = recargos;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pago pago)) return false;
        return Objects.equals(montoPagado, pago.montoPagado) && Objects.equals(cuota, pago.cuota) && Objects.equals(medioPago, pago.medioPago);
    }

    @Override
    public int hashCode() {
        return Objects.hash(montoPagado, cuota, medioPago);
    }

    @Override
    public String toString() {
        String sb = "Pago{" + "idPago=" + idPago +
                ", montoPagado=" + montoPagado +
                ", cuota=" + cuota +
                ", fechaPago=" + fechaPago +
                ", medioPago=" + medioPago +
                ", recargos=" + recargos +
                ", estadoPago=" + estadoPago +
                '}';
        return sb;
    }
}
