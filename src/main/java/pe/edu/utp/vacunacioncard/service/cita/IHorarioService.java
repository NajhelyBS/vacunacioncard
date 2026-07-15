package pe.edu.utp.vacunacioncard.service.cita;

import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio para la gestión y administración del calendario base de atención.
 * Proporciona la lógica corporativa necesaria para configurar turnos, bloques operativos
 * y disponibilidades semanales.
 */
public interface IHorarioService {

    /**
     * Obtiene el catálogo completo de todos los horarios de atención configurados en el sistema.
     *
     * @return {@link List} que contiene las entidades {@link Horario}, o una lista vacía si no hay registros.
     */
    List<Horario> getAll();

    /**
     * Recupera un horario de atención médico específico a partir de su identificador único.
     *
     * @param id Identificador único y correlativo del horario en la base de datos.
     * @return Un contenedor {@link Optional} con el {@link Horario} si es hallado, o vacío en caso contrario.
     */
    Optional<Horario> findById(Long id);

    /**
     * Registra un nuevo horario o bloque de atención médica operativa en el repositorio.
     *
     * @param horario La entidad {@link Horario} que contiene los parámetros del nuevo turno.
     * @return La entidad persistida incluyendo su clave primaria autogenerada asignada.
     * @throws ServiceException Si se genera algún inconveniente transaccional durante la inserción en la base de datos.
     */
    Horario create(Horario horario);

    /**
     * Actualiza la información de un bloque de horario operativo previamente guardado en el sistema.
     *
     * @param horario La entidad {@link Horario} con las modificaciones requeridas y un ID válido.
     * @return La entidad modificada y guardada con éxito en el motor de persistencia.
     * @throws ServiceException Si el ID es nulo, si el horario no existe o por fallos mecánicos de la BD.
     */
    Horario update(Horario horario);

    /**
     * Filtra y lista los turnos o bloques de atención programados para un día de la semana en particular.
     *
     * @param day El día de la semana bajo el enumerado clínico {@link DiaSemana} a consultar.
     * @return {@link List} con las entidades {@link Horario} asignadas al día especificado.
     */
    List<Horario> findByDay(DiaSemana day);

    /**
     * Remueve físicamente un bloque de horario de la plataforma utilizando su identificador único.
     *
     * @param id Identificador único del registro de horario que se desea eliminar.
     * @throws ServiceException Si ocurre una excepción transaccional de acceso a datos al borrar el registro.
     */
    void deleteById(Long id);
}
