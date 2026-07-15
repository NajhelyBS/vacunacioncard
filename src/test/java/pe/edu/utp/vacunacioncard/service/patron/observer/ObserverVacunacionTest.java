package pe.edu.utp.vacunacioncard.service.patron.observer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.service.patron.observer.impl.AuditoriaObserver;
import pe.edu.utp.vacunacioncard.service.patron.observer.impl.CartillaObserver;
import pe.edu.utp.vacunacioncard.service.patron.observer.impl.RecordatorioObserver;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Observer - NotificadorVacunacion")
class ObserverVacunacionTest {

    private static final LocalDateTime PROXIMA_DOSIS_FIJA = LocalDateTime.of(2026, Month.SEPTEMBER, 1, 10, 0);

    private RegistroVacuna registroDemo() {
        RegistroVacuna registro = new RegistroVacuna();
        registro.setNumeroDosis(1);
        registro.setLote("LOTE-ABC-123");
        registro.setProximaDosis(PROXIMA_DOSIS_FIJA);
        return registro;
    }

    private NotificadorVacunacionConcreto concreto() {
        return new NotificadorVacunacionConcreto(
                new CartillaObserver(), new AuditoriaObserver(), new RecordatorioObserver());
    }

    @Test
    @DisplayName("El sujeto concreto dispara los eventos sin error")
    void disparaEventos() {
        NotificadorVacunacionConcreto concreto = concreto();
        RegistroVacuna registro = registroDemo();

        assertDoesNotThrow(() -> {
            concreto.dosisAplicada(registro);
            concreto.programarProximaDosis(registro);
        });
    }

    @Test
    @DisplayName("Notifica a todos los observadores suscritos a un evento")
    void notificaATodosLosSuscritos() {
        AtomicInteger invocaciones = new AtomicInteger(0);
        NotificadorVacunacion notificador = new NotificadorVacunacion(NotificadorVacunacionConcreto.DOSIS_APLICADA);

        notificador.suscribir(NotificadorVacunacionConcreto.DOSIS_APLICADA, (evento, registro) -> invocaciones.incrementAndGet());
        notificador.suscribir(NotificadorVacunacionConcreto.DOSIS_APLICADA, (evento, registro) -> invocaciones.incrementAndGet());

        notificador.notificar(NotificadorVacunacionConcreto.DOSIS_APLICADA, registroDemo());

        assertEquals(2, invocaciones.get());
    }

    @Test
    @DisplayName("Notificar un evento sin suscriptores no lanza error")
    void eventoSinSuscriptores() {
        NotificadorVacunacion notificador = new NotificadorVacunacion();
        assertDoesNotThrow(() -> notificador.notificar("INEXISTENTE", registroDemo()));
    }

    @Test
    @DisplayName("getNotificador retorna la instancia interna del notificador subyacente")
    void testGetNotificador() {
        NotificadorVacunacionConcreto concreto = concreto();
        NotificadorVacunacion interno = concreto.getNotificador();

        assertNotNull(interno);
    }
}
