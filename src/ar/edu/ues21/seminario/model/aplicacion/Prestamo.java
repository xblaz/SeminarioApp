package ar.edu.ues21.seminario.model.aplicacion;

import ar.edu.ues21.seminario.model.seguridad.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prestamo {
    private Long idPrestamo;
    private Long nroPrestamo;
    private LocalDate fechaOtorgamiento;
    private BigDecimal monto;
    private Integer cantidadCuota;
    private Usuario usuarioAutorizador;
    private Usuario usuarioGenerador;
    private List<Garante> garantes = new ArrayList<>();
    private EstadoPrestamo estado;
    private Cliente cliente;
    private EsquemaFinanciacion esquema;
}
