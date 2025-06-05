package ar.edu.ues21.seminario.model.cobro;

import ar.edu.ues21.seminario.exception.LogicaException;
import ar.edu.ues21.seminario.utils.Util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class DebitoAutomatico implements MedioPago {
    private String banco;
    private String numeroCuenta;
    private String cbu;
    private String titular;
    private LocalDate fechaOperacion;

    public DebitoAutomatico(String banco, String numeroCuenta, String cbu, String titular, LocalDate fechaOperacion) {
        this.banco = banco;
        this.numeroCuenta = numeroCuenta;
        this.cbu = cbu;
        this.titular = titular;
        this.fechaOperacion = fechaOperacion;
    }

    @Override
    public void registrarCobro(Pago pPago) throws LogicaException {
        validarPago(pPago);
        // Logica
    }
    @Override
    public String descripcion() {
        return String.format("Debito automatico en %s - número de cuenta: %s - fecha %s",
                banco, numeroCuenta, Util.formatearFecha(fechaOperacion));
    }
    private void validarPago(Pago pago) throws LogicaException {
        if (pago == null) {
            throw new LogicaException("El pago no puede ser nulo");
        }
        if (pago.getMontoPagado().compareTo(BigDecimal.ZERO) <= 0) {
            throw new LogicaException("El monto pagado debe ser mayor a cero");
        }
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public LocalDate getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(LocalDate fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DebitoAutomatico that)) return false;
        return Objects.equals(banco, that.banco) && Objects.equals(numeroCuenta, that.numeroCuenta) && Objects.equals(titular, that.titular) && Objects.equals(fechaOperacion, that.fechaOperacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(banco, numeroCuenta, titular, fechaOperacion);
    }
}
