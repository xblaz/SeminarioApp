package ar.edu.ues21.seminario.model.base;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Persona {
    private Long idPersona;
    private String email;
    private LocalDate fechaRegistro;
    List<Domicilio> domicilios = new ArrayList<>();
    List<Telefono> telefonos = new ArrayList<>();

    public Persona(Long idPersona, String email, LocalDate fechaRegistro, List<Domicilio> domicilios, List<Telefono> telefonos) {
        this.idPersona = idPersona;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
        this.domicilios = domicilios;
        this.telefonos = telefonos;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Domicilio> getDomicilios() {
        return domicilios;
    }

    public void setDomicilios(List<Domicilio> domicilios) {
        this.domicilios = domicilios;
    }

    public void agregarDomicilio(Domicilio domicilio) {
        if (domicilio != null) {
            domicilios.add(domicilio);
        }
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public void agregarTelefono(Telefono telefono) {
        if (telefono != null) {
            telefonos.add(telefono);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona persona)) return false;
        return Objects.equals(email, persona.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
