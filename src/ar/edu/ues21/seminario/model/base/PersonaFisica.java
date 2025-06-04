package ar.edu.ues21.seminario.model.base;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PersonaFisica extends Persona {
    private String nombre;
    private String apellido;
    private Long dni;
    private LocalDate fechaNacimiento;
    private Character genero;
    public PersonaFisica(Long idPersona, String email, LocalDate fechaRegistro, List<Domicilio> domicilios, List<Telefono> telefonos) {
        super(idPersona, email, fechaRegistro, domicilios, telefonos);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonaFisica that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getNombre(), that.getNombre()) && Objects.equals(getApellido(), that.getApellido()) && Objects.equals(getDni(), that.getDni());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNombre(), getApellido(), getDni());
    }
}
