package pe.edu.utp.vacunacioncard.repository.vacunacion;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import java.util.List;

public interface VacunaRepository extends JpaRepository<Vacuna, Long> {
    List<Vacuna> findByDisponible(boolean disponible);
    List<Vacuna> findByLaboratorioId(Long laboratorioId);
}

