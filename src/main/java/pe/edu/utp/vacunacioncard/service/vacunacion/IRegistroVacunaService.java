package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import java.util.List;
import java.util.Optional;

public interface IRegistroVacunaService {

    List<RegistroVacuna> listarTodos();

    Optional<RegistroVacuna> obtenerPorId(Long id);

    RegistroVacuna guardar(RegistroVacuna registro);

    List<RegistroVacuna> listarPorLote(String lote);

    List<RegistroVacuna> listarPorEnfermero(Long enfermeroId);
}
