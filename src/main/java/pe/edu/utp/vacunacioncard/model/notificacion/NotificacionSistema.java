package pe.edu.utp.vacunacioncard.model.notificacion;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Clase NotificacionSistema que representa una alerta o mensaje generado por el sistema.
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

    /**
     * Crea una notificación de sistema para un destinatario.
     *
     * @param destinatario Usuario que recibirá la notificación.
     * @param mensaje      Contenido textual de la notificación.
     * @param tipo         Tipo de notificación de sistema; no puede ser nulo ni vacío.
     */
    public NotificacionSistema(Usuario destinatario, String mensaje, String tipo) {
        super(destinatario, mensaje);
        this.tipo = validateType(tipo);
    }

    /**
     * Valida que el tipo de notificación no sea nulo ni vacío.
     *
     * @param tipo Tipo de notificación a validar.
     * @return El tipo validado.
     * @throws IllegalArgumentException Si el tipo es nulo o está en blanco.
     */
    private static String validateType(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de notificacion no puede ser nulo o vacio");
        }
        return tipo;
    }
}
