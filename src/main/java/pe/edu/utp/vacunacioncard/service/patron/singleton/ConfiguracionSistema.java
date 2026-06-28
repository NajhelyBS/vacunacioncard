package pe.edu.utp.vacunacioncard.service.patron.singleton;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.Getter;

/**
 * Singleton que centraliza la configuración global del sistema de vacunación.
 * Implementa el patrón Bill Pugh (Initialization-on-demand holder) para
 * garantizar thread-safety y lazy initialization.
 */
@Getter
public class ConfiguracionSistema implements Serializable {
    private static final long serialVersionUID = 1L;

    private final ZoneId zonaHoraria = ZoneId.of("America/Lima");
    private String nombreSistema = "VacunacionCard";
    private int maxIntentosLogin = 3;
    private int diasValidezCita = 30;
    private boolean notificacionesActivas = true;

    private ConfiguracionSistema() {
    }

    private static class Holder {
        private static final ConfiguracionSistema INSTANCIA = new ConfiguracionSistema();
    }

    /**
     * Proporciona el punto de acceso global a la instancia única de la configuración.
     */
    public static ConfiguracionSistema getInstancia() {
        return Holder.INSTANCIA;
    }

    protected Object readResolve() {
        return getInstancia();
    }

    public void setNombreSistema(String nombreSistema) {
        this.nombreSistema = nombreSistema;
    }

    public void setMaxIntentosLogin(int maxIntentosLogin) {
        this.maxIntentosLogin = maxIntentosLogin;
    }

    public void setDiasValidezCita(int diasValidezCita) {
        this.diasValidezCita = diasValidezCita;
    }

    public void setNotificacionesActivas(boolean notificacionesActivas) {
        this.notificacionesActivas = notificacionesActivas;
    }

    /**
     * Restaura todos los valores de configuración a sus valores por defecto.
     */
    public void reset() {
        this.nombreSistema = "VacunacionCard";
        this.maxIntentosLogin = 3;
        this.diasValidezCita = 30;
        this.notificacionesActivas = true;
    }

    /**
     * Verifica si una cuenta debe ser bloqueada según los intentos fallidos.
     */
    public boolean isLoginBloqueado(int intentosFallidos) {
        return intentosFallidos >= maxIntentosLogin;
    }

    /**
     * Verifica si una cita sigue vigente según los días de validez configurados.
     */
    public boolean isCitaVigente(LocalDate fechaCita) {
        LocalDate hoy = LocalDate.now(zonaHoraria);
        return fechaCita != null && !fechaCita.plusDays(diasValidezCita).isBefore(hoy);
    }
}
