package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import java.util.List;
import java.util.Optional;

public interface IRegistroVacunaService {

    /** Obtiene todos los registros de dosis aplicadas. */
    List<RegistroVacuna> getAll();

    /** Busca un registro de vacuna por su identificador único. */
    Optional<RegistroVacuna> getById(Long id);

    /** Registra la aplicación de una nueva dosis. */
    RegistroVacuna create(RegistroVacuna registro);

    /** Actualiza los datos de un registro de vacuna existente. */
    RegistroVacuna update(RegistroVacuna registro);

    /**
     * Elimina un registro de vacuna del sistema.
     * Uso previsto para corrección de errores de carga (ej. dosis registrada por error).
     */
    void deleteById(Long id);

    /** Lista los registros según el lote de fabricación de la vacuna. */
    List<RegistroVacuna> findByBatch(String lote);

    /** Lista los registros aplicados por un enfermero específico. */
    List<RegistroVacuna> findByNurse(Long enfermeroId);
}