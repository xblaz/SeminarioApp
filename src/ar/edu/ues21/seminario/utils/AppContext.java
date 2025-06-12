package ar.edu.ues21.seminario.utils;

import ar.edu.ues21.seminario.repository.aplicacion.EsquemaFinanciacionRepository;
import ar.edu.ues21.seminario.repository.seguridad.RolRepository;
import ar.edu.ues21.seminario.repository.seguridad.UsuarioRepository;
import ar.edu.ues21.seminario.service.ConfiguracionService;
import ar.edu.ues21.seminario.service.UsuarioService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class AppContext {

    private static Connection conn;
    private static UsuarioService usuarioService;
    private static ConfiguracionService configuracionService;

    public static void init() {
        DatabaseConexion conexion = new DatabaseConexion();
        conn = conexion.getConexion();
        // Inicializa repositorios
        UsuarioRepository usuarioRepo = new UsuarioRepository(conn);
        RolRepository rolRepo = new RolRepository(conn);
        EsquemaFinanciacionRepository repoEsquemaFinanciamiento = new EsquemaFinanciacionRepository(conn);

        // Inicializa services, inyecta su repositorio por parámetros
        usuarioService = new UsuarioService(usuarioRepo, rolRepo);
        configuracionService = new ConfiguracionService(repoEsquemaFinanciamiento);
    }
    public static Connection getConexion() {
        return conn;
    }
    public static UsuarioService getUsuarioService() {
        return usuarioService;
    }
    public static ConfiguracionService getConfiguracionService() { return configuracionService; }

    public static String getEnvironment() {
        // Detectar entorno de desarrollo
        String env = System.getProperty("app.environment");
        if (env == null) {
            env = System.getenv("APP_ENVIRONMENT");
        }
        return env;
    }

    public static boolean isDevelopmentEnvironment() {
        return "develop".equalsIgnoreCase(AppContext.getEnvironment());
    }

    private static Map<String, String> cargarPropertiesPorEnviroment(String env) {
        Map<String, String> properties = new HashMap<>();
        // Busca el archivo en el directorio resources del proyecto
        try (InputStream is = AppContext.class.getClassLoader().getResourceAsStream(env + ".properties")) {
            if (is == null) {
                throw new RuntimeException("No se encontró el archivo properties para : " + env + ".properties");
            }
           BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            //database.username=seminario
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().strip();
                if (line != null && line.isEmpty()) {
                    String[] parametro = line.split("=");
                    if (!properties.containsKey(parametro[0])) {
                        properties.put(parametro[0], parametro[1]);
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al cargar properties para entorno " + env, e);
        }

        return properties;
    }
}
