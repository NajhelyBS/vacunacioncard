package pe.edu.utp.vacunacioncard.service.comun;

import pe.edu.utp.vacunacioncard.model.comun.Calendario;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICalendarioService {

    Calendario guardarDia(Calendario calendario);

    Optional<Calendario> obtenerPorFecha(LocalDate fecha);

    List<Calendario> listarPorDisponibilidad(boolean esHabil);

    void eliminar(Long id);
}
