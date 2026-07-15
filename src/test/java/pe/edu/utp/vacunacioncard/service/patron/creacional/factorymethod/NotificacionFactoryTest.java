package pe.edu.utp.vacunacioncard.service.patron.creacional.factorymethod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionRecordatorio;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionSistema;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.service.patron.factorymethod.INotificacionFactory;
import pe.edu.utp.vacunacioncard.service.patron.factorymethod.RecordatorioNotificacionFactory;
import pe.edu.utp.vacunacioncard.service.patron.factorymethod.SistemaNotificacionFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
@SuppressWarnings("java:S5976") 
@DisplayName("Factory Method - NotificacionFactory")
class NotificacionFactoryTest {

    private final Usuario usuario = new Enfermero();
    private static final String ZONE_LIMA = "America/Lima";

    @Test
    @DisplayName("Crea notificación de tipo SISTEMA")
    void creaTipoSistema() {
        INotificacionFactory factory = new SistemaNotificacionFactory("SISTEMA");
        Notificacion notif = factory.createNotification(usuario, "Mensaje");

        assertInstanceOf(NotificacionSistema.class, notif);
        assertEquals("Mensaje", notif.getMensaje());
        assertSame(usuario, notif.getDestinatario());
    }

    @Test
    @DisplayName("Crea notificación de tipo ALERTA")
    void creaTipoAlerta() {
        INotificacionFactory factory = new SistemaNotificacionFactory("ALERTA");
        Notificacion notif = factory.createNotification(usuario, "Alerta");

        assertInstanceOf(NotificacionSistema.class, notif);
    }

    @Test
    @DisplayName("Crea notificación de tipo INFORMACION")
    void creaTipoInformacion() {
        INotificacionFactory factory = new SistemaNotificacionFactory("INFORMACION");
        Notificacion notif = factory.createNotification(usuario, "Info");

        assertInstanceOf(NotificacionSistema.class, notif);
    }

    @Test
    @DisplayName("Acepta tipos en minúsculas")
    void aceptaMinusculas() {
        INotificacionFactory factory = new SistemaNotificacionFactory("sistema");
        Notificacion notif = factory.createNotification(usuario, "Test");

        assertInstanceOf(NotificacionSistema.class, notif);
    }

    @Test
    @DisplayName("Lanza excepción para tipo no soportado")
    void tipoInvalido() {
        INotificacionFactory factory = new SistemaNotificacionFactory("INVALIDO");
        assertThrows(IllegalArgumentException.class,
                () -> factory.createNotification(usuario, "Msg"));
    }

    @Test
    @DisplayName("Lanza excepción para tipo nulo o vacío en fábrica de sistema")
    void tipoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new SistemaNotificacionFactory(null));
        assertThrows(IllegalArgumentException.class,
                () -> new SistemaNotificacionFactory("   "));
    }

    @Test
    @DisplayName("Crea recordatorio correctamente")
    void creaRecordatorio() {
        RegistroVacuna registro = new RegistroVacuna();
        LocalDateTime fecha = LocalDateTime.now(ZoneId.of(ZONE_LIMA)).plusDays(30);

        INotificacionFactory factory = new RecordatorioNotificacionFactory(registro, fecha);
        Notificacion notif = factory.createNotification(usuario, null);

        assertInstanceOf(NotificacionRecordatorio.class, notif);
        assertEquals("Recordatorio de proxima dosis", notif.getMensaje());
    }

    @Test
    @DisplayName("Recordatorio lanza excepción si registro es nulo")
    void recordatorioRegistroNulo() {
        LocalDateTime fecha = LocalDateTime.now(ZoneId.of(ZONE_LIMA));
        INotificacionFactory factory = new RecordatorioNotificacionFactory(null, fecha);
        
        assertThrows(IllegalArgumentException.class,
                () -> factory.createNotification(usuario, null));
    }

    @Test
    @DisplayName("Recordatorio lanza excepción si fecha es nula")
    void recordatorioFechaNula() {
        RegistroVacuna registro = new RegistroVacuna();
        INotificacionFactory factory = new RecordatorioNotificacionFactory(registro, null);
        
        assertThrows(IllegalArgumentException.class,
                () -> factory.createNotification(usuario, null));
    }
}
