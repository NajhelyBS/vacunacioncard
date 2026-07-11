package pe.edu.utp.vacunacioncard.service.patron.observer;

import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

/**
 * Observador (Observer) del patrón Observer.
 * Cada observador concreto reacciona ante un evento relacionado con la aplicación de una dosis.
 */
public interface ObservadorVacunacion {

    /**
     * Reacciona ante un evento de vacunación.
     *
     * @param tipoEvento Tipo de evento notificado (por ejemplo, "DOSIS_APLICADA").
     * @param registro   Registro de vacuna asociado al evento.
     */
    void alAplicarDosis(String tipoEvento, RegistroVacuna registro);
}
