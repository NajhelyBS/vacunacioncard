package pe.edu.utp.vacunacioncard.model.notificacion;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Clase NotificacionSistema que representa una notificación generada por el sistema.
 * No es una entidad persistente: se gestiona en memoria o mediante un sistema
 * de mensajería/eventos (ej. WebSocket, cola de mensajes).
 *
 * @author Grupo 1
 * @version 1.0
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificacionSistema {

    private String id = UUID.randomUUID().toString();
    private String mensaje;
    private Usuario destinatario;
    private LocalDateTime fechaEnvio = LocalDateTime.now(ZoneId.of("America/Lima"));
    private String estado = "PENDIENTE";
    private String tipo;
    private boolean leida = false;

    public NotificacionSistema(Usuario destinatario, String mensaje, String tipo) {
        if (destinatario == null || mensaje == null) {
            throw new IllegalArgumentException("Destinatario y mensaje no pueden ser nulos");
        }
        this.destinatario = destinatario;
        this.mensaje = mensaje;
        this.tipo = tipo;
    }
}