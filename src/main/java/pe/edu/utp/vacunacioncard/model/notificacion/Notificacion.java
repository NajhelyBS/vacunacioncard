
package pe.edu.utp.vacunacioncard.model.notificacion;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Clase Notificacion que representa una notificacion enviada a un usuario.
 * Es la clase base de los diferentes tipos de notificaciones.
 */

@Entity
@Table(name = "mae_notificacion")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mensaje")
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Usuario destinatario;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "estado")
    private String estado = "PENDIENTE";

    protected Notificacion(Usuario destinatario, String mensaje) {
        this.destinatario = destinatario;
        this.mensaje = mensaje;
    }
}