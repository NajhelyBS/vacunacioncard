package pe.edu.utp.vacunacioncard.service.patron.factorymethod;

import java.time.LocalDateTime;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionRecordatorio;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

/** ConcreteCreator: construye recordatorios de vacunación. */
public class RecordatorioNotificacionCreator extends NotificacionCreator {

    private static final String ESTADO_PENDIENTE = "PENDIENTE";
    private final RegistroVacuna registroVacuna;
    private final LocalDateTime fechaRecordatorio;

    public RecordatorioNotificacionCreator(RegistroVacuna registroVacuna, LocalDateTime fechaRecordatorio) {
        if (registroVacuna == null) {
            throw new IllegalArgumentException("El registro de vacuna no puede ser nulo.");
        }
        if (fechaRecordatorio == null) {
            throw new IllegalArgumentException("La fecha de recordatorio no puede ser nula.");
        }
        this.registroVacuna = registroVacuna;
        this.fechaRecordatorio = fechaRecordatorio;
    }
    @Override
    protected Notificacion crearNotificacion(Usuario destinatario, String mensaje) {
        return new NotificacionRecordatorio(destinatario, this.registroVacuna, this.fechaRecordatorio);
    }

    @Override
    protected String estadoInicial() {
        return ESTADO_PENDIENTE;
    }
}