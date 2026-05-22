package pe.edu.utp.vacunacioncard.model.notificacion;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

/**
 * Clase NotificacionRecordatorio que representa una notificación de recordatorio de vacunación.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class NotificacionRecordatorio extends Notificacion {
    private RegistroVacuna registroVacuna;
    private LocalDateTime fechaRecordatorio;

    public NotificacionRecordatorio(Usuario destinatario, RegistroVacuna registroVacuna, LocalDateTime fechaRecordatorio) {
        super(destinatario, "Recordatorio de próxima dosis");
        this.registroVacuna = registroVacuna;
        this.fechaRecordatorio = fechaRecordatorio;
    }
}
