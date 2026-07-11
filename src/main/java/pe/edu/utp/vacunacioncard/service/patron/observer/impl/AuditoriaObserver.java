package pe.edu.utp.vacunacioncard.service.patron.observer.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.service.patron.observer.ObservadorVacunacion;

/**
 * Observador concreto que registra la traza de auditoría de la aplicación de la dosis.
 */
@Slf4j
@Component
public class AuditoriaObserver implements ObservadorVacunacion {

    @Override
    public void alAplicarDosis(String tipoEvento, RegistroVacuna registro) {
        log.info("Evento: {} - Registrando en auditoría la aplicación de la dosis (lote {})",
                tipoEvento, registro.getLote());
    }
}
