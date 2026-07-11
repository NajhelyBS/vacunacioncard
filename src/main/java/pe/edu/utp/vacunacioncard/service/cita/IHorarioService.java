package pe.edu.utp.vacunacioncard.service.cita;


import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import java.util.List;
import java.util.Optional;


public interface IHorarioService {
    List<Horario> getAll();
    Optional<Horario> findById(Long id);
    Horario create(Horario horario);
    Horario update(Horario horario);
    List<Horario> findByDay(DiaSemana day);
    void deleteById(Long id);
}
