package pe.edu.utp.vacunacioncard.service.patron.creacional.factorymethod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionRecordatorio;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionSistema;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Factory Method - NotificacionFactory")
class NotificacionFactoryTest {

    private final Usuario usuario = new Enfermero();

    @Test
    @DisplayName("Crea notificación de tipo SISTEMA")
    void creaTipoSistema() {
        Notificacion notif = NotificacionFactory.crearNotificacion("SISTEMA", usuario, "Mensaje");

        assertInstanceOf(NotificacionSistema.class, notif);
        assertEquals("Mensaje", notif.getMensaje());
        assertSame(usuario, notif.getDestinatario());
    }

    @Test
    @DisplayName("Crea notificación de tipo ALERTA")
    void creaTipoAlerta() {
        Notificacion notif = NotificacionFactory.crearNotificacion("ALERTA", usuario, "Alerta");

        assertInstanceOf(NotificacionSistema.class, notif);
    }

    @Test
    @DisplayName("Crea notificación de tipo INFORMACION")
    void creaTipoInformacion() {
        Notificacion notif = NotificacionFactory.crearNotificacion("INFORMACION", usuario, "Info");

        assertInstanceOf(NotificacionSistema.class, notif);
    }

    @Test
    @DisplayName("Acepta tipos en minúsculas")
    void aceptaMinusculas() {
        Notificacion notif = NotificacionFactory.crearNotificacion("sistema", usuario, "Test");

        assertInstanceOf(NotificacionSistema.class, notif);
    }

    @Test
    @DisplayName("Lanza excepción para tipo no soportado")
    void tipoInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> NotificacionFactory.crearNotificacion("INVALIDO", usuario, "Msg"));
    }

    @Test
    @DisplayName("Lanza excepción para tipo nulo")
    void tipoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> NotificacionFactory.crearNotificacion(null, usuario, "Msg"));
    }

    @Test
    @DisplayName("Crea recordatorio correctamente")
    void creaRecordatorio() {
        RegistroVacuna registro = new RegistroVacuna();
        LocalDateTime fecha = LocalDateTime.now().plusDays(30);

        Notificacion notif = NotificacionFactory.crearRecordatorio(usuario, registro, fecha);

        assertInstanceOf(NotificacionRecordatorio.class, notif);
        assertEquals("Recordatorio de proxima dosis", notif.getMensaje());
    }

    @Test
    @DisplayName("Recordatorio lanza excepción si registro es nulo")
    void recordatorioRegistroNulo() {
        LocalDateTime fecha = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class,
                () -> NotificacionFactory.crearRecordatorio(usuario, null, fecha));
    }

    @Test
    @DisplayName("Recordatorio lanza excepción si fecha es nula")
    void recordatorioFechaNula() {
        RegistroVacuna registro = new RegistroVacuna();
        assertThrows(IllegalArgumentException.class,
                () -> NotificacionFactory.crearRecordatorio(usuario, registro, null));
    }
}
