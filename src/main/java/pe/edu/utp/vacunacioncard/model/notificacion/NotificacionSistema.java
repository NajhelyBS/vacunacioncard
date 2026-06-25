package pe.edu.utp.vacunacioncard.model.notificacion;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

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
        this.tipo = validarTipo(tipo);
    }

    private static String validarTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de notificacion no puede ser nulo o vacio");
        }
        return tipo;
    }
}
