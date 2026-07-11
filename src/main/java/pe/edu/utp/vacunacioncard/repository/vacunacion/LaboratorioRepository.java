package pe.edu.utp.vacunacioncard.repository.vacunacion;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.vacunacion.Laboratorio;
import java.util.List;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {
    List<Laboratorio> findByPaisOrigenIgnoreCase(String paisOrigen);

}
