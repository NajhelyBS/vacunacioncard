package pe.edu.utp.vacunacioncard.dto.auth;

/**
 * Credenciales de entrada para el inicio de sesión.
 *
 * @param username Nombre de usuario de la cuenta.
 * @param password Contraseña en texto plano a verificar.
 */
public record LoginRequest(String username, String password) {
}
