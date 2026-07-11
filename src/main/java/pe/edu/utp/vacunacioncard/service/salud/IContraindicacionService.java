package pe.edu.utp.vacunacioncard.service.salud;

import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;
import java.util.List;
import java.util.Optional;

public interface IContraindicacionService {

    /** Obtiene todas las contraindicaciones registradas. */
    List<Contraindicacion> getAll();

    /** Busca una contraindicación por su identificador único. */
    Optional<Contraindicacion> getById(Long id);

    /** Registra una nueva contraindicación. */
    Contraindicacion create(Contraindicacion contraindicacion);

    /** Actualiza los datos de una contraindicación existente. */
    Contraindicacion update(Contraindicacion contraindicacion);

    /** Elimina una contraindicación del sistema. */
    void deleteById(Long id);

    /** Lista las contraindicaciones que afectan a una vacuna específica. */
    List<Contraindicacion> findByAffectedVaccine(Long vacunaId);
}