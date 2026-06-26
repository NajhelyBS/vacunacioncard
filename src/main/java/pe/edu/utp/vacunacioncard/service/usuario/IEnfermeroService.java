package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import java.util.List;
import java.util.Optional;

public interface IEnfermeroService {

    List<Enfermero> listarTodos();

    Optional<Enfermero> obtenerPorId(Long id);

    Enfermero registrar(Enfermero enfermero);

    Optional<Enfermero> obtenerPorColegiatura(String colegiatura);

    List<Enfermero> listarPorCentroTrabajo(String centroTrabajo);
}
