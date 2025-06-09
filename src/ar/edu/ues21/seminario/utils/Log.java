package ar.edu.ues21.seminario.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    static final String LOG_DATE_FORMAT = "YYYY-mm-dd - HH:mm:ss";
    public static void info(String mensaje) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOG_DATE_FORMAT);
        String tiempo = LocalDateTime.now().format(formatter);
        System.out.println(String.format("[%s][%s] %s", tiempo, "info", mensaje));
    }
    public static void error(String mensaje) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOG_DATE_FORMAT);
        String tiempo = LocalDateTime.now().format(formatter);
        System.out.println(String.format("[%s][%s] %s",  tiempo,"error", mensaje));
    }
}
