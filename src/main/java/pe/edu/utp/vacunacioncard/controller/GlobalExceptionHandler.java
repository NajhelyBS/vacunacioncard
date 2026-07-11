package pe.edu.utp.vacunacioncard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.edu.utp.vacunacioncard.exception.ServiceException;

import java.util.Map;

/**
 * Manejador global de excepciones para los controladores REST.
 * Traduce las excepciones de negocio en respuestas HTTP con un cuerpo de error legible,
 * evitando que se propaguen como errores 500 genéricos.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Traduce una {@link ServiceException} en una respuesta HTTP 400 (Bad Request).
     *
     * @param ex Excepción de negocio capturada.
     * @return Respuesta con el mensaje de error.
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Map<String, String>> handleServiceException(ServiceException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", mensaje(ex)));
    }

    /**
     * Traduce una {@link IllegalArgumentException} (validaciones de dominio) en una respuesta HTTP 400.
     *
     * @param ex Excepción de validación capturada.
     * @return Respuesta con el mensaje de error.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", mensaje(ex)));
    }

    private String mensaje(Throwable ex) {
        return ex.getMessage() != null ? ex.getMessage() : "Solicitud inválida";
    }
}
