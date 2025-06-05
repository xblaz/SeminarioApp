package ar.edu.ues21.seminario.exception;

public class ServiceException extends Exception {
    public ServiceException(String mensaje) {
        super(mensaje);
    }
    public ServiceException(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }
}
