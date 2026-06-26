package pe.edu.utp.vacunacioncard.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
