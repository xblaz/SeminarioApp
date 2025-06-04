package ar.edu.ues21.seminario.model.aplicacion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SistemaFrances extends EsquemaFinanciacion {
    public SistemaFrances(String descripcion, Double tasaInteres, Integer cantidadCuotas, Boolean requiereGarante, EstadoEsquema estado) {
        super(descripcion, tasaInteres, cantidadCuotas, requiereGarante, estado);
    }
    @Override
    public BigDecimal calcularCuota(BigDecimal montoAFinanciar, Integer periodo) {
        // Validaciones básicas
        if (montoAFinanciar.compareTo(BigDecimal.ZERO) <= 0 || periodo <= 0) {
            throw new IllegalArgumentException("El monto a financiar y el plazo deben ser positivos.");
        }
        // Convertir tasa anual a mensual y a decimal (ej: 12% -> 0.01)
        BigDecimal tasaInteresMensual = BigDecimal.valueOf(getTasaInteres() / 100 / 12);
        // Fórmula: C = (P * i * (1 + i)^n) / ((1 + i)^n - 1)
        BigDecimal potencia = BigDecimal.ONE.add(tasaInteresMensual).pow(getCantidadCuotas());
        BigDecimal numerador = montoAFinanciar.multiply(tasaInteresMensual).multiply(potencia);
        BigDecimal denominador = potencia.subtract(BigDecimal.ONE);
        BigDecimal cuota = numerador.divide(denominador, 2, RoundingMode.HALF_UP);
        return cuota;
    }
    @Override
    public List<Cuota> generarPrestamo(Prestamo prestamo) {
        List<Cuota> cuotas = new ArrayList<>();
        BigDecimal monto = prestamo.getMonto();
        BigDecimal tasaMensual = BigDecimal.valueOf(getTasaInteres() / 100 / 12);
        BigDecimal cuotaFija = calcularCuota(monto, getCantidadCuotas());
        BigDecimal saldo = monto;

        for (int i = 1; i <= getCantidadCuotas(); i++) {
            LocalDate fechaVencimiento = prestamo.getFechaOtorgamiento().plusMonths(i);
            BigDecimal intereses = saldo.multiply(tasaMensual).setScale(2, RoundingMode.HALF_UP);
            BigDecimal amortizacion = cuotaFija.subtract(intereses);
            saldo = saldo.subtract(amortizacion);
            cuotas.add(new Cuota(
                    i,
                    fechaVencimiento,
                    cuotaFija,
                    intereses,
                    amortizacion,
                    saldo.compareTo(BigDecimal.ZERO) > 0 ? saldo : BigDecimal.ZERO
            ));
        }
        return cuotas;
    }
}
