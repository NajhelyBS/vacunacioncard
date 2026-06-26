package pe.edu.utp.vacunacioncard.service.salud;

import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;
import java.util.List;
import java.util.Optional;

public interface IContraindicacionService {

    List<Contraindicacion> listarTodas();

    Optional<Contraindicacion> obtenerPorId(Long id);

    Contraindicacion registrar(Contraindicacion contraindicacion);

    List<Contraindicacion> listarPorVacunaAfectada(Long vacunaId);

    void eliminar(Long id);
}
