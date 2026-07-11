package pe.edu.utp.vacunacioncard.dto.auth;

/**
 * Datos de entrada para cerrar una sesión activa.
 *
 * @param token Token de la sesión que se desea cerrar.
 */
public record LogoutRequest(String token) {
}
