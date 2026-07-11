package pe.edu.utp.vacunacioncard.repository.vacunacion;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion;
import java.util.List;

public interface EsquemaVacunacionRepository extends JpaRepository<EsquemaVacunacion, Long> {
    List<EsquemaVacunacion> findByPacienteAsignadoId(Long pacienteId);
    List<EsquemaVacunacion> findByEstadoIgnoreCase(String estado);
}
