package pe.edu.utp.vacunacioncard.repository.salud;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;
import java.util.List;

public interface ContraindicacionRepository extends JpaRepository<Contraindicacion, Long> {
    List<Contraindicacion> findByVacunaAfectadaId(Long vacunaId);
}

