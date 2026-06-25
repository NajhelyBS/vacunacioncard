package pe.edu.utp.vacunacioncard.repository.notificacion;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByDestinatarioId(Long destinatarioId);
    List<Notificacion> findByDestinatarioIdAndEstadoIgnoreCase(Long destinatarioId, String estado);
}
