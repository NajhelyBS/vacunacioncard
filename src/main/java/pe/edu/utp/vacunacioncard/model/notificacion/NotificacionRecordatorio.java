package pe.edu.utp.vacunacioncard.model.notificacion;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

/**
 * Clase NotificacionRecordatorio que representa una notificacion de recordatorio de vacunacion.
 */

@Entity
@Table(name = "mae_notificacion_recordatorio")
@Getter
@Setter
@NoArgsConstructor
public class NotificacionRecordatorio extends Notificacion {

    @ManyToOne
    @JoinColumn(name = "registro_vacuna_id")
    private RegistroVacuna registroVacuna;

    @Column(name = "fecha_recordatorio")
    private LocalDateTime fechaRecordatorio;

    /**
     * Crea un recordatorio de vacunación para un destinatario. El mensaje se genera automáticamente.
     *
     * @param destinatario     Usuario que debe acudir a la vacunación.
     * @param registroVacuna   Registro de vacuna asociado al recordatorio.
     * @param fechaRecordatorio Fecha y hora en que se debe gatillar el recordatorio.
     */
    public NotificacionRecordatorio(Usuario destinatario, RegistroVacuna registroVacuna, LocalDateTime fechaRecordatorio) {
        super(destinatario, buildMessage(registroVacuna, fechaRecordatorio));
        this.registroVacuna = registroVacuna;
        this.fechaRecordatorio = fechaRecordatorio;
    }

    /**
     * Valida las dependencias del recordatorio y construye su mensaje por defecto.
     *
     * @param rv Registro de vacuna asociado; no puede ser nulo.
     * @param fr Fecha del recordatorio; no puede ser nula.
     * @return El mensaje generado para el recordatorio.
     * @throws IllegalArgumentException Si el registro de vacuna o la fecha son nulos.
     */
    private static String buildMessage(RegistroVacuna rv, LocalDateTime fr) {
        if (rv == null || fr == null) {
            throw new IllegalArgumentException("Registro de vacuna y fecha no pueden ser nulos");
        }
        return "Recordatorio de proxima dosis";
    }
}
