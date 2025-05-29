package ar.edu.ues21.seminario.auth;

import ar.edu.ues21.seminario.dao.seguridad.impl.UsuarioDaoMemoriaImpl;
import ar.edu.ues21.seminario.dao.seguridad.impl.UsuarioDaoMySQLImpl;
import ar.edu.ues21.seminario.model.LogicaException;

import java.util.EnumMap;
import java.util.Map;

public class AuthFactory {
    private static final Map<AuthType, AuthMethod> AUTH_METHODS = new EnumMap<>(AuthType.class);
    static {
        try {
            UsuarioDaoMySQLImpl mysqlDao = new UsuarioDaoMySQLImpl();
            UsuarioDaoMemoriaImpl memoriaDao = new UsuarioDaoMemoriaImpl();

            AUTH_METHODS.put(AuthType.MYSQL, new MySQLAuth(mysqlDao));
            AUTH_METHODS.put(AuthType.MEMORIA, new MemoriaAuth(memoriaDao));

        } catch ( LogicaException e) {
            throw new ExceptionInInitializerError("Error al inicializar AuthFactory: " + e.getMessage());
        }
    }
    public static AuthMethod createAuth(AuthType type) {
        AuthMethod method = AUTH_METHODS.get(type);
        if (method == null) {
            throw new IllegalArgumentException("Tipo de autenticación no soportado: " + type);
        }
        return method;
    }

    // Versión alternativa que detecta automáticamente el tipo en desarrollo/producción
    public static AuthMethod createAutoConfigAuth() {
        if (isDevelopmentEnvironment()) {
            return createAuth(AuthType.MEMORIA);
        } else {
            return createAuth(AuthType.MYSQL);
        }
    }

    private static boolean isDevelopmentEnvironment() {
        // Lógica para detectar entorno de desarrollo
        return System.getProperty("app.environment", "produccion").equals("desarrollo");
    }
}
