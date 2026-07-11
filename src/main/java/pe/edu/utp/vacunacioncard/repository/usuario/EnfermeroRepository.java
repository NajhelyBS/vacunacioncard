package pe.edu.utp.vacunacioncard.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import java.util.Optional;
import java.util.List;

public interface EnfermeroRepository extends JpaRepository<Enfermero, Long> {
    Optional<Enfermero> findByColegiatura(String colegiatura);
    List<Enfermero> findByCentroTrabajoIgnoreCase(String centroTrabajo);

}
