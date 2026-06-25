package pe.edu.utp.vacunacioncard.model.notificacion;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Clase NotificacionSistema que representa una notificacion generada por el sistema.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Entity
@Table(name = "mae_notificacion_sistema")
@Getter
@Setter
@NoArgsConstructor
public class NotificacionSistema extends Notificacion {

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "leida")
    private boolean leida = false;

    public NotificacionSistema(Usuario destinatario, String mensaje, String tipo) {
        super(destinatario, mensaje);
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de notificacion no puede ser nulo o vacio");
        }
        this.tipo = tipo;
    }
}
