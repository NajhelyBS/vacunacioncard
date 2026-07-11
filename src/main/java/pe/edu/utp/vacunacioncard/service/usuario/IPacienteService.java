package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    /** Obtiene todos los pacientes registrados. */
    List<Paciente> getAll();

    /** Busca un paciente por su identificador único. */
    Optional<Paciente> getById(Long id);

    /** Registra un nuevo paciente en el sistema. */
    Paciente create(Paciente paciente);

    /** Actualiza los datos de un paciente existente. */
    Paciente update(Paciente paciente);

    /**
     * Elimina físicamente un paciente del sistema.
     */
    void deleteById(Long id);

    /** Busca un paciente por su DNI. */
    Optional<Paciente> findByDni(String dni);

    /** Busca un paciente por el ID de su historia clínica. */
    Optional<Paciente> findByHistoriaClinicaId(String historiaClinicaId);

    /** Lista los pacientes filtrados por su estado activo/inactivo. */
    List<Paciente> findByStatus(boolean activo);
}
