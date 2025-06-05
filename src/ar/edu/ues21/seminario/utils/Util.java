package ar.edu.ues21.seminario.utils;

import ar.edu.ues21.seminario.exception.LogicaException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Util {
    private static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final RoundingMode redondeoModo = RoundingMode.HALF_UP;
    public static final int cantidadDecimales = 2;
    /**
     * Redondea un BigDecimal al número de decimales especificado.
     */
    public static BigDecimal redondear(BigDecimal valor, int decimales) {
        return Optional.ofNullable(valor)
                .map(v -> v.setScale(decimales, redondeoModo))
                .orElse(null);
    }

    public static BigDecimal redondear(BigDecimal valor) {
        return Optional.ofNullable(valor)
                .map(v -> v.setScale(cantidadDecimales, redondeoModo))
                .orElse(null);
    }

    /**
     * Convierte un LocalDate a String usando el formato por defecto (dd/MM/yyyy).
     */
    public static String formatearFecha(LocalDate fecha) {
        return Optional.ofNullable(fecha)
                .map(d -> d.format(DEFAULT_DATE_FORMAT))
                .orElse(null);
    }

    /**
     * Convierte un String a LocalDate usando el formato por defecto (dd/MM/yyyy).
     */
    public static LocalDate formatearFecha(String fechaString) {
        return Optional.ofNullable(fechaString)
                .map(d -> LocalDate.parse(d, DEFAULT_DATE_FORMAT))
                .orElse(null);
    }

    /**
     * Convierte un LocalDate a String con un patrón personalizado.
     */
    public static String formatearFecha(LocalDate fechaString, String patron) {
        return Optional.ofNullable(fechaString)
                .map(d -> d.format(DateTimeFormatter.ofPattern(patron)))
                .orElse(null);
    }

    public static String hashSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new LogicaException(String.format("Error al calcular hash SHA-256: %s", e.getMessage()));
        }
    }

    private boolean verificarHash(String claveIngresada, String hashAlmacenado) {
        String hashCalculado = hashSHA256(claveIngresada);
        return hashAlmacenado.equals(hashCalculado);
    }

}
