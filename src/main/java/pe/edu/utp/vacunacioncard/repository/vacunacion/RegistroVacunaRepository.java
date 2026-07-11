package pe.edu.utp.vacunacioncard.repository.vacunacion;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import java.util.List;

public interface RegistroVacunaRepository extends JpaRepository<RegistroVacuna, Long> {
    List<RegistroVacuna> findByLoteIgnoreCase(String lote);
    List<RegistroVacuna> findByEnfermeroAplicadorId(Long enfermeroId);
}

