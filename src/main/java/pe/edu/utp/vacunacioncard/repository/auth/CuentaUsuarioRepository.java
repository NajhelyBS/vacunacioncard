package pe.edu.utp.vacunacioncard.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import java.util.Optional;

/**
 * Repositorio de la entidad CuentaUsuario que permite realizar operaciones CRUD y consultas personalizadas.
 */
public interface CuentaUsuarioRepository extends JpaRepository<CuentaUsuario, Long> {
    Optional<CuentaUsuario> findByUsername(String username);
}