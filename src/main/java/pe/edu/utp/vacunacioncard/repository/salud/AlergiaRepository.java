package pe.edu.utp.vacunacioncard.repository.salud;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import java.util.List;

public interface AlergiaRepository extends JpaRepository<Alergia, Long> {
    List<Alergia> findBySeveridadIgnoreCase(String severidad);
}

