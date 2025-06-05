package ar.edu.ues21.seminario.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateQueryLoader {
    private static final Map<String, Map<String, String>> cache = new HashMap<>();
    private static final String BASE_PATH = "sql_templates/";

    public static String get(String archivo, String clave) {
        archivo = archivo.toLowerCase();

        if (!cache.containsKey(archivo)) {
            cache.put(archivo, cargarArchivo(archivo));
        }

        Map<String, String> queries = cache.get(archivo);
        String query = queries.get(clave);

        if (query == null) {
            throw new RuntimeException("No se encontró la query con clave '" + clave + "' en el archivo '" + archivo + "'");
        }
        return query;
    }

    private static Map<String, String> cargarArchivo(String archivo) {
        Map<String, String> queries = new HashMap<>();

        try (InputStream is = TemplateQueryLoader.class.getClassLoader().getResourceAsStream(BASE_PATH + archivo + ".sql")) {
            if (is == null) {
                throw new RuntimeException("No se encontró el archivo: " + archivo + ".sql");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder currentQuery = new StringBuilder();
            String currentKey = null;

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.strip();
                if (line.startsWith("--")) {
                    if (currentKey != null && currentQuery.length() > 0) {
                        queries.put(currentKey, currentQuery.toString().trim());
                        currentQuery.setLength(0);
                    }
                    currentKey = line.replace("--", "").strip();
                } else if (!line.isEmpty() && currentKey != null) {
                    currentQuery.append(line).append("\n");
                }
            }

            // última query
            if (currentKey != null && currentQuery.length() > 0) {
                queries.put(currentKey, currentQuery.toString().trim());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al cargar queries del archivo " + archivo, e);
        }

        return queries;
    }
}
