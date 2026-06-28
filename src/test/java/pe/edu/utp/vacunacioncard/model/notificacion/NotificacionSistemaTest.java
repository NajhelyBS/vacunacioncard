package pe.edu.utp.vacunacioncard.model.notificacion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Modelo - NotificacionSistema")
class NotificacionSistemaTest {

    private final Usuario usuario = new Enfermero();

    @Test
    @DisplayName("Constructor asigna campos correctamente")
    void constructorAsignaCampos() {
        NotificacionSistema notif = new NotificacionSistema(usuario, "Hola", "ALERTA");

        assertEquals("Hola", notif.getMensaje());
        assertEquals("ALERTA", notif.getTipo());
        assertSame(usuario, notif.getDestinatario());
        assertFalse(notif.isLeida());
    }

    @Test
    @DisplayName("Lanza excepción si tipo es nulo")
    void tipoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new NotificacionSistema(usuario, "Msg", null));
    }

    @Test
    @DisplayName("Lanza excepción si tipo está vacío")
    void tipoVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> new NotificacionSistema(usuario, "Msg", "  "));
    }
}
