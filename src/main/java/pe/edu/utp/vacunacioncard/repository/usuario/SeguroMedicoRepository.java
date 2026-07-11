package pe.edu.utp.vacunacioncard.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.usuario.SeguroMedico;
import java.util.Optional;

public interface SeguroMedicoRepository extends JpaRepository<SeguroMedico, Long> {
    Optional<SeguroMedico> findByNumeroPoliza(String numeroPoliza);
}

