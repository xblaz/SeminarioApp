package ar.edu.ues21.seminario.model.aplicacion;

import ar.edu.ues21.seminario.model.base.PersonaFisica;

import java.time.LocalDate;

public class Garante {
    private Long idGarante;
    private String relacionDeudor;
    private Double porcentajeResponsabilidad;
    private LocalDate fechaFirmaGarante;
    private PersonaFisica garante;

    public Garante(){}
    public Garante(Long idGarante, String relacionDeudor, Double porcentajeResponsabilidad, LocalDate fechaFirmaGarante, PersonaFisica garante) {
        this.idGarante = idGarante;
        this.relacionDeudor = relacionDeudor;
        this.porcentajeResponsabilidad = porcentajeResponsabilidad;
        this.fechaFirmaGarante = fechaFirmaGarante;
        this.garante = garante;
    }

    public Long getIdGarante() {
        return idGarante;
    }

    public void setIdGarante(Long idGarante) {
        this.idGarante = idGarante;
    }

    public String getRelacionDeudor() {
        return relacionDeudor;
    }

    public void setRelacionDeudor(String relacionDeudor) {
        this.relacionDeudor = relacionDeudor;
    }

    public Double getPorcentajeResponsabilidad() {
        return porcentajeResponsabilidad;
    }

    public void setPorcentajeResponsabilidad(Double porcentajeResponsabilidad) {
        this.porcentajeResponsabilidad = porcentajeResponsabilidad;
    }

    public LocalDate getFechaFirmaGarante() {
        return fechaFirmaGarante;
    }

    public void setFechaFirmaGarante(LocalDate fechaFirmaGarante) {
        this.fechaFirmaGarante = fechaFirmaGarante;
    }

    public PersonaFisica getGarante() {
        return garante;
    }

    public void setGarante(PersonaFisica garante) {
        this.garante = garante;
    }
}
