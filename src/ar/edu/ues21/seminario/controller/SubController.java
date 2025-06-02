package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.model.seguridad.Usuario;

public interface SubController {
    void setPrincipalController(PrincipalController principalController);
    void setUsuario(Usuario usuario);
}
