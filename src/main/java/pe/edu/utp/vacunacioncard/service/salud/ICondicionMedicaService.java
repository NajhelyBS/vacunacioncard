package pe.edu.utp.vacunacioncard.service.salud;

import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión y diagnóstico de Condiciones Médicas preexistentes.
 */
public interface ICondicionMedicaService {

    /**
     * Obtiene una lista con todas las condiciones médicas registradas en el sistema.
     * @return Lista de objetos {@link CondicionMedica}.
     */
    List<CondicionMedica> listarTodas();

    /**
     * Recupera una condición médica específica mediante su identificador único de base de datos.
     * @param id El identificador único de la condición.
     * @return Un {@link Optional} que contiene el registro si es hallado.
     */
    Optional<CondicionMedica> obtenerPorId(Long id);

    /**
     * Registra o actualiza una condición de salud en la base de datos maestra con control de errores.
     * @param condicion El objeto condición médica a guardar.
     * @return La {@link CondicionMedica} guardada con éxito, o null si ocurre un fallo.
     */
    CondicionMedica registrar(CondicionMedica condicion);

    /**
     * Busca una condición médica por su código estandarizado internacional.
     * @param codigoCIE10 Código CIE-10 a consultar.
     * @return Un {@link Optional} con el resultado.
     */
    Optional<CondicionMedica> obtenerPorCodigoCIE10(String codigoCIE10);

    /**
     * Filtra las condiciones patológicas según su estado de vigencia clínica actual.
     * @param activa Estado lógico de la condición.
     * @return Lista de objetos {@link CondicionMedica}.
     */
    List<CondicionMedica> listarPorEstado(boolean activa);
}

