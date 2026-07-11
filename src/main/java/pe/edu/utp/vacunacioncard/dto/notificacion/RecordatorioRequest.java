package pe.edu.utp.vacunacioncard.dto.notificacion;

import java.time.LocalDateTime;

/**
 * Datos de entrada para registrar un recordatorio de próxima dosis.
 *
 * @param usuarioId        Identificador del usuario destinatario.
 * @param registroVacunaId Identificador del registro de vacuna asociado.
 * @param fecha            Fecha y hora en que se debe gatillar el recordatorio.
 */
public record RecordatorioRequest(Long usuarioId, Long registroVacunaId, LocalDateTime fecha) {
}
