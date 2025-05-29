package ar.edu.ues21.seminario.model.seguridad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {

    private Long id;
    private String nombre;
    private Date fechaAlta;
    private Date fechaBaja;
    private String clave;
    private Boolean estado;
    private List<Rol> listaRoles = new ArrayList<>();

    public Usuario(){
    }

    public Usuario(Long id, String nombre, String clave){
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
    }

    public Usuario(Long id, String nombre, Date fechaAlta, Date fechaBaja, String clave, Boolean estado) {
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
    public Date getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public Date getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public Boolean getEstado() {
        return estado;
    }
    public void setEstado(Boolean estado) {
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
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        return true;
    }
}

