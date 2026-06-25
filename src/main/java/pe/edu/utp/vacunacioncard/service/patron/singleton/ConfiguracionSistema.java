package pe.edu.utp.vacunacioncard.service.patron.singleton;

import java.io.Serializable;
import java.time.ZoneId;
import lombok.Getter;

/**
 * Singleton que centraliza la configuracion global del sistema de vacunacion.
 */
@Getter
public class ConfiguracionSistema implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static ConfiguracionSistema instancia;

    private final ZoneId zonaHoraria = ZoneId.of("America/Lima");
    private String nombreSistema = "VacunacionCard";
    private int maxIntentosLogin = 3;
    private int diasValidezCita = 30;
    private boolean notificacionesActivas = true;

    /**
     * Constructor 
     */
    ConfiguracionSistema() {
        
    }

    /**
     * Proporciona el punto de acceso global a la instancia única de la configuración.
     */
    public static synchronized ConfiguracionSistema getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionSistema();
        }
        return instancia;
    }

    /**
     * Actualiza el máximo de intentos de inicio de sesión de forma segura.
     */
    public synchronized void setMaxIntentosLogin(int maxIntentosLogin) {
        this.maxIntentosLogin = maxIntentosLogin;
    }

    /**
     * Define la validez en días para una cita programada de forma segura.
     */
    public synchronized void setDiasValidezCita(int diasValidezCita) {
        this.diasValidezCita = diasValidezCita;
    }

    /**
     * Activa o desactiva el envío de notificaciones de forma segura.
     */
    public synchronized void setNotificacionesActivas(boolean notificacionesActivas) {
        this.notificacionesActivas = notificacionesActivas;
    }

    /**
     * Establece el nombre oficial del sistema de forma segura.
     */
    public synchronized void setNombreSistema(String nombreSistema) {
        this.nombreSistema = nombreSistema;
    }
}



