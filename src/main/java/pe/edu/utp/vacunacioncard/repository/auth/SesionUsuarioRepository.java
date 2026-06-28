package pe.edu.utp.vacunacioncard.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import java.util.Optional;

/***
 * Repositorio de la entidad SesionUsuario que permite encontrar sesiones de usuario por su token.
 */
public interface SesionUsuarioRepository extends JpaRepository<SesionUsuario, Long> {
    Optional<SesionUsuario> findByToken(String token);
}