package pe.edu.utp.vacunacioncard.service.patron.creacional.factorymethod;

import java.time.LocalDateTime;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionSistema;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionRecordatorio;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

/**
 * Fábrica centralizada para la creación de notificaciones en el sistema.
 */
public class NotificacionFactory {

    private NotificacionFactory() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Crea notificaciones de sistema basadas en un tipo específico.
     */
    public static Notificacion crearNotificacion(String tipoNotificacion, Usuario destinatario, String mensaje) {
        if (tipoNotificacion == null) {
            throw new IllegalArgumentException("El tipo de notificación no puede ser nulo");
        }
        
        return switch (tipoNotificacion.toUpperCase()) {
            case "SISTEMA" -> new NotificacionSistema(destinatario, mensaje, "SISTEMA");
            case "ALERTA" -> new NotificacionSistema(destinatario, mensaje, "ALERTA");
            case "INFORMACION" -> new NotificacionSistema(destinatario, mensaje, "INFORMACION");
            default -> throw new IllegalArgumentException("Tipo de notificacion no soportado: " + tipoNotificacion);
        };
    }

    /**
     * Crea una notificación de tipo recordatorio para el cronograma de vacunas.
     */
    public static Notificacion crearRecordatorio(Usuario destinatario, RegistroVacuna registroVacuna, LocalDateTime fechaRecordatorio) {
        return new NotificacionRecordatorio(destinatario, registroVacuna, fechaRecordatorio);
    }
}
