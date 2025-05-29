package ar.edu.ues21.seminario.service;

import ar.edu.ues21.seminario.auth.AuthException;
import ar.edu.ues21.seminario.auth.AuthFactory;
import ar.edu.ues21.seminario.auth.AuthMethod;
import ar.edu.ues21.seminario.auth.AuthType;
import ar.edu.ues21.seminario.model.seguridad.Usuario;

public class AuthService {

    private AuthMethod authMethod;

    public AuthService(AuthType authType) {
        this.authMethod = AuthFactory.createAuth(authType);
    }

    public Usuario login(String username, String password) throws AuthException {
        validarCredenciales(username, password);
        return authMethod.autenticar(username, password);
    }

    private void validarCredenciales(String username, String password) throws AuthException {
        if (username == null || username.trim().isEmpty()) {
            throw new AuthException("Username no puede estar vacío");
        }

        if (password == null || password.isEmpty()) {
            throw new AuthException("Password no puede estar vacío");
        }
    }

    // Permite cambiar el método en tiempo de ejecución
    public void setAuthMethod(AuthType authType) {
        this.authMethod = AuthFactory.createAuth(authType);
    }

}
