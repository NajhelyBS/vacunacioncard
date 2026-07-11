package pe.edu.utp.vacunacioncard.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.auth.Rol;
import java.util.Optional;

/***
 * Repositorio de la entidad Rol que permite encontrar roles por su nombre.
 */
public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Busca un rol por su nombre único.
     *
     * @param nombre Nombre descriptivo del rol.
     * @return Un {@link Optional} con el {@link Rol} si existe, o vacío en caso contrario.
     */
    Optional<Rol> findByNombre(String nombre);
}