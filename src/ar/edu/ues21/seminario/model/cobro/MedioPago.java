package ar.edu.ues21.seminario.model.cobro;

import ar.edu.ues21.seminario.exception.LogicaException;

public interface MedioPago {
    void registrarCobro(Pago pPago) throws LogicaException;
    String descripcion();
}
