package pe.edu.utp.vacunacioncard.service.comun;


import pe.edu.utp.vacunacioncard.model.comun.Calendario;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ICalendarioService {


    Calendario create(Calendario calendario);


    Optional<Calendario> findByDate(LocalDate fecha);


    List<Calendario> findByAvailability(boolean esHabil);


    void deleteById(Long id);
}
