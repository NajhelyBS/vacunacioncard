package pe.edu.utp.vacunacioncard.service.campania;

import pe.edu.utp.vacunacioncard.model.campania.CentroVacunacion;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio para la gestión y administración operativa de los centros de vacunación.
 * Centraliza la administración de capacidades de atención, disponibilidad de personal y estados de sedes.
 */
public interface ICentroVacunacionService {

    /**
     * Recupera el listado completo de todos los centros de vacunación registrados en el sistema.
     *
     * @return {@link List} que contiene las entidades {@link CentroVacunacion}, o una lista vacía si no hay registros.
     */
    List<CentroVacunacion> getAll();

    /**
     * Realiza la búsqueda de un establecimiento de salud mediante su identificador único.
     *
     * @param id Identificador único y correlativo del centro de vacunación.
     * @return Un {@link Optional} conteniendo el {@link CentroVacunacion} si es hallado, o {@link Optional#empty()} en caso contrario.
     */
    Optional<CentroVacunacion> getById(Long id);

    /**
     * Registra de manera persistente un nuevo establecimiento de vacunación en la plataforma.
     *
     * @param centro Entidad {@link CentroVacunacion} con las capacidades e información geográfica a guardar.
     * @return La entidad {@link CentroVacunacion} persistida incluyendo su clave primaria autogenerada.
     * @throws ServiceException Si ocurre una anomalía transaccional o fallo de acceso en el motor de persistencia.
     */
    CentroVacunacion create(CentroVacunacion centro);

    /**
     * Modifica de manera integral los datos operativos o logísticos de un centro preexistente.
     *
     * @param centro Entidad {@link CentroVacunacion} con las modificaciones estructuradas y un ID válido asignado.
     * @return La entidad {@link CentroVacunacion} guardada con sus valores actualizados.
     * @throws ServiceException Si el identificador provisto no existe en los registros o ante fallos transaccionales de la BD.
     */
    CentroVacunacion update(CentroVacunacion centro);

    /**
     * Elimina del sistema un centro de vacunación utilizando su identificador único.
     *
     * @param id Identificador único del centro a remover.
     * @throws ServiceException Si se presenta un fallo de integridad relacional en el esquema de la base de datos.
     */
    void deleteById(Long id);

    /**
     * Filtra y lista los centros de vacunación registrados según su disponibilidad operativa actual.
     *
     * @param activo Valor booleano; {@code true} para sedes operativas, {@code false} para sedes inactivas o clausuradas.
     * @return {@link List} conteniendo las entidades {@link CentroVacunacion} que coinciden con el estado solicitado.
     */
    List<CentroVacunacion> findByStatus(boolean activo);

    /**
     * Realiza una búsqueda predictiva y filtrada de centros de vacunación cuyos nombres coincidan o contengan
     * la cadena proporcionada, ejecutando una comparación insensible a mayúsculas y minúsculas.
     *
     * @param nombre Cadena de texto representativa o parcial del nombre del establecimiento.
     * @return {@link List} con los centros de vacunación que cumplan con el criterio de coincidencia.
     */
    List<CentroVacunacion> searchByName(String nombre);
}
