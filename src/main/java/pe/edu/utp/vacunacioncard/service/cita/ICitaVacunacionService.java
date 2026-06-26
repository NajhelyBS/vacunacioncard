package pe.edu.utp.vacunacioncard.service.cita;

import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import java.util.List;
import java.util.Optional;

public interface ICitaVacunacionService {

    List<CitaVacunacion> listarTodas();

    Optional<CitaVacunacion> obtenerPorId(Long id);

    CitaVacunacion programar(CitaVacunacion cita);

    List<CitaVacunacion> listarPorPaciente(Long pacienteId);

    List<CitaVacunacion> listarPorEstado(String estado);

    void eliminar(Long id);
}
