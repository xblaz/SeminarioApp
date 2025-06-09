package ar.edu.ues21.seminario.config;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Configuracion {

    public final static String APP_NAME = "SiGeP - Gestión de Prestamos";
    public final static String APP_VERSION = "Versión 0.1";
    public final static DateTimeFormatter DEFAULT_FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public final static Map<String, Map<String,String>> CONFIGURACION = new HashMap<>();

    public final static String USERNAME = "seminario";
    public final static String PASSWORD = "password123";
    public final static String DRIVER = "com.mysql.jdbc.Driver";
    public final static String URL = "jdbc:mysql://172.17.0.2:3306/seminario_db?allowPublicKeyRetrieval=true&useSSL=false";
}
