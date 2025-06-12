package ar.edu.ues21.seminario.model.aplicacion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SistemaAleman extends EsquemaFinanciacion {
    public SistemaAleman() {
        setTipoEsquema(TipoEsquema.ALEMAN);
    }

    public SistemaAleman(Long idEsquema, String descripcion, Double tasaInteres, Integer cantidadCuotas, Boolean requiereGarante, LocalDate fechaCreacion, EstadoEsquema estado, TipoEsquema tipoEsquema) {
        super(idEsquema, descripcion, tasaInteres, cantidadCuotas, requiereGarante, fechaCreacion, estado, tipoEsquema);
        setTipoEsquema(TipoEsquema.ALEMAN);
    }

    @Override
    public BigDecimal calcularCuota(BigDecimal montoAFinanciar, Integer periodo) {
        if (periodo <= 0 || periodo > getCantidadCuotas()) {
            throw new IllegalArgumentException("Período inválido");
        }

        BigDecimal amortizacionCapital = montoAFinanciar.divide(
                BigDecimal.valueOf(getCantidadCuotas()), 2, RoundingMode.HALF_UP
        );

        BigDecimal saldoPendiente = montoAFinanciar.subtract(
                amortizacionCapital.multiply(BigDecimal.valueOf(periodo - 1))
        );

        BigDecimal intereses = saldoPendiente.multiply(
                BigDecimal.valueOf(getTasaInteres() / 100 / 12) // Tasa mensual
        ).setScale(2, RoundingMode.HALF_UP);

        return amortizacionCapital.add(intereses);
    }

    @Override
    public List<Cuota> generarPrestamo(Prestamo prestamo) {
        List<Cuota> cuotas = new ArrayList<>();
        BigDecimal monto = prestamo.getMonto();
        BigDecimal tasaMensual = BigDecimal.valueOf(getTasaInteres() / 100 / 12);
        BigDecimal amortizacionCapital = monto.divide(
                new BigDecimal(getCantidadCuotas()), 2, RoundingMode.HALF_UP
        );
        BigDecimal saldo = monto;

        for (int i = 1; i <= getCantidadCuotas(); i++) {
            LocalDate fechaVencimiento = prestamo.getFechaOtorgamiento().plusMonths(i);
            BigDecimal intereses = saldo.multiply(tasaMensual).setScale(2, RoundingMode.HALF_UP);
            BigDecimal cuotaTotal = amortizacionCapital.add(intereses);
            saldo = saldo.subtract(amortizacionCapital);

            cuotas.add(new Cuota(
                    i,
                    fechaVencimiento,
                    cuotaTotal,
                    intereses,
                    amortizacionCapital,
                    saldo.compareTo(BigDecimal.ZERO) > 0 ? saldo : BigDecimal.ZERO
            ));
        }

        return cuotas;
    }
}
