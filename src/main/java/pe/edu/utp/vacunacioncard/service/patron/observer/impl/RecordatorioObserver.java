package pe.edu.utp.vacunacioncard.service.patron.observer.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.service.patron.observer.ObservadorVacunacion;

/**
 * Observador concreto que genera un recordatorio para la próxima dosis.
 * En el flujo real, aquí se delegaría la creación de la notificación al Factory Method
 * ({@code RecordatorioNotificacionFactory}).
 */
@Slf4j
@Component
public class RecordatorioObserver implements ObservadorVacunacion {

    @Override
    public void alAplicarDosis(String tipoEvento, RegistroVacuna registro) {
        log.info("Evento: {} - Generando recordatorio de próxima dosis para el {}",
                tipoEvento, registro.getProximaDosis());
    }
}
