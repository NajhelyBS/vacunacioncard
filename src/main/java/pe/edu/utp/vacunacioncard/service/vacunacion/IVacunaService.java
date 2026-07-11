package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import java.util.List;
import java.util.Optional;

public interface IVacunaService {

    /** Obtiene todas las vacunas del catálogo. */
    List<Vacuna> getAll();

    /** Busca una vacuna por su identificador único. */
    Optional<Vacuna> getById(Long id);

    /** Registra una nueva vacuna en el catálogo. */
    Vacuna create(Vacuna vacuna);

    /** Actualiza los datos de una vacuna existente. */
    Vacuna update(Vacuna vacuna);

    /** Elimina una vacuna del catálogo. */
    void deleteById(Long id);

    /** Filtra las vacunas según su disponibilidad en stock. */
    List<Vacuna> findByAvailability(boolean disponible);

    /** Lista las vacunas producidas por un laboratorio específico. */
    List<Vacuna> findByLaboratory(Long laboratorioId);
}
