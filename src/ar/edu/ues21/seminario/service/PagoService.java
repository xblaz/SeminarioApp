package ar.edu.ues21.seminario.service;

import ar.edu.ues21.seminario.model.LogicaException;
import ar.edu.ues21.seminario.model.cobro.MedioPago;
import ar.edu.ues21.seminario.model.cobro.Pago;

import java.util.Objects;

public class PagoService {
    private MedioPago medioPago;

    public PagoService(MedioPago medioPago) {
        this.medioPago = Objects.requireNonNull(medioPago);
    }

    public void cambiarMedioPago(MedioPago medioPago) {
        this.medioPago = Objects.requireNonNull(medioPago);
    }

    public void procesarPago(Pago pago) throws LogicaException {
        medioPago.registrarCobro(pago);
        System.out.println(medioPago.descripcion());
    }
}
