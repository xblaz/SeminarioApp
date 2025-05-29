package ar.edu.ues21.seminario.auth;

public class AuthFactory {
    public static AuthMethod createAuth(AuthType type) {
        switch (type) {
            case MYSQL:
                return new MySQLAuth();
            case MEMORIA:
                return new MemoriaAuth();
            default:
                throw new IllegalArgumentException("Tipo de autenticación no soportado");
        }
    }
}
