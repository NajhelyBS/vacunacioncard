package pe.edu.utp.vacunacioncard.model.notificacion;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

/**
 * Clase NotificacionRecordatorio que representa una notificacion de recordatorio de vacunacion.
 *
 * @author Grupo 1
 * @version 1.0
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

    public NotificacionRecordatorio(Usuario destinatario, RegistroVacuna registroVacuna, LocalDateTime fechaRecordatorio) {
        super(destinatario, validarMensaje(registroVacuna, fechaRecordatorio));
        this.registroVacuna = registroVacuna;
        this.fechaRecordatorio = fechaRecordatorio;
    }

    private static String validarMensaje(RegistroVacuna rv, LocalDateTime fr) {
        if (rv == null || fr == null) {
            throw new IllegalArgumentException("Registro de vacuna y fecha no pueden ser nulos");
        }
        return "Recordatorio de proxima dosis";
    }
}
