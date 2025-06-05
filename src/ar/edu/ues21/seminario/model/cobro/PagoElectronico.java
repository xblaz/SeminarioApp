package ar.edu.ues21.seminario.model.cobro;

import ar.edu.ues21.seminario.exception.LogicaException;
import ar.edu.ues21.seminario.utils.Util;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagoElectronico implements MedioPago {
    private String plataforma;  // Ej: "MercadoPago"
    private String idTransaccion;
    private String emailUsuario;
    private String tokenSeguridad;
    private LocalDate fechaOperacion;

    public PagoElectronico() {
    }

    public PagoElectronico(String plataforma, String idTransaccion, String emailUsuario, String tokenSeguridad, LocalDate fechaOperacion) {
        this.plataforma = plataforma;
        this.idTransaccion = idTransaccion;
        this.emailUsuario = emailUsuario;
        this.tokenSeguridad = tokenSeguridad;
        this.fechaOperacion = fechaOperacion;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getTokenSeguridad() {
        return tokenSeguridad;
    }

    public void setTokenSeguridad(String tokenSeguridad) {
        this.tokenSeguridad = tokenSeguridad;
    }

    @Override
    public void registrarCobro(Pago pPago) throws LogicaException {
        validarPago(pPago);
    }

    @Override
    public String descripcion() {
        return String.format("Pago electronico plataforma %s - email %s - fecha operación %s", plataforma, emailUsuario, Util.formatearFecha(fechaOperacion));
    }

    private void validarPago(Pago pago) throws LogicaException {
        if (pago == null) {
            throw new LogicaException("El pago no puede ser nulo");
        }
        if (pago.getMontoPagado().compareTo(BigDecimal.ZERO) <= 0) {
            throw new LogicaException("El monto pagado debe ser mayor a cero");
        }
    }
}