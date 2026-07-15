package pe.edu.utp.vacunacioncard.service.patron.factorymethod;

import java.time.LocalDateTime;
import java.time.ZoneId;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Creator abstracto del patrón <b>Factory Method</b>.
 * <p>
 * Define el método fábrica {@link #crearNotificacion(Usuario, String)}, que delega en las
 * subclases la decisión de qué subtipo concreto de {@link Notificacion} instanciar, sin que
 * el código cliente conozca esa implementación.
 * <p>
 * El método público {@link #generarNotificacion(Usuario, String)} actúa como plantilla:
 * valida el destinatario, invoca al método fábrica y aplica la configuración común a toda
 * notificación (marca de tiempo en la zona horaria de Lima y estado inicial). Al ser
 * {@code final}, garantiza que ninguna subclase pueda alterar ese flujo.
 *
 * @see SistemaNotificacionCreator
 * @see RecordatorioNotificacionCreator
 */
public abstract class NotificacionCreator {

    /** Zona horaria oficial usada para estampar la fecha de envío de las notificaciones. */
    private static final String ZONE_LIMA = "America/Lima";

    /**
     * Método fábrica (Factory Method) que cada Creator concreto implementa para construir
     * el subtipo de notificación que le corresponde.
     *
     * @param destinatario Usuario que recibirá la notificación.
     * @param mensaje      Contenido textual de la notificación; puede ser {@code null} si el
     *                     subtipo genera su propio mensaje (por ejemplo, los recordatorios).
     * @return Una instancia concreta de {@link Notificacion}.
     */
    protected abstract Notificacion crearNotificacion(Usuario destinatario, String mensaje);

    /**
     * Define el estado con el que nace la notificación según su tipo
     * (por ejemplo, {@code "ENVIADA"} para las alertas de sistema o
     * {@code "PENDIENTE"} para los recordatorios programados).
     *
     * @return El estado inicial que se asignará a la notificación creada.
     */
    protected abstract String estadoInicial();

    /**
     * Genera una notificación lista para persistir: valida el destinatario, delega la
     * construcción en el método fábrica y le aplica la fecha de envío y el estado inicial.
     *
     * @param destinatario Usuario que recibirá la notificación; no puede ser {@code null}.
     * @param mensaje      Contenido textual de la notificación.
     * @return La {@link Notificacion} construida y configurada por el Creator concreto.
     * @throws IllegalArgumentException Si el destinatario es {@code null}.
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
