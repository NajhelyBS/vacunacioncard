package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    List<Paciente> listarTodos();

    Optional<Paciente> obtenerPorId(Long id);

    Paciente registrar(Paciente paciente);

    Optional<Paciente> obtenerPorDni(String dni);

    Optional<Paciente> obtenerPorHistoriaClinica(String historiaClinicaId);

    List<Paciente> listarPorEstado(boolean activo);
}
