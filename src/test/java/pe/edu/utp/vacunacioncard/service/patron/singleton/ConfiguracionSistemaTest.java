package pe.edu.utp.vacunacioncard.service.patron.singleton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
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
        config.setDiasValidezCita(45);           
        config.setNotificacionesActivas(false);   

        assertEquals(5, config.getMaxIntentosLogin());
        assertEquals("NuevoNombre", config.getNombreSistema());
        assertEquals(45, config.getDiasValidezCita());
        assertFalse(config.isNotificacionesActivas());
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

        // Caso 1: Una cita programada para hoy es vigente
        assertTrue(config.isCitaVigente(hoy));

        // Caso 2: Una cita de hace exactamente 30 días todavía es vigente (Límite)
        assertTrue(config.isCitaVigente(hoy.minusDays(30)));

        // Caso 3: Una cita de hace 31 días ya caducó (Fuera de límite)
        assertFalse(config.isCitaVigente(hoy.minusDays(31)));

        // Caso 4: Una cita nula no es vigente
        assertFalse(config.isCitaVigente(null));
    }

    @Test
    @DisplayName("readResolve mantiene la misma instancia tras la deserialización")
    void readResolveMantieneInstancia() throws Exception {
       
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(config);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        ConfiguracionSistema deserializado = (ConfiguracionSistema) ois.readObject();
        ois.close();

        assertNotNull(deserializado);
        assertSame(config, deserializado); 
    }
}
