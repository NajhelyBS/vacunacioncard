package pe.edu.utp.vacunacioncard.dto.auth;

/**
 * Respuesta devuelta tras un inicio de sesión exitoso.
 *
 * @param token    Token de la sesión creada, usado para autenticar peticiones posteriores.
 * @param cuentaId Identificador de la cuenta autenticada.
 * @param username Nombre de usuario autenticado.
 */
public record LoginResponse(String token, Long cuentaId, String username) {
}
