package ar.edu.ues21.seminario.model.seguridad;

import java.util.ArrayList;
import java.util.List;

public class Rol {
    private Long idRol;
    private String descripcion;
    private List<Permiso> listaPermisos = new ArrayList<>();
    public Rol() {}
    public Rol(Long idRol, String descripcion) {
        this.idRol = idRol;
        this.descripcion = descripcion;
        this.listaPermisos = new ArrayList<>();
    }
    public Long getIdRol() {
        return idRol;
    }
    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public List<Permiso> getListaPermisos() {
        return listaPermisos;
    }
    public void setListaPermisos(List<Permiso> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idRol == null) ? 0 : idRol.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
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
        Rol other = (Rol) obj;
        if (idRol == null) {
            if (other.idRol != null)
                return false;
        } else if (!idRol.equals(other.idRol))
            return false;
        if (descripcion == null) {
            return other.descripcion == null;
        } else return descripcion.equals(other.descripcion);
    }

}
