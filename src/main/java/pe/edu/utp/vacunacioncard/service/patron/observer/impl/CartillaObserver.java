package pe.edu.utp.vacunacioncard.service.patron.observer.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.service.patron.observer.ObservadorVacunacion;

/**
 * Observador concreto que adjunta la dosis aplicada a la cartilla del paciente.
 */
@Slf4j
@Component
public class CartillaObserver implements ObservadorVacunacion {

    /**
     * {@inheritDoc}
     * <p>
     * Reacciona al evento {@code DOSIS_APLICADA} adjuntando la dosis al historial
     * (cartilla) del paciente.
     */
    @Override
    public void alAplicarDosis(String tipoEvento, RegistroVacuna registro) {
        log.info("Evento: {} - Adjuntando dosis N°{} (lote {}) a la cartilla del paciente",
                tipoEvento, registro.getNumeroDosis(), registro.getLote());
    }
}
