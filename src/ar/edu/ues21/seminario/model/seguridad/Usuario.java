package ar.edu.ues21.seminario.model.seguridad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {
    private Long id;
    private String nombre;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja;
    private String clave;
    private EstadoUsuario estado;
    private List<Rol> listaRoles = new ArrayList<>();
    private String roles;

    public Usuario() {
        this.estado = EstadoUsuario.NO_ACTIVO;
    }

    public Usuario(Long id, String nombre, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
        this.estado = EstadoUsuario.NO_ACTIVO;
    }
    public Usuario(String nombre, String clave, EstadoUsuario estado, List<Rol> listaRoles) {
        this.clave = clave;
        this.nombre = nombre;
        this.estado = estado;
        this.listaRoles = listaRoles;
    }
    public Usuario(Long id, String nombre, LocalDate fechaAlta, LocalDate fechaBaja, String clave, EstadoUsuario estado) {
        this.id = id;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.clave = clave;
        this.nombre = nombre;
        this.estado = estado;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public LocalDate getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public EstadoUsuario getEstado() {
        return estado;
    }
    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public List<Rol> getListaRoles() {
        return listaRoles;
    }
    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }
    public Boolean tienePermiso(String pPermiso) {
        for (Rol r : this.listaRoles) {
            for (Permiso p : r.getListaPermisos()) {
                if (p.getCodigo().equals(pPermiso))
                    return true;
            }
        }
        return false;
    }

    public String getRoles() {
        roles = "Sin Roles";
        if (this.listaRoles != null && !this.listaRoles.isEmpty()) {
            roles = this.listaRoles.stream()
                    .map(Rol::getDescripcion)
                    .collect(Collectors.joining(", ", "", ""));
        }
        return roles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nombre == null) {
            return other.nombre == null;
        } else return nombre.equals(other.nombre);
    }
}

