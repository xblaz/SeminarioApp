package ar.edu.ues21.seminario.repository;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.model.Columna;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GenericMapper {
    /**
     * Interface para factories personalizadas
     */
    @FunctionalInterface
    public interface InstanceFactory<T> {
        T create(ResultSet rs) throws SQLException;
    }

    /**
     * Mapea un ResultSet a una lista de objetos del tipo especificado
     * @param <T> Tipo del objeto a mapear
     * @param rs ResultSet obtenido de la consulta SQL (no nulo)
     * @param clazz Clase del objeto a mapear (no nula)
     * @return Lista de objetos mapeados (nunca nula, pero puede estar vacía)
     * @throws SQLException Si ocurre algún error al acceder a la base de datos o mapear los resultados
     */
    public static <T> List<T> mapperList(ResultSet rs, Class<T> clazz,  InstanceFactory<T> factory) throws RepositoryException {
        if (rs == null) {
            throw new IllegalArgumentException("El ResultSet no puede ser nulo");
        }
        if (clazz == null) {
            throw new IllegalArgumentException("La clase no puede ser nula");
        }
        List<T> results = new ArrayList<>();
        try {
            while (rs.next()) {
                T objeto = mapper(rs, clazz, factory);
                if (objeto != null) {
                    results.add(objeto);
                }
            }
        } catch (SQLException e) {

            throw new RepositoryException("Error al acceder a la base de datos", e);
        } catch (Exception e) {
            throw new RepositoryException("Error inesperado al mapear resultados", e);
        }
        return results;
    }

    /**
     * Mapea un ResultSet a un objeto del tipo especificado
     * @param <T> Tipo del objeto a mapear
     * @param rs ResultSet posicionado en la fila actual
     * @param clazz Clase del objeto
     * @return Objeto mapeado
     * @throws SQLException Si ocurre un error de base de datos o mapeo
     */
    public static <T> T mapper(ResultSet rs, Class<T> clazz, InstanceFactory<T> factory) throws RepositoryException {
        try {
            // Validación de parámetros
            if (rs == null || clazz == null) {
                throw new IllegalArgumentException("ResultSet y Class no pueden ser nulos");
            }
            T instancia = factory != null ? factory.create(rs) : clazz.getDeclaredConstructor().newInstance();
            Field[] campos = clazz.getDeclaredFields();
            for (Field campo : campos) {
                if (campo.isAnnotationPresent(Columna.class)) {
                    mapearCampo(rs, instancia, campo);
                }
            }
            return instancia;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException |
                 SQLException e) {
            throw new RepositoryException("Error al crear instancia de " + clazz.getName(), e);
        }
    }

    /**
     * Mapea por anotación del campo, esto cuando el nombre del atributo en la tabla no es diferente al de la clase
     * @param rs
     * @param instancia
     * @param campo
     * @param <T>
     * @throws SQLException
     */
    private static <T> void mapearCampo(ResultSet rs, T instancia, Field campo) throws SQLException {
        Columna anotacion = null;
        try {
            anotacion = campo.getAnnotation(Columna.class);
            String nombreColumna = anotacion.nombre();
            campo.setAccessible(true);

            Object valor = obtenerValorCampo(rs, campo, nombreColumna, anotacion);



            if (valor != null || !anotacion.requerido()) {
                campo.set(instancia, valor);
            } else if (anotacion.requerido()) {
                throw new SQLException("El campo requerido '" + nombreColumna + "' es nulo");
            }
        } catch (IllegalAccessException e) {
            throw new SQLException("Error al establecer valor en campo " + campo.getName(), e);
        } catch (SQLException e) {
            if (!anotacion.ignorarFaltaColumna()) {
                throw e;
            }
            // Si la columna no existe y está marcada para ignorar, continuamos
        }
    }

    private static Object obtenerValorCampo(ResultSet rs, Field campo, String nombreColumna, Columna anotacion)
            throws SQLException {
        try {
            Class<?> tipo = campo.getType();
            Object valor = rs.getObject(nombreColumna);

            if (valor == null) {
                return null;
            }
            // Conversiones de tipos especiales
            if (tipo == LocalDate.class) {
                return convertirADate(valor, anotacion.formatoFecha());
            } else if (tipo == LocalDateTime.class) {
                return convertirADateTime(valor, anotacion.formatoFecha());
            } else if (tipo.isEnum()) {
                return convertirAEnum(tipo, valor);
            }

            return convertirTipoBasico(tipo, valor);

        } catch (SQLException e) {
            if (anotacion.ignorarFaltaColumna()) {
                return null;
            }
            throw e;
        }
    }

    private static Object convertirTipoBasico(Class<?> tipo, Object valor) {
        // Conversión para tipos numéricos
        if (tipo == Integer.class || tipo == int.class) {
            return ((Number) valor).intValue();
        } else if (tipo == Long.class || tipo == long.class) {
            return ((Number) valor).longValue();
        } else if (tipo == Double.class || tipo == double.class) {
            return ((Number) valor).doubleValue();
        } else if (tipo == Float.class || tipo == float.class) {
            return ((Number) valor).floatValue();
        } else if (tipo == Boolean.class || tipo == boolean.class) {
            return convertirABoolean(valor);
        }
        return valor;
    }

    private static Boolean convertirABoolean(Object valor) {
        if (valor instanceof Boolean) {
            return (Boolean) valor;
        } else if (valor instanceof Number) {
            return ((Number) valor).intValue() != 0;
        } else if (valor instanceof String) {
            return "1".equals(valor) || "true".equalsIgnoreCase((String) valor);
        }
        return false;
    }

    private static LocalDate convertirADate(Object valor, String formato) throws SQLException {
        try {
            if (valor instanceof Date) {
                return ((Date) valor).toLocalDate();
            } else if (valor instanceof String) {
                return LocalDate.parse((String) valor, DateTimeFormatter.ofPattern(formato));
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("Error al convertir fecha: " + valor, e);
        }
    }

    private static LocalDateTime convertirADateTime(Object valor, String formato) throws SQLException {
        try {
            if (valor instanceof Timestamp) {
                return ((Timestamp) valor).toLocalDateTime();
            } else if (valor instanceof String) {
                return LocalDateTime.parse((String) valor, DateTimeFormatter.ofPattern(formato));
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("Error al convertir fecha/hora: " + valor, e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends Enum<E>> E convertirAEnum(Class<?> tipo, Object valor) {
        if (valor instanceof String) {
            return Enum.valueOf((Class<E>) tipo, (String) valor);
        } else if (valor instanceof Number) {
            E[] valores = (E[]) tipo.getEnumConstants();
            return valores[((Number) valor).intValue()];
        }
        return null;
    }
}
