package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import java.util.List;
import java.util.Optional;

public interface IEnfermeroService {

    /** Obtiene todos los enfermeros registrados. */
    List<Enfermero> getAll();

    /** Busca un enfermero por su identificador único. */
    Optional<Enfermero> getById(Long id);

    /** Registra un nuevo enfermero en el sistema. */
    Enfermero create(Enfermero enfermero);

    /** Actualiza los datos de un enfermero existente. */
    Enfermero update(Enfermero enfermero);

    /** Elimina un enfermero del sistema. */
    void deleteById(Long id);

    /** Busca un enfermero por su número de colegiatura. */
    Optional<Enfermero> findByColegiatura(String colegiatura);

    /** Lista los enfermeros asignados a un centro de trabajo. */
    List<Enfermero> findByCentroTrabajo(String centroTrabajo);
}