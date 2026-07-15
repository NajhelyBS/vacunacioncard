package pe.edu.utp.vacunacioncard.service.campania;

import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio para la planificación, despliegue y control operativo de campañas de vacunación.
 * Gestiona el ciclo de vida de los eventos sanitarios masivos dirigidos a la población.
 */
public interface ICampaniaVacunacionService {

    /**
     * Recupera el listado general de todas las campañas de vacunación planificadas o ejecutadas en el sistema.
     *
     * @return {@link List} que contiene las entidades {@link CampaniaVacunacion}, o una lista vacía si no hay registros.
     */
    List<CampaniaVacunacion> getAll();

    /**
     * Realiza la búsqueda de una campaña de vacunación específica mediante su identificador único.
     *
     * @param id Identificador único y correlativo de la campaña en la base de datos.
     * @return Un {@link Optional} conteniendo la {@link CampaniaVacunacion} si es hallada, o {@link Optional#empty()} en caso contrario.
     */
    Optional<CampaniaVacunacion> getById(Long id);

    /**
     * Registra de manera persistente una nueva campaña de vacunación con sus respectivas metas poblacionales.
     *
     * @param campania Entidad {@link CampaniaVacunacion} con los metadatos cronológicos e institucionales a guardar.
     * @return La entidad {@link CampaniaVacunacion} persistida incluyendo su clave primaria autogenerada.
     * @throws ServiceException Si ocurre una anomalía transaccional o fallo de acceso en el motor de persistencia.
     */
    CampaniaVacunacion create(CampaniaVacunacion campania);

    /**
     * Modifica de manera integral los datos de planificación o cobertura de una campaña preexistente.
     *
     * @param campania Entidad {@link CampaniaVacunacion} con las modificaciones estructuradas y un ID válido asignado.
     * @return La entidad {@link CampaniaVacunacion} guardada con sus valores actualizados.
     * @throws ServiceException Si el identificador provisto no existe en los registros o ante fallos transaccionales de la BD.
     */
    CampaniaVacunacion update(CampaniaVacunacion campania);

    /**
     * Elimina del sistema una campaña de vacunación utilizando su identificador único.
     *
     * @param id Identificador único de la campaña a remover.
     * @throws ServiceException Si se presenta un fallo de integridad relacional en el esquema de la base de datos.
     */
    void deleteById(Long id);

    /**
     * Filtra y lista las campañas de vacunación registradas en base a su estado de gestión operativa actual.
     *
     * @param estado Estado de control (por ejemplo, "PLANEADA", "ACTIVA", "CONCLUIDA").
     * @return {@link List} conteniendo las entidades {@link CampaniaVacunacion} que coinciden con el criterio solicitado.
     */
    List<CampaniaVacunacion> findByStatus(String estado);
}
