package pe.edu.utp.vacunacioncard.service.salud;

import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión y control de Contraindicaciones de vacunas.
 */
public interface IContraindicacionService {

    /**
     * Obtiene una lista con todas las contraindicaciones registradas en el sistema.
     * @return Lista de objetos {@link Contraindicacion}.
     */
    List<Contraindicacion> listarTodas();

    /**
     * Recupera una contraindicación específica mediante su identificador único.
     * @param id El identificador único de la contraindicación.
     * @return Un {@link Optional} que contiene el registro si es hallado.
     */
    Optional<Contraindicacion> obtenerPorId(Long id);

    /**
     * Registra o actualiza una contraindicación médica con control de persistencia y errores.
     * @param contraindicacion El objeto contraindicación a guardar.
     * @return La {@link Contraindicacion} guardada con éxito, o null si ocurre un fallo.
     */
    Contraindicacion registrar(Contraindicacion contraindicacion);

    /**
     * Obtiene el catálogo de restricciones médicas vinculadas a una vacuna en particular.
     * @param vacunaId Identificador único de la vacuna.
     * @return Lista de objetos {@link Contraindicacion}.
     */
    List<Contraindicacion> listarPorVacunaAfectada(Long vacunaId);
}

