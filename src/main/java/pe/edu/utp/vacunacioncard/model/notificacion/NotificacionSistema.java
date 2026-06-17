package pe.edu.utp.vacunacioncard.model.notificacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Clase NotificacionSistema que representa una notificación generada por el sistema.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionSistema extends Notificacion{
    private String tipo;
    private boolean leida = false;

    public NotificacionSistema(Usuario destinatario, String mensaje, String tipo) {
        super(destinatario, mensaje);
        this.tipo = tipo;
    }
}
