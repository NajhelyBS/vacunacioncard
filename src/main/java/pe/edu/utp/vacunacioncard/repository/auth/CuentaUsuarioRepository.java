package pe.edu.utp.vacunacioncard.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import java.util.Optional;

public interface CuentaUsuarioRepository extends JpaRepository<CuentaUsuario, Long> {
    Optional<CuentaUsuario> findByUsername(String username);
}