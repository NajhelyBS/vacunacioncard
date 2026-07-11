package pe.edu.utp.vacunacioncard.service.patron.observer;

import org.springframework.stereotype.Service;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.service.patron.observer.impl.AuditoriaObserver;
import pe.edu.utp.vacunacioncard.service.patron.observer.impl.CartillaObserver;
import pe.edu.utp.vacunacioncard.service.patron.observer.impl.RecordatorioObserver;

/**
 * Sujeto concreto (Concrete Subject) del patrón Observer, gestionado por Spring.
 * Envuelve un {@link NotificadorVacunacion}, suscribe los observadores inyectados por Spring
 * y expone métodos de negocio que disparan los eventos ocurridos al aplicar una dosis.
 */
@Service
public class NotificadorVacunacionConcreto {

    /** Evento emitido cuando se aplica una dosis (reaccionan cartilla y auditoría). */
    public static final String DOSIS_APLICADA = "DOSIS_APLICADA";

    /** Evento emitido cuando la dosis tiene una próxima fecha programada (reacciona el recordatorio). */
    public static final String PROXIMA_DOSIS = "PROXIMA_DOSIS";

    private final NotificadorVacunacion notificador;

    /**
     * Crea el sujeto concreto y suscribe cada observador a los eventos que le corresponden.
     *
     * @param cartillaObserver    Observador que adjunta la dosis a la cartilla.
     * @param auditoriaObserver   Observador que registra la traza de auditoría.
     * @param recordatorioObserver Observador que genera el recordatorio de próxima dosis.
     */
    public NotificadorVacunacionConcreto(CartillaObserver cartillaObserver,
                                         AuditoriaObserver auditoriaObserver,
                                         RecordatorioObserver recordatorioObserver) {
        this.notificador = new NotificadorVacunacion(DOSIS_APLICADA, PROXIMA_DOSIS);
        this.notificador.suscribir(DOSIS_APLICADA, cartillaObserver);
        this.notificador.suscribir(DOSIS_APLICADA, auditoriaObserver);
        this.notificador.suscribir(PROXIMA_DOSIS, recordatorioObserver);
    }

    /**
     * Notifica que se aplicó una dosis (dispara la reacción de cartilla y auditoría).
     *
     * @param registro Registro de la dosis aplicada.
     */
    public void dosisAplicada(RegistroVacuna registro) {
        notificador.notificar(DOSIS_APLICADA, registro);
    }

    /**
     * Notifica que corresponde programar una próxima dosis (dispara el recordatorio).
     *
     * @param registro Registro de la dosis con la próxima fecha.
     */
    public void programarProximaDosis(RegistroVacuna registro) {
        notificador.notificar(PROXIMA_DOSIS, registro);
    }

    /**
     * Expone el notificador subyacente (útil para suscribir observadores adicionales o para pruebas).
     *
     * @return el {@link NotificadorVacunacion} interno.
     */
    public NotificadorVacunacion getNotificador() {
        return notificador;
    }
}
