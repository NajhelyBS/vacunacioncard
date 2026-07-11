package pe.edu.utp.vacunacioncard.service.patron.creacional.factorymethod;

import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionSistema;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Fábrica concreta encargada de la creación de Notificaciones de Sistema y Alertas.
 */
public class SistemaNotificacionFactory implements INotificacionFactory {
    
    private final String tipo;

    /**
     * Constructor de la fábrica. Reutiliza las validaciones originales.
     */
    public String validateType(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de notificación no puede ser nulo o vacío");
        }
        return tipo.toUpperCase();
    }

    public SistemaNotificacionFactory(String tipo) {
        this.tipo = validateType(tipo);
    }

    /**
     * Crea una notificación de sistema o alerta según el tipo especificado.
     * @param destinatario
     * @param mensaje
     * @return
     */
    @Override
    public Notificacion createNotification(Usuario destinatario, String mensaje) {
        return switch (this.tipo) {
            case "SISTEMA", "ALERTA", "INFORMACION" -> new NotificacionSistema(destinatario, mensaje, this.tipo);
            default -> throw new IllegalArgumentException("Tipo de notificación de sistema no soportado: " + this.tipo);
        };
    }
}
