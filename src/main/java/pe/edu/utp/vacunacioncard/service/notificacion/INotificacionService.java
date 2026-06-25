package pe.edu.utp.vacunacioncard.service.notificacion;

import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import java.util.List;

/**
 * Interfaz de servicio para la gestión y procesamiento de alertas en el sistema.
 */
public interface INotificacionService {
    
    /**
     * Procesa, persiste y simula el despacho de una alerta hacia un destinatario.
     * @param notificacion Objeto notificación estructurado a procesar.
     * @return La notificación persistida o null en caso de error.
     */
    Notificacion enviarNotificacion(Notificacion notificacion);

    /**
     * Recupera el compendio general de alertas recibidas por un operador o paciente.
     * @param usuarioId Identificador único del usuario.
     * @return Lista de objetos {@link Notificacion}.
     */
    List<Notificacion> listarPorUsuario(Long usuarioId);

    /**
     * Orquesta la creación dinámica de una alerta del sistema empleando el patrón Factory Method.
     * @param usuario Instancia del usuario destinatario.
     * @param mensaje Cuerpo textual del aviso.
     */
    void registrarNuevaAlerta(Usuario usuario, String mensaje);
}
