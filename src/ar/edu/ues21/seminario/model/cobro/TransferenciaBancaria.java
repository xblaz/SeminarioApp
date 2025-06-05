package ar.edu.ues21.seminario.model.cobro;

import ar.edu.ues21.seminario.exception.LogicaException;
import ar.edu.ues21.seminario.utils.Util;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferenciaBancaria implements MedioPago {
    private final String numeroTransaccion;
    private final String cbu;
    private final String banco;
    private final LocalDate fechaTransaccion;

    public TransferenciaBancaria(String numeroTransaccion, String cbu, String banco, LocalDate fechaTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
        this.cbu = cbu;
        this.banco = banco;
        this.fechaTransaccion = fechaTransaccion;
    }

    @Override
    public void registrarCobro(Pago pPago) throws LogicaException {
        validarPago(pPago);
    }

    @Override
    public String descripcion() {
        return String.format("Transferencia Bancaria %s - cbu %s - fecha %s", banco, cbu, Util.formatearFecha(fechaTransaccion));
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
