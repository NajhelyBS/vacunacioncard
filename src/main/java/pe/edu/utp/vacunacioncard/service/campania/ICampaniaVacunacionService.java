package pe.edu.utp.vacunacioncard.service.campania;

import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import java.util.List;
import java.util.Optional;

public interface ICampaniaVacunacionService {

    List<CampaniaVacunacion> listarTodas();

    Optional<CampaniaVacunacion> obtenerPorId(Long id);

    CampaniaVacunacion registrar(CampaniaVacunacion campania);

    List<CampaniaVacunacion> listarPorEstado(String estado);

    void eliminar(Long id);
}
