package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.Laboratorio;
import java.util.List;
import java.util.Optional;

public interface ILaboratorioService {

    List<Laboratorio> listarTodos();

    Optional<Laboratorio> obtenerPorId(Long id);

    Laboratorio registrar(Laboratorio laboratorio);

    List<Laboratorio> listarPorPais(String pais);

    void eliminar(Long id);
}
