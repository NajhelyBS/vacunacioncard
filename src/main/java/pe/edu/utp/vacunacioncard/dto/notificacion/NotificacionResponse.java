package pe.edu.utp.vacunacioncard.dto.notificacion;

import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;

import java.time.LocalDateTime;

/**
 * Vista pública de una notificación.
 *
 * @param id             Identificador de la notificación.
 * @param mensaje        Contenido de la notificación.
 * @param estado         Estado actual (por ejemplo, "ENVIADA", "PENDIENTE", "LEIDA").
 * @param fechaEnvio     Fecha y hora de envío.
 * @param destinatarioId Identificador del usuario destinatario.
 */
public record NotificacionResponse(
        Long id,
        String mensaje,
        String estado,
        LocalDateTime fechaEnvio,
        Long destinatarioId) {

    /**
     * Construye una {@link NotificacionResponse} a partir de la entidad {@link Notificacion}.
     *
     * @param n Entidad origen.
     * @return La representación pública de la notificación.
     */
    public static NotificacionResponse from(Notificacion n) {
        return new NotificacionResponse(
                n.getId(),
                n.getMensaje(),
                n.getEstado(),
                n.getFechaEnvio(),
                n.getDestinatario() != null ? n.getDestinatario().getId() : null);
    }
}
