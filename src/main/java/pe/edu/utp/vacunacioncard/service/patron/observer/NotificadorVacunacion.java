package pe.edu.utp.vacunacioncard.service.patron.observer;

import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sujeto (Subject/Publisher) del patrón Observer.
 * Mantiene los observadores agrupados por tipo de evento y los notifica cuando el evento ocurre.
 */
public class NotificadorVacunacion {

    private final Map<String, List<ObservadorVacunacion>> observadores = new HashMap<>();

    /**
     * Registra los tipos de evento que este notificador es capaz de emitir.
     *
     * @param eventos Nombres de los eventos soportados.
     */
    public NotificadorVacunacion(String... eventos) {
        for (String evento : eventos) {
            observadores.put(evento, new ArrayList<>());
        }
    }

    /**
     * Suscribe un observador a un tipo de evento.
     *
     * @param tipoEvento Evento al que se suscribe.
     * @param observador Observador que reaccionará ante el evento.
     */
    public void suscribir(String tipoEvento, ObservadorVacunacion observador) {
        observadores.computeIfAbsent(tipoEvento, e -> new ArrayList<>()).add(observador);
    }

    /**
     * Notifica a todos los observadores suscritos a un tipo de evento.
     *
     * @param tipoEvento Evento que se está emitiendo.
     * @param registro   Registro de vacuna que acompaña al evento.
     */
    public void notificar(String tipoEvento, RegistroVacuna registro) {
        List<ObservadorVacunacion> suscritos = observadores.getOrDefault(tipoEvento, List.of());
        for (ObservadorVacunacion observador : suscritos) {
            observador.alAplicarDosis(tipoEvento, registro);
        }
    }
}
