package ar.edu.ues21.seminario.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class ResourceQueryLoader {

    protected Map<String, String> sqltemplates;
    public static String loadSQL(String fileName) {
        try (InputStream inputStream = ResourceQueryLoader.class.getResourceAsStream("/sql_templates/" + fileName);
             BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResourceFileAsString(String fileName) throws IOException {
        return loadSQL(fileName);
    }

    public void pushQueryTemplate(String tipoQuery, String template) throws IOException {
        sqltemplates.put(tipoQuery, getResourceFileAsString(template));
    }
}
