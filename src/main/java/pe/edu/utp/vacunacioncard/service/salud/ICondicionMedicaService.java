package pe.edu.utp.vacunacioncard.service.salud;

import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio para la gestión y auditoría clínica de condiciones médicas.
 * Centraliza la lógica de negocio para el control de patologías, códigos internacionales CIE-10
 * y estados de actividad de los pacientes.
 */
public interface ICondicionMedicaService {

    /**
     * Recupera la totalidad de las condiciones médicas registradas en la plataforma.
     *
     * @return {@link List} con las entidades {@link CondicionMedica} mapeadas, o una lista vacía si no existen registros.
     */
    List<CondicionMedica> getAll();

    /**
     * Realiza la búsqueda de una patología o condición médica a través de su identificador único.
     *
     * @param id Identificador único y correlativo de la condición médica.
     * @return Un {@link Optional} conteniendo la {@link CondicionMedica} hallada, o {@link Optional#empty()} si no existe.
     */
    Optional<CondicionMedica> getById(Long id);

    /**
     * Registra de manera persistente una nueva condición médica con sus metadatos clínicos.
     *
     * @param condicion Entidad {@link CondicionMedica} a ser almacenada en el repositorio.
     * @return La entidad {@link CondicionMedica} persistida con su identificador transaccional asignado.
     * @throws ServiceException Si ocurre una anomalía o fallo en la comunicación con el motor de base de datos.
     */
    CondicionMedica create(CondicionMedica condicion);

    /**
     * Actualiza de forma integral los valores de una condición médica previamente registrada.
     *
     * @param condicion Entidad {@link CondicionMedica} con las modificaciones estructuradas y un ID válido.
     * @return La entidad {@link CondicionMedica} resguardada con los nuevos cambios.
     * @throws ServiceException Si el identificador provisto no existe en el sistema o ante fallos de persistencia.
     */
    CondicionMedica update(CondicionMedica condicion);

    /**
     * Elimina del sistema una condición médica mediante su identificador único.
     *
     * @param id Identificador único de la condición médica a dar de baja.
     * @throws ServiceException Si se presenta un fallo de integridad o restricción transaccional en la BD.
     */
    void deleteById(Long id);

    /**
     * Busca de manera específica una patología clínica utilizando su codificación internacional estándar CIE-10,
     * ignorando la presencia de mayúsculas o minúsculas.
     *
     * @param codigoCIE10 Cadena de texto representativa del código CIE-10 (ej. "E11").
     * @return Un {@link Optional} conteniendo la entidad {@link CondicionMedica} si coincide, o {@link Optional#empty()} en caso contrario.
     */
    Optional<CondicionMedica> findByIcd10Code(String codigoCIE10);

    /**
     * Filtra y lista las condiciones médicas vigentes o inactivas registradas dentro del sistema.
     *
     * @param activa Valor booleano; {@code true} para condiciones activas, {@code false} para inactivas.
     * @return {@link List} con las entidades {@link CondicionMedica} que coincidan con el estado solicitado.
     */
    List<CondicionMedica> findByStatus(boolean activa);
}
