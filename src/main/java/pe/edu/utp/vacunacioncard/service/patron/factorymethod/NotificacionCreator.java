package pe.edu.utp.vacunacioncard.service.patron.factorymethod;

import java.time.LocalDateTime;
import java.time.ZoneId;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Creator abstracto del patrón Factory Method.
 * Define
 */
public abstract class NotificacionCreator {
    private static final String ZONE_LIMA = "America/Lima";

    protected abstract Notificacion crearNotificacion(Usuario destinatario, String mensaje);

    protected abstract String estadoInicial();

    /**
     * Genera una notificación para un destinatario específico con un mensaje dado.
     * @param destinatario
     * @param mensaje
     */
    public final Notificacion generarNotificacion(Usuario destinatario, String mensaje) {
        if (destinatario == null) {
            throw new IllegalArgumentException("No se puede generar una notificación sin un destinatario válido.");
        }

        Notificacion notificacion = crearNotificacion(destinatario, mensaje);
        notificacion.setFechaEnvio(LocalDateTime.now(ZoneId.of(ZONE_LIMA)));
        notificacion.setEstado(estadoInicial());
        return notificacion;
    }
}