package pe.edu.utp.vacunacioncard.repository.notificacion;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import java.util.List;

/**
 * Repositorio de la entidad Notificacion que permite realizar operaciones CRUD
 * y consultas por destinatario y estado.
 */
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    /**
     * Obtiene todas las notificaciones dirigidas a un destinatario específico.
     *
     * @param destinatarioId Identificador único del usuario receptor.
     * @return {@link List} con las {@link Notificacion} del destinatario.
     */
    List<Notificacion> findByDestinatarioId(Long destinatarioId);

    /**
     * Obtiene las notificaciones de un destinatario filtradas por estado,
     * comparando el estado sin distinguir mayúsculas y minúsculas.
     *
     * @param destinatarioId Identificador único del usuario receptor.
     * @param estado         Estado por el cual filtrar (por ejemplo, "ENVIADA", "PENDIENTE", "LEIDA").
     * @return {@link List} con las {@link Notificacion} que coinciden con el destinatario y el estado.
     */
    List<Notificacion> findByDestinatarioIdAndEstadoIgnoreCase(Long destinatarioId, String estado);
}
