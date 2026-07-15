package pe.edu.utp.vacunacioncard.service.salud;

import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio para la gestión de restricciones y contraindicaciones clínicas.
 * Controla las advertencias médicas y criterios de exclusión asociados a la aplicación de dosis.
 */
public interface IContraindicacionService {

    /**
     * Obtiene el catálogo histórico de todas las contraindicaciones registradas en el ecosistema.
     *
     * @return {@link List} conteniendo las entidades {@link Contraindicacion}, o una lista vacía si no hay registros.
     */
    List<Contraindicacion> getAll();

    /**
     * Realiza la búsqueda de una contraindicación médica mediante su identificador único.
     *
     * @param id Identificador único y correlativo de la contraindicación.
     * @return Un {@link Optional} con la {@link Contraindicacion} si es hallada, o {@link Optional#empty()} en caso contrario.
     */
    Optional<Contraindicacion> getById(Long id);

    /**
     * Registra de manera persistente una nueva contraindicación o restricción en el sistema.
     *
     * @param contraindicacion Entidad {@link Contraindicacion} con los metadatos clínicos a resguardar.
     * @return La entidad {@link Contraindicacion} persistida incluyendo su clave primaria autogenerada.
     * @throws ServiceException Si ocurre una anomalía transaccional o fallo de acceso en la base de datos.
     */
    Contraindicacion create(Contraindicacion contraindicacion);

    /**
     * Modifica de manera integral los datos de una restricción médica preexistente.
     *
     * @param contraindicacion Entidad {@link Contraindicacion} con los cambios estructurados y un ID válido asignado.
     * @return La entidad {@link Contraindicacion} guardada con sus valores actualizados.
     * @throws ServiceException Si el identificador no existe en los registros o ante fallos mecánicos de la BD.
     */
    Contraindicacion update(Contraindicacion contraindicacion);

    /**
     * Elimina del sistema una contraindicación médica utilizando su identificador único.
     *
     * @param id Identificador único de la contraindicación a remover.
     * @throws ServiceException Si se presenta un fallo de integridad relacional en las tablas de la BD.
     */
    void deleteById(Long id);

    /**
     * Filtra y lista de manera específica todas las contraindicaciones médicas que impactan
     * o impiden la inoculación de una vacuna en particular.
     *
     * @param vacunaId Identificador único de la vacuna bajo evaluación clínica.
     * @return {@link List} conteniendo las entidades {@link Contraindicacion} asociadas a dicha vacuna.
     */
    List<Contraindicacion> findByAffectedVaccine(Long vacunaId);
}
