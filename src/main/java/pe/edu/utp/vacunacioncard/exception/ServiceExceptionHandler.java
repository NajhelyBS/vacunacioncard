package pe.edu.utp.vacunacioncard.exception;

import org.springframework.dao.DataAccessException;
import java.util.function.Supplier;

/**
 * Utilidad para centralizar el manejo de excepciones de acceso a datos
 * en la capa de servicios, evitando la repetición del bloque try/catch
 * en cada ServiceImpl.
 */
public final class ServiceExceptionHandler {

    private ServiceExceptionHandler() {
        // Utility class, no instanciable
    }

    /**
     * Ejecuta una operación que retorna un valor, envolviendo cualquier
     * {@link DataAccessException} en una {@link ServiceException}.
     *
     * @param operation    operación a ejecutar.
     * @param errorMessage mensaje descriptivo del error.
     * @return el resultado de la operación.
     */
    public static <T> T execute(Supplier<T> operation, String errorMessage) {
        try {
            return operation.get();
        } catch (DataAccessException e) {
            throw new ServiceException(errorMessage, e);
        }
    }

    /**
     * Ejecuta una operación sin retorno (void), envolviendo cualquier
     * {@link DataAccessException} en una {@link ServiceException}.
     */
    public static void executeVoid(Runnable operation, String errorMessage) {
        try {
            operation.run();
        } catch (DataAccessException e) {
            throw new ServiceException(errorMessage, e);
        }
    }
}