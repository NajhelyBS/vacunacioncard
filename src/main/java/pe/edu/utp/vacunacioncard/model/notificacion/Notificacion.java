package pe.edu.utp.vacunacioncard.model.notificacion;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Clase Notificacion que representa una notificación enviada a un usuario.
 * Es la clase base de los diferentes tipos de notificaciones.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public abstract class Notificacion {
   // Atributos comunes heredados por las subclases
    private final String id = UUID.randomUUID().toString();
    private String mensaje;
    private Usuario destinatario;
    private final LocalDateTime fechaEnvio = LocalDateTime.now(ZoneId.of("America/Lima"));
    private String estado = "PENDIENTE"; 

    // Constructor base para inicializar la lógica común
    protected Notificacion(Usuario destinatario, String mensaje) {
        this.destinatario = destinatario;
        this.mensaje = mensaje;
    }
}
