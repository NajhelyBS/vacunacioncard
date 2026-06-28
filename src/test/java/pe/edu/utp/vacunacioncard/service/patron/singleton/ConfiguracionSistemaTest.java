package pe.edu.utp.vacunacioncard.service.patron.singleton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Singleton - ConfiguracionSistema")
class ConfiguracionSistemaTest {

    private ConfiguracionSistema config;

    @BeforeEach
    void setUp() {
        config = ConfiguracionSistema.getInstancia();
        config.reset();
    }

    @Test
    @DisplayName("Siempre retorna la misma instancia")
    void retornaMismaInstancia() {
        ConfiguracionSistema otra = ConfiguracionSistema.getInstancia();
        assertSame(config, otra);
    }

    @Test
    @DisplayName("Valores por defecto son correctos")
    void valoresPorDefecto() {
        assertEquals("VacunacionCard", config.getNombreSistema());
        assertEquals(3, config.getMaxIntentosLogin());
        assertEquals(30, config.getDiasValidezCita());
        assertTrue(config.isNotificacionesActivas());
    }

    @Test
    @DisplayName("Los setters modifican la configuración")
    void settersModifican() {
        config.setMaxIntentosLogin(5);
        config.setNombreSistema("NuevoNombre");

        assertEquals(5, config.getMaxIntentosLogin());
        assertEquals("NuevoNombre", config.getNombreSistema());
    }

    @Test
    @DisplayName("Reset restaura los valores por defecto")
    void resetRestaura() {
        config.setMaxIntentosLogin(10);
        config.reset();

        assertEquals(3, config.getMaxIntentosLogin());
    }

    @Test
    @DisplayName("Login bloqueado cuando intentos >= límite")
    void loginBloqueado() {
        assertTrue(config.isLoginBloqueado(3));
        assertTrue(config.isLoginBloqueado(5));
        assertFalse(config.isLoginBloqueado(2));
    }

    @Test
    @DisplayName("Cita vigente dentro del rango de días")
    void citaVigente() {
        LocalDate hoy = LocalDate.now(config.getZonaHoraria());

        assertTrue(config.isCitaVigente(hoy));
        assertFalse(config.isCitaVigente(hoy.minusDays(31)));
        assertFalse(config.isCitaVigente(null));
    }
}
