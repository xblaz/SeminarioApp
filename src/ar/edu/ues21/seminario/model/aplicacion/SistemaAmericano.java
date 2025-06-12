package ar.edu.ues21.seminario.model.aplicacion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SistemaAmericano extends EsquemaFinanciacion {
    public SistemaAmericano(Long idEsquema, String descripcion, Double tasaInteres, Integer cantidadCuotas, Boolean requiereGarante, LocalDate fechaCreacion, EstadoEsquema estado, TipoEsquema tipoEsquema) {
        super(idEsquema, descripcion, tasaInteres, cantidadCuotas, requiereGarante, fechaCreacion, estado, tipoEsquema);
    }
    public SistemaAmericano() {
        this.setTipoEsquema(TipoEsquema.AMERICANO);
    }

    @Override
    public BigDecimal calcularCuota(BigDecimal montoAFinanciar, Integer periodo) {
        if (periodo <= 0 || periodo > getCantidadCuotas()) {
            throw new IllegalArgumentException("Período inválido");
        }

        BigDecimal intereses = montoAFinanciar.multiply(
                BigDecimal.valueOf(getTasaInteres() / 100 / 12) // Tasa mensual
        ).setScale(2, RoundingMode.HALF_UP);

        // Última cuota: intereses + capital
        if (periodo == getCantidadCuotas()) {
            return intereses.add(montoAFinanciar);
        }

        return intereses; // Cuotas normales: solo intereses
    }

    @Override
    public List<Cuota> generarPrestamo(Prestamo prestamo) {
        List<Cuota> cuotas = new ArrayList<>();
        BigDecimal monto = prestamo.getMonto();
        BigDecimal tasaMensual = BigDecimal.valueOf(getTasaInteres() / 100 / 12);
        BigDecimal saldo = monto;

        for (int i = 1; i <= getCantidadCuotas(); i++) {
            LocalDate fechaVencimiento = prestamo.getFechaOtorgamiento().plusMonths(i);
            BigDecimal intereses = saldo.multiply(tasaMensual).setScale(2, RoundingMode.HALF_UP);
            BigDecimal amortizacion = (i == getCantidadCuotas()) ? saldo : BigDecimal.ZERO;
            BigDecimal cuotaTotal = intereses.add(amortizacion);
            saldo = saldo.subtract(amortizacion);

            cuotas.add(new Cuota(
                    i,
                    fechaVencimiento,
                    cuotaTotal,
                    intereses,
                    amortizacion,
                    saldo.compareTo(BigDecimal.ZERO) > 0 ? saldo : BigDecimal.ZERO
            ));
        }

        return cuotas;
    }
}
