package pe.edu.utp.vacunacioncard.repository.vacunacion;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import java.util.Optional;

public interface CartillaVacunacionRepository extends JpaRepository<CartillaVacunacion, Long> {
    Optional<CartillaVacunacion> findByPacienteId(Long pacienteId);
    Optional<CartillaVacunacion> findByCodigoQR(String codigoQR);
}

