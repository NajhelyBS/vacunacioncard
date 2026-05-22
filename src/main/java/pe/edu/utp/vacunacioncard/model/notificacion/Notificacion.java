package pe.edu.utp.vacunacioncard.model.notificacion;

import java.time.LocalDateTime;

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
    private String id;
    private String mensaje;
    private Usuario destinatario;
    private LocalDateTime fechaEnvio;
    private String estado;

    // Constructor base para inicializar la lógica común
    public Notificacion(Usuario destinatario, String mensaje) {
        this.id = java.util.UUID.randomUUID().toString();
        this.destinatario = destinatario;
        this.mensaje = mensaje;
        this.estado = "PENDIENTE";
    }
}
