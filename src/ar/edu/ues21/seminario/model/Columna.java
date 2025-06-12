package ar.edu.ues21.seminario.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Columna {
    String nombre();
    boolean requerido() default false;
    boolean ignorarFaltaColumna() default false;
    String formatoFecha() default "dd/MM/yyyy";
}