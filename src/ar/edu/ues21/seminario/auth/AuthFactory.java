package ar.edu.ues21.seminario.auth;

import ar.edu.ues21.seminario.dao.seguridad.impl.UsuarioDaoMemoriaImpl;
import ar.edu.ues21.seminario.dao.seguridad.impl.UsuarioDaoMySQLImpl;
import ar.edu.ues21.seminario.model.LogicaException;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class AuthFactory {
    private static final Map<AuthType, Supplier<AuthMethod>> AUTH_METHODS = new EnumMap<>(AuthType.class);
    static {
        try {
            AUTH_METHODS.put(AuthType.MYSQL, () -> {
                try {
                    UsuarioDaoMySQLImpl mysqlDao = new UsuarioDaoMySQLImpl();
                    return new MySQLAuth(mysqlDao);
                } catch (LogicaException e) {
                    throw new RuntimeException("Error al crear MySQLAuthStrategy", e);
                }
            });

            AUTH_METHODS.put(AuthType.MEMORIA, () -> {
                UsuarioDaoMemoriaImpl memoriaDao = new UsuarioDaoMemoriaImpl();
                return new MemoriaAuth(memoriaDao);
            });

        } catch ( LogicaException e) {
            throw new ExceptionInInitializerError("Error al inicializar AuthFactory: " + e.getMessage());
        }
    }
    public static AuthMethod createAuth(AuthType type) {
        Supplier<AuthMethod> method = AUTH_METHODS.get(type);
        if (method == null) {
            throw new IllegalArgumentException("Tipo de autenticación no soportado: " + type);
        }
        return method.get();
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
        // Detectar entorno de desarrollo
        String env = System.getProperty("app.environment");
        if (env == null) {
            env = System.getenv("APP_ENVIRONMENT");
        }
        return "develop".equalsIgnoreCase(env);
    }
}
