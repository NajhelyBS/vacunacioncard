package pe.edu.utp.vacunacioncard.service.patron.factorymethod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Month;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionRecordatorio;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionSistema;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"java:S5976", "java:S5778"})
@DisplayName("Factory Method - NotificacionCreator")
class NotificacionFactoryTest {

    private final Usuario usuario = new Enfermero();

    private static final LocalDateTime FECHA_TEST_FIJA = LocalDateTime.of(2026, Month.AUGUST, 1, 10, 0);

    @Test
    @DisplayName("Crea notificación de tipo SISTEMA")
    void creaTipoSistema() {
        NotificacionCreator creator = new SistemaNotificacionCreator("SISTEMA");
        Notificacion notif = creator.generarNotificacion(usuario, "Mensaje de Sistema");

        assertInstanceOf(NotificacionSistema.class, notif);
        assertEquals("Mensaje de Sistema", notif.getMensaje());
        assertSame(usuario, notif.getDestinatario());
        assertEquals("ENVIADA", notif.getEstado());
        assertNotNull(notif.getFechaEnvio());
    }

    @Test
    @DisplayName("Crea notificación de tipo ALERTA")
    void creaTipoAlerta() {
        NotificacionCreator creator = new SistemaNotificacionCreator("ALERTA");
        Notificacion notif = creator.generarNotificacion(usuario, "Alerta Crítica");

        assertInstanceOf(NotificacionSistema.class, notif);
        assertEquals("Alerta Crítica", notif.getMensaje());
    }

    @Test
    @DisplayName("Crea notificación de tipo INFORMACION")
    void creaTipoInformacion() {
        NotificacionCreator creator = new SistemaNotificacionCreator("INFORMACION");
        Notificacion notif = creator.generarNotificacion(usuario, "Info General");

        assertInstanceOf(NotificacionSistema.class, notif);
        assertEquals("Info General", notif.getMensaje());
    }

    @Test
    @DisplayName("Acepta tipos en minúsculas")
    void aceptaMinusculas() {
        NotificacionCreator creator = new SistemaNotificacionCreator("sistema");
        Notificacion notif = creator.generarNotificacion(usuario, "Test Minúsculas");

        assertInstanceOf(NotificacionSistema.class, notif);
    }

    @Test
    @DisplayName("Lanza excepción para tipo no soportado")
    void tipoInvalido() {
        NotificacionCreator creator = new SistemaNotificacionCreator("INVALIDO");
        assertThrows(IllegalArgumentException.class,
                () -> creator.generarNotificacion(usuario, "Msg"));
    }

    @Test
    @DisplayName("Lanza excepción para tipo nulo o vacío en creador de sistema")
    void tipoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new SistemaNotificacionCreator(null));
        assertThrows(IllegalArgumentException.class,
                () -> new SistemaNotificacionCreator("   "));
    }

    @Test
    @DisplayName("Crea recordatorio correctamente")
    void creaRecordatorio() {
        RegistroVacuna registro = new RegistroVacuna();

        // Usamos la fecha fija calculada estáticamente sin tocar el reloj del sistema
        NotificacionCreator creator = new RecordatorioNotificacionCreator(registro, FECHA_TEST_FIJA);
        Notificacion notif = creator.generarNotificacion(usuario, null);

        assertInstanceOf(NotificacionRecordatorio.class, notif);
        assertEquals("PENDIENTE", notif.getEstado());
        assertNotNull(notif.getFechaEnvio());
    }

    @Test
    @DisplayName("Recordatorio lanza excepción si registro es nulo en el constructor")
    void recordatorioRegistroNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new RecordatorioNotificacionCreator(null, FECHA_TEST_FIJA));
    }

    @Test
    @DisplayName("Recordatorio lanza excepción si fecha es nula en el constructor")
    void recordatorioFechaNula() {
        RegistroVacuna registro = new RegistroVacuna();

        assertThrows(IllegalArgumentException.class,
                () -> new RecordatorioNotificacionCreator(registro, null));
    }

    @Test
    @DisplayName("Lanza excepción si el destinatario es nulo en la operación base")
    void destinatarioNulo() {
        NotificacionCreator creator = new SistemaNotificacionCreator("SISTEMA");

        assertThrows(IllegalArgumentException.class,
                () -> creator.generarNotificacion(null, "Mensaje"));
    }
}
