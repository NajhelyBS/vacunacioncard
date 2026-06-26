package pe.edu.utp.vacunacioncard.service.salud;

import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import java.util.List;
import java.util.Optional;

public interface IAlergiaService {

    List<Alergia> listarTodas();

    Optional<Alergia> obtenerPorId(Long id);

    Alergia registrar(Alergia alergia);

    List<Alergia> listarPorSeveridad(String severidad);

    void eliminar(Long id);
}
