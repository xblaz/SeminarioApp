package ar.edu.ues21.seminario.model.aplicacion;

import ar.edu.ues21.seminario.model.base.Persona;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cliente {
    private Long nroCliente;
    private LocalDate fechaAlta;
    private Persona persona;
    private List<Prestamo> prestamos = new ArrayList<>();

    public Cliente(){}
    public Cliente(Long nroCliente, LocalDate fechaAlta, Persona persona) {
        this.nroCliente = nroCliente;
        this.fechaAlta = fechaAlta;
        this.persona = persona;
    }

    public BigDecimal obtenerSaldoConsolidado() {
        return BigDecimal.ZERO;
    }
    public Map<String, BigDecimal> consultarSaldosDetallados() {
        Map<String, BigDecimal> saldos = new LinkedHashMap<>();
        for (Prestamo prestamo : prestamos) {
            // Recorro los prestamos para obtener saldos
        }
        return saldos;
    }

    public Long getNroCliente() {
        return nroCliente;
    }

    public void setNroCliente(Long nroCliente) {
        this.nroCliente = nroCliente;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
}
