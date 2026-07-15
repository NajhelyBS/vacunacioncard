package pe.edu.utp.vacunacioncard.exception;

/**
 * Excepción de negocio de la capa de servicios.
 * <p>
 * Envuelve los fallos de acceso a datos y las violaciones de reglas de negocio en un único
 * tipo no comprobado (unchecked), evitando que las excepciones propias de la persistencia
 * se filtren hacia las capas superiores. El {@code GlobalExceptionHandler} la traduce a una
 * respuesta HTTP 400 con un cuerpo de error legible.
 */
public class ServiceException extends RuntimeException {

    /**
     * Crea una excepción de servicio.
     *
     * @param mensaje Descripción del error orientada al usuario o al consumidor de la API.
     * @param causa   Excepción original que provocó el fallo, o {@code null} si el error
     *                proviene de una regla de negocio y no de una causa subyacente.
     */
    public ServiceException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
