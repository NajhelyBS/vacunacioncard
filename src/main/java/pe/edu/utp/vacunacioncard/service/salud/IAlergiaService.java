package pe.edu.utp.vacunacioncard.service.salud;

import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio para la gestión del catálogo de alergias médicas.
 * Proporciona las operaciones de persistencia, consulta y filtrado clínico de severidades.
 */
public interface IAlergiaService {

    /**
     * Obtiene el catálogo completo de todas las alergias registradas en el ecosistema.
     *
     * @return {@link List} que contiene las entidades {@link Alergia} mapeadas, o una lista vacía si no hay registros.
     */
    List<Alergia> getAll();

    /**
     * Busca de forma exhaustiva una alergia mediante su identificador único.
     *
     * @param id Identificador único correlativo de la alergia.
     * @return Un {@link Optional} que contiene la {@link Alergia} si es hallada, o {@link Optional#empty()} en caso contrario.
     */
    Optional<Alergia> getById(Long id);

    /**
     * Registra de manera persistente una nueva alergia dentro del sistema médico.
     *
     * @param alergia Entidad {@link Alergia} con los datos clínicos obligatorios a guardar.
     * @return La entidad {@link Alergia} persistida incluyendo su identificador autogenerado.
     * @throws ServiceException Si ocurre una anomalía o fallo en el motor de persistencia de datos.
     */
    Alergia create(Alergia alergia);

    /**
     * Modifica de manera integral los datos de una alergia previamente existente en el sistema.
     *
     * @param alergia Entidad {@link Alergia} con los cambios estructurados y un ID válido asignado.
     * @return La entidad {@link Alergia} actualizada y resguardada.
     * @throws ServiceException Si el identificador no corresponde a ninguna entidad registrada o si ocurre un fallo en cascada en la BD.
     */
    Alergia update(Alergia alergia);

    /**
     * Elimina de forma lógica o física una alergia del sistema utilizando su identificador.
     *
     * @param id Identificador único de la alergia a dar de baja.
     * @throws ServiceException Si ocurre una denegación de acceso a nivel transaccional o fallo de base de datos.
     */
    void deleteById(Long id);

    /**
     * Filtra y lista las alergias registradas en base a su nivel de severidad determinado,
     * realizando una comparación insensible a mayúsculas y minúsculas.
     *
     * @param severidad Nivel de criticidad clínica (por ejemplo, "ALTA", "MEDIA", "BAJA").
     * @return {@link List} con las entidades {@link Alergia} que cumplen con el parámetro de severidad.
     */
    List<Alergia> findBySeverity(String severidad);
}
