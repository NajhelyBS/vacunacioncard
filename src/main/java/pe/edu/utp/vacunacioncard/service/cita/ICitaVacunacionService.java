package pe.edu.utp.vacunacioncard.service.cita;
import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio para la planificación, control y seguimiento de citas de vacunación.
 * Define las operaciones permitidas para el ciclo de vida de las programaciones médicas.
 */
public interface ICitaVacunacionService {

    /**
     * Recupera la totalidad de las citas de vacunación registradas en el sistema.
     *
     * @return {@link List} que contiene las entidades {@link CitaVacunacion}, o una lista vacía si no hay registros.
     */
    List<CitaVacunacion> getAll();

    /**
     * Busca el registro clínico de una cita de vacunación mediante su identificador único.
     *
     * @param id El identificador único de la cita.
     * @return Un contenedor {@link Optional} que aloja la cita si se encuentra, o vacío en caso contrario.
     */
    Optional<CitaVacunacion> findById(Long id);

    /**
     * Registra y programa una nueva cita de vacunación en el repositorio.
     *
     * @param cita La entidad {@link CitaVacunacion} con los datos de la nueva programación.
     * @return La entidad persistida con su respectivo identificador generado.
     * @throws ServiceException Si ocurre una anomalía a nivel de datos durante el registro.
     */
    CitaVacunacion create(CitaVacunacion cita);

    /**
     * Actualiza los datos de una cita de vacunación ya existente en el sistema.
     *
     * @param cita La entidad {@link CitaVacunacion} con los valores actualizados.
     * @return La entidad modificada y guardada en la base de datos.
     * @throws ServiceException Si el ID es nulo, si la cita no existe o si ocurre un fallo en la persistencia.
     */
    CitaVacunacion update(CitaVacunacion cita);

    /**
     * Obtiene el historial de citas médicas asociadas a un paciente en específico.
     *
     * @param patientId El identificador único del paciente.
     * @return Una lista de {@link CitaVacunacion} correspondientes al paciente indicado.
     */
    List<CitaVacunacion> findByPatient(Long patientId);

    /**
     * Filtra y lista las citas de vacunación según su estado operativo actual.
     *
     * @param status El estado clínico o administrativo a filtrar (ej. PENDIENTE, COMPLETADA).
     * @return Una lista de {@link CitaVacunacion} que cumplen con el estado especificado.
     */
    List<CitaVacunacion> findByStatus(String status);

    /**
     * Elimina físicamente del repositorio una cita de vacunación mediante su ID.
     *
     * @param id El identificador único de la cita a remover.
     * @throws ServiceException Si se presenta un error al intentar eliminar el registro.
     */
    void deleteById(Long id);
}
