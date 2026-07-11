package pe.edu.utp.vacunacioncard.dto.auth;

/**
 * Datos de entrada para registrar una nueva cuenta de usuario.
 *
 * @param username Nombre de usuario único de la cuenta.
 * @param password Contraseña en texto plano (el servicio la codifica con BCrypt).
 */
public record RegisterRequest(String username, String password) {
}
