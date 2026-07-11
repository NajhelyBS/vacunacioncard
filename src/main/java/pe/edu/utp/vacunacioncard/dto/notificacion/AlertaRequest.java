package pe.edu.utp.vacunacioncard.dto.notificacion;

/**
 * Datos de entrada para registrar una alerta de sistema dirigida a un usuario.
 *
 * @param usuarioId Identificador del usuario destinatario.
 * @param mensaje   Contenido textual de la alerta.
 */
public record AlertaRequest(Long usuarioId, String mensaje) {
}
