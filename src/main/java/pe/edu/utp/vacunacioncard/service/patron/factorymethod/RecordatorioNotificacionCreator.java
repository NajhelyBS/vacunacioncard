package pe.edu.utp.vacunacioncard.service.patron.factorymethod;

import java.time.LocalDateTime;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionRecordatorio;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

/**
 * Creator concreto (ConcreteCreator) del patrón <b>Factory Method</b>.
 * <p>
 * Construye notificaciones de tipo {@link NotificacionRecordatorio} para avisar al paciente
 * de una próxima dosis. Encapsula las dependencias del recordatorio (el registro de vacuna y
 * la fecha programada), que se validan al instanciar la fábrica, de modo que el servicio que
 * la usa no necesita conocerlas al momento de crear la notificación.
 * <p>
 * A diferencia de las alertas de sistema, estas notificaciones nacen en estado
 * {@code "PENDIENTE"}, ya que se despacharán en la fecha programada.
 *
 * @see NotificacionCreator
 */
public class RecordatorioNotificacionCreator extends NotificacionCreator {

    private static final String ESTADO_PENDIENTE = "PENDIENTE";

    private final RegistroVacuna registroVacuna;
    private final LocalDateTime fechaRecordatorio;

    /**
     * Crea la fábrica de recordatorios inyectando sus dependencias.
     *
     * @param registroVacuna    Registro de la dosis aplicada a la que se asocia el recordatorio.
     * @param fechaRecordatorio Fecha y hora en que debe gatillarse el recordatorio.
     * @throws IllegalArgumentException Si el registro de vacuna o la fecha son {@code null}.
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * Produce una {@link NotificacionRecordatorio} con el registro y la fecha configurados en
     * esta fábrica. El parámetro {@code mensaje} se ignora: el propio recordatorio genera su
     * texto a partir de sus datos.
     */
    @Override
    protected Notificacion crearNotificacion(Usuario destinatario, String mensaje) {
        return new NotificacionRecordatorio(destinatario, this.registroVacuna, this.fechaRecordatorio);
    }

    /**
     * {@inheritDoc}
     * <p>
     * El recordatorio se despachará en su fecha programada, por lo que nace como {@code "PENDIENTE"}.
     */
    @Override
    protected String estadoInicial() {
        return ESTADO_PENDIENTE;
    }
}
