package pe.edu.utp.vacunacioncard.service.cita;

import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import java.util.List;
import java.util.Optional;

public interface IHorarioService {

    List<Horario> listarTodos();

    Optional<Horario> obtenerPorId(Long id);

    Horario guardar(Horario horario);

    List<Horario> listarPorDia(DiaSemana diaSemana);

    void eliminar(Long id);
}
