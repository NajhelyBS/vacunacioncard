package pe.edu.utp.vacunacioncard.repository.salud;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import java.util.List;
import java.util.Optional;

public interface CondicionMedicaRepository extends JpaRepository<CondicionMedica, Long> {
    Optional<CondicionMedica> findByCodigoCIE10IgnoreCase(String codigoCIE10);
    List<CondicionMedica> findByActiva(boolean activa);
}
