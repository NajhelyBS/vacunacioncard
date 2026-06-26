package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import java.util.List;
import java.util.Optional;

public interface IVacunaService {

    List<Vacuna> listarTodas();

    Optional<Vacuna> obtenerPorId(Long id);

    Vacuna registrar(Vacuna vacuna);

    List<Vacuna> listarPorDisponibilidad(boolean disponible);

    List<Vacuna> listarPorLaboratorio(Long laboratorioId);

    void eliminar(Long id);
}
