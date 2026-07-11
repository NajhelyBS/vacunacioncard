package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion;
import java.util.List;
import java.util.Optional;

public interface IEsquemaVacunacionService {

    /** Obtiene todos los esquemas de vacunación registrados. */
    List<EsquemaVacunacion> getAll();

    /** Busca un esquema de vacunación por su identificador único. */
    Optional<EsquemaVacunacion> getById(Long id);

    /** Registra un nuevo esquema de vacunación. */
    EsquemaVacunacion create(EsquemaVacunacion esquema);

    /** Actualiza los datos de un esquema existente. */
    EsquemaVacunacion update(EsquemaVacunacion esquema);

    /** Elimina un esquema de vacunación del sistema. */
    void deleteById(Long id);

    /** Lista los esquemas asignados a un paciente. */
    List<EsquemaVacunacion> findByPatient(Long pacienteId);

    /** Lista los esquemas según su estado clínico. */
    List<EsquemaVacunacion> findByStatus(String estado);
}