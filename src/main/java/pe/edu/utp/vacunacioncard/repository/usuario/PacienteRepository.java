package pe.edu.utp.vacunacioncard.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import java.util.Optional;
import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByDni(String dni);
    Optional<Paciente> findByHistoriaClinicaId(String historiaClinicaId);
    List<Paciente> findByActivo(boolean activo);
}
