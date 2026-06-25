package pe.edu.utp.vacunacioncard.service.notificacion;

import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import java.util.List;

/**
 * Interfaz de servicio para la gestión de notificaciones.
 * Definida para soportar pruebas unitarias y lógica de negocio.
 */
public interface INotificacionService {
    
    /**
     * Procesa y envía una notificación al destinatario.
     * @param notificacion Objeto notificación a procesar.
     */
    void enviarNotificacion(Notificacion notificacion);

    /**
     * Recupera las notificaciones pendientes de un usuario.
     * @param usuarioId ID del usuario.
     * @return Lista de notificaciones.
     */
    List<Notificacion> listarPorUsuario(Long usuarioId);
}