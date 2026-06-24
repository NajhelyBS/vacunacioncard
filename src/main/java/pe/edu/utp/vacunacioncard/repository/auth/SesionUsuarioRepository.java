package pe.edu.utp.vacunacioncard.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import java.util.Optional;

public interface SesionUsuarioRepository extends JpaRepository<SesionUsuario, Long> {
    Optional<SesionUsuario> findByToken(String token);
}