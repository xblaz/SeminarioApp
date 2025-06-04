package ar.edu.ues21.seminario.model.base;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PersonaJuridica extends Persona {
    private String razonSocial;
    private Long cuit;
    public PersonaJuridica(Long idPersona, String email, LocalDate fechaRegistro, List<Domicilio> domicilios, List<Telefono> telefonos) {
        super(idPersona, email, fechaRegistro, domicilios, telefonos);
    }
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonaJuridica that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getRazonSocial(), that.getRazonSocial()) && Objects.equals(getCuit(), that.getCuit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRazonSocial(), getCuit());
    }

    @Override
    public String toString() {
        return "PersonaJuridica{" +
                "razonSocial='" + razonSocial + '\'' +
                ", cuit=" + cuit +
                '}';
    }
}
