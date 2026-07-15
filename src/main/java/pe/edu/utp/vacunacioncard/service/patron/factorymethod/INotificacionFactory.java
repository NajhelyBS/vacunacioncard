package pe.edu.utp.vacunacioncard.service.patron.factorymethod;

import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Interfaz creadora base para el patrón Factory Method.
 * Define la firma del método de fabricación que deben implementar las fábricas concretas.
 */
public interface INotificacionFactory {

    /**
     * Factory Method que delega la instanciación de notificaciones a las subclases.
     *
     * @param destinatario El usuario que recibirá la notificación.
     * @param mensaje El cuerpo o contenido del mensaje.
     * @return Una instancia concreta de un subtipo de Notificacion.
     */
    Notificacion createNotification(Usuario destinatario, String mensaje);
}
