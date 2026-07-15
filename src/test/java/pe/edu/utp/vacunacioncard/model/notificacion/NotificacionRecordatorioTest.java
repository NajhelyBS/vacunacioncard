package pe.edu.utp.vacunacioncard.model.notificacion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Modelo - NotificacionRecordatorio")
class NotificacionRecordatorioTest {

    private final Usuario usuario = new Enfermero();

    /** Fecha fija para garantizar pruebas deterministas. */
    private static final LocalDateTime FECHA_FIJA = LocalDateTime.of(2026, Month.JULY, 15, 10, 0);

    @Test
    @DisplayName("Constructor asigna campos correctamente")
    void constructorAsignaCampos() {
        RegistroVacuna registro = new RegistroVacuna();
        LocalDateTime fecha = FECHA_FIJA;

        NotificacionRecordatorio notif = new NotificacionRecordatorio(usuario, registro, fecha);

        assertEquals("Recordatorio de proxima dosis", notif.getMensaje());
        assertSame(usuario, notif.getDestinatario());
        assertSame(registro, notif.getRegistroVacuna());
        assertTrue(fecha.isEqual(notif.getFechaRecordatorio()));
    }

    @Test
    @DisplayName("Lanza excepción si registro es nulo")
    void registroNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new NotificacionRecordatorio(usuario, null, FECHA_FIJA));
    }

    @Test
    @DisplayName("Lanza excepción si fecha es nula")
    void fechaNula() {
        RegistroVacuna registro = new RegistroVacuna();
        assertThrows(IllegalArgumentException.class,
                () -> new NotificacionRecordatorio(usuario, registro, null));
    }
}
