package pe.edu.utp.vacunacioncard.service.patron.creacional.factorymethod;

import java.time.LocalDateTime;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionRecordatorio;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

/**
 * Fábrica concreta encargada de la creación de Notificaciones de Recordatorio de Vacunas.
 */
public class RecordatorioNotificacionFactory implements INotificacionFactory {

    private final RegistroVacuna registroVacuna;
    private final LocalDateTime fechaRecordatorio;

    public RecordatorioNotificacionFactory(RegistroVacuna registroVacuna, LocalDateTime fechaRecordatorio) {
        this.registroVacuna = registroVacuna;
        this.fechaRecordatorio = fechaRecordatorio;
    }

    /***
     * Crea una notificación de recordatorio de vacuna. El mensaje se genera automáticamente dentro de NotificacionRecordatorio.
     * @param destinatario
     * @param mensaje
     * @return
     */
    @Override
    public Notificacion createNotification(Usuario destinatario, String mensaje) {
        return new NotificacionRecordatorio(destinatario, this.registroVacuna, this.fechaRecordatorio);
    }
}
