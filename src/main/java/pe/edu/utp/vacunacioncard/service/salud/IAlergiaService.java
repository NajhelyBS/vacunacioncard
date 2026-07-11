package pe.edu.utp.vacunacioncard.service.salud;

import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import java.util.List;
import java.util.Optional;

public interface IAlergiaService {

    /** Obtiene todas las alergias catalogadas. */
    List<Alergia> getAll();

    /** Busca una alergia por su identificador único. */
    Optional<Alergia> getById(Long id);

    /** Registra una nueva alergia. */
    Alergia create(Alergia alergia);

    /** Actualiza los datos de una alergia existente. */
    Alergia update(Alergia alergia);

    /** Elimina una alergia del sistema. */
    void deleteById(Long id);

    /** Lista las alergias según su nivel de severidad. */
    List<Alergia> findBySeverity(String severidad);
}