package pe.edu.utp.vacunacioncard.service.salud;

import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para el registro y catalogación de Alergias médicas.
 */
public interface IAlergiaService {

    /**
     * Obtiene una lista con todas las alergias registradas en el catálogo maestro.
     * @return Lista de objetos {@link Alergia}.
     */
    List<Alergia> listarTodas();

    /**
     * Recupera el registro de una alergia específica mediante su identificador único.
     * @param id El identificador único de la alergia.
     * @return Un {@link Optional} que contiene la alergia si es hallada.
     */
    Optional<Alergia> obtenerPorId(Long id);

    /**
     * Registra o actualiza una alergia en el catálogo médico con control de persistencia.
     * @param alergia El objeto alergia con las especificaciones clínicas.
     * @return La {@link Alergia} guardada con éxito, o null si ocurre un fallo.
     */
    Alergia registrar(Alergia alergia);

    /**
     * Filtra las alergias del sistema según su nivel de peligrosidad o severidad.
     * @param severidad Criterio de severidad clínica.
     * @return Lista de objetos {@link Alergia}.
     */
    List<Alergia> listarPorSeveridad(String severidad);
}
