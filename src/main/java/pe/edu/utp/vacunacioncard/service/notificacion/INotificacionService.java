package pe.edu.utp.vacunacioncard.service.notificacion;

import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Contrato del servicio para la gestión y envío de notificaciones.
 * Define el ciclo de vida de las alertas del sistema y los recordatorios de vacunación.
 */
public interface INotificacionService {

    /**
     * Despacha de forma inmediata una notificación previamente configurada,
     * estableciendo su estado a "ENVIADA" y su fecha de envío.
     *
     * @param notificacion Entidad {@link Notificacion} con el contenido y el destinatario asignado.
     * @return La entidad {@link Notificacion} persistida con su identificador generado.
     */
    Notificacion sendNotification(Notificacion notificacion);

    /**
     * Recupera todas las notificaciones dirigidas a un usuario específico.
     *
     * @param usuarioId Identificador único del usuario receptor.
     * @return {@link List} con las {@link Notificacion} del usuario.
     */
    List<Notificacion> findByUser(Long usuarioId);

    /**
     * Recupera las notificaciones de un usuario filtradas por su estado.
     * La comparación del estado es insensible a mayúsculas y minúsculas.
     *
     * @param usuarioId Identificador único del usuario receptor.
     * @param estado    Estado por el cual filtrar (por ejemplo, "ENVIADA", "PENDIENTE", "LEIDA").
     * @return {@link List} con las {@link Notificacion} del usuario que coinciden con el estado.
     */
    List<Notificacion> findByUserAndStatus(Long usuarioId, String estado);

    /**
     * Marca una notificación como leída cambiando su estado a "LEIDA".
     *
     * @param notificacionId Identificador único de la notificación a marcar.
     * @return La entidad {@link Notificacion} actualizada.
     */
    Notificacion markAsRead(Long notificacionId);

    /**
     * Construye y persiste una nueva alerta informativa dirigida a un usuario.
     *
     * @param usuario Entidad {@link Usuario} que recibirá la alerta.
     * @param mensaje Contenido textual de la alerta o evento del sistema.
     */
    void registerAlert(Usuario usuario, String mensaje);

    /**
     * Registra un recordatorio para la aplicación de una dosis o vacuna pendiente.
     *
     * @param usuario  Entidad {@link Usuario} que debe acudir a la vacunación.
     * @param registro Entidad {@link RegistroVacuna} con el detalle de la dosis correspondiente.
     * @param fecha    Fecha y hora en que se debe gatillar el recordatorio.
     */
    void registerVaccineReminder(Usuario usuario, RegistroVacuna registro, LocalDateTime fecha);
}
