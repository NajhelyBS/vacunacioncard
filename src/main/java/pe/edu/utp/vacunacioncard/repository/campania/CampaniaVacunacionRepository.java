package pe.edu.utp.vacunacioncard.repository.campania;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import java.util.List;

public interface CampaniaVacunacionRepository extends JpaRepository<CampaniaVacunacion, Long> {
    List<CampaniaVacunacion> findByEstado(String estado);

}