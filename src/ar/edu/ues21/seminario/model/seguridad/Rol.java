package ar.edu.ues21.seminario.model.seguridad;

import java.util.ArrayList;
import java.util.List;

public class Rol {

    private Long idRol;
    private String nombre;
    List<Permiso> listaPermisos = new ArrayList<>();

    public Rol(){       
    }
    public Rol(String nombre){
        this.nombre = nombre;
    }

    public Rol(Long idRol, String nombre){
        this.idRol = idRol;
        this.nombre = nombre;
        this.listaPermisos = new ArrayList<>();
    }

    public Rol(Long idRol, String nombre, List<Permiso> listaPermisos) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.listaPermisos = listaPermisos;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        Rol other = (Rol) obj;
        if (idRol == null) {
            if (other.idRol != null)
                return false;
        } else if (!idRol.equals(other.idRol))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        return true;
    }
    
}
