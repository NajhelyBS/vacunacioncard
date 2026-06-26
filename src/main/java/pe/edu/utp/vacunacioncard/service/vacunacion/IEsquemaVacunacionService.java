package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion;
import java.util.List;
import java.util.Optional;

public interface IEsquemaVacunacionService {

    List<EsquemaVacunacion> listarTodos();

    Optional<EsquemaVacunacion> obtenerPorId(Long id);

    EsquemaVacunacion guardar(EsquemaVacunacion esquema);

    List<EsquemaVacunacion> listarPorPaciente(Long pacienteId);

    List<EsquemaVacunacion> listarPorEstado(String estado);

    void eliminar(Long id);
}
