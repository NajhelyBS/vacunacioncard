package pe.edu.utp.vacunacioncard.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import java.util.Optional;

/**
 * Repositorio de la entidad CuentaUsuario que permite realizar operaciones CRUD y consultas personalizadas.
 */
public interface CuentaUsuarioRepository extends JpaRepository<CuentaUsuario, Long> {

    /**
     * Busca una cuenta de usuario por su nombre de usuario (username).
     *
     * @param username Nombre de usuario único asociado a la cuenta.
     * @return Un {@link Optional} con la {@link CuentaUsuario} si existe, o vacío en caso contrario.
     */
    Optional<CuentaUsuario> findByUsername(String username);
}