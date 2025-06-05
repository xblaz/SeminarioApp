package ar.edu.ues21.seminario.auth;

import ar.edu.ues21.seminario.exception.AuthException;
import ar.edu.ues21.seminario.model.seguridad.Usuario;

public interface AuthMethod {
    Usuario autenticar(String pUsuario, String pClave) throws AuthException;
}
