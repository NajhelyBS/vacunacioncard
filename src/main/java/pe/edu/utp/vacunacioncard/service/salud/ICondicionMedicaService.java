package pe.edu.utp.vacunacioncard.service.salud;

import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import java.util.List;
import java.util.Optional;

public interface ICondicionMedicaService {

    List<CondicionMedica> listarTodas();

    Optional<CondicionMedica> obtenerPorId(Long id);

    CondicionMedica registrar(CondicionMedica condicion);

    Optional<CondicionMedica> obtenerPorCodigoCIE10(String codigoCIE10);

    List<CondicionMedica> listarPorEstado(boolean activa);

    void eliminar(Long id);
}
