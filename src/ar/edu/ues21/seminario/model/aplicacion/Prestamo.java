package ar.edu.ues21.seminario.model.aplicacion;

import ar.edu.ues21.seminario.model.LogicaException;
import ar.edu.ues21.seminario.model.seguridad.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Prestamo {
    private Long idPrestamo;
    private Long nroPrestamo;
    private LocalDate fechaOtorgamiento;
    private BigDecimal monto;
    private Integer cantidadCuota;
    private Usuario usuarioAutorizador;
    private Usuario usuarioGenerador;
    private final List<Garante> garantes = new ArrayList<>();
    private EstadoPrestamo estado;
    private Cliente cliente;
    private EsquemaFinanciacion esquema;
    private List<Cuota> cuotas = new ArrayList<>();

    public Prestamo(Long nroPrestamo, BigDecimal monto, EsquemaFinanciacion esquema) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        this.nroPrestamo = Objects.requireNonNull(nroPrestamo, "El número de préstamo no puede ser nulo");
        this.monto = monto;
        this.esquema = Objects.requireNonNull(esquema, "El esquema de financiación no puede ser nulo");
        this.estado = EstadoPrestamo.PENDIENTE;
    }

    public BigDecimal totalPagado() {
        return cuotas.stream()
                .filter(c -> c.getEstado().equals(EstadoCobro.COBRADO)) // suponiendo que exista ese método
                .map(Cuota::getMontoCuota)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Long idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Long getNroPrestamo() {
        return nroPrestamo;
    }

    public void setNroPrestamo(Long nroPrestamo) {
        this.nroPrestamo = nroPrestamo;
    }

    public LocalDate getFechaOtorgamiento() {
        return fechaOtorgamiento;
    }

    public void setFechaOtorgamiento(LocalDate fechaOtorgamiento) {
        this.fechaOtorgamiento = fechaOtorgamiento;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new LogicaException("Monto a financiar debe ser positivo");
        }
        this.monto = monto;
    }

    public Integer getCantidadCuota() {
        return cantidadCuota;
    }

    public void setCantidadCuota(Integer cantidadCuota) {
        this.cantidadCuota = cantidadCuota;
    }

    public Usuario getUsuarioAutorizador() {
        return usuarioAutorizador;
    }

    public void setUsuarioAutorizador(Usuario usuarioAutorizador) {
        this.usuarioAutorizador = usuarioAutorizador;
    }

    public Usuario getUsuarioGenerador() {
        return usuarioGenerador;
    }

    public void setUsuarioGenerador(Usuario usuarioGenerador) {
        this.usuarioGenerador = usuarioGenerador;
    }

    public List<Garante> getGarantes() {
        return garantes;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EsquemaFinanciacion getEsquema() {
        return esquema;
    }

    public void setEsquema(EsquemaFinanciacion esquema) {
        this.esquema = esquema;
    }

    public List<Cuota> getCuotas() {
        return cuotas;
    }

    public void setCuotas(List<Cuota> cuotas) {
        this.cuotas = cuotas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prestamo prestamo)) return false;
        return Objects.equals(idPrestamo, prestamo.idPrestamo);
    }
    @Override
    public int hashCode() {
        return Objects.hash(idPrestamo);
    }
}
