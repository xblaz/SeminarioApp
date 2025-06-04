package ar.edu.ues21.seminario.model.aplicacion;

import ar.edu.ues21.seminario.model.base.PersonaFisica;

import java.time.LocalDate;

public class Garante {
    private Long idGarante;
    private String relacionDeudor;
    private Double porcentajeResponsabilidad;
    private LocalDate fechaFirmaGarante;
    private PersonaFisica garante;
}
