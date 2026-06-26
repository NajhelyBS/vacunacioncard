package pe.edu.utp.vacunacioncard.service.campania;

import pe.edu.utp.vacunacioncard.model.campania.CentroVacunacion;
import java.util.List;
import java.util.Optional;

public interface ICentroVacunacionService {

    List<CentroVacunacion> listarTodos();

    Optional<CentroVacunacion> obtenerPorId(Long id);

    CentroVacunacion registrar(CentroVacunacion centro);

    List<CentroVacunacion> listarPorEstado(boolean activo);

    List<CentroVacunacion> buscarPorNombre(String nombre);

    void eliminar(Long id);
}
