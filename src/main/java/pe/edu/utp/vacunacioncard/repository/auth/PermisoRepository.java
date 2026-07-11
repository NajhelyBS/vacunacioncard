package pe.edu.utp.vacunacioncard.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.auth.Permiso;
import java.util.Optional;

/***
 * Repositorio de la entidad Permiso que permite realizar operaciones CRUD y consultas personalizadas.
 */
public interface PermisoRepository extends JpaRepository<Permiso, Long> {

    /**
     * Busca un permiso por su código único.
     *
     * @param codigo Código identificador del permiso.
     * @return Un {@link Optional} con el {@link Permiso} si existe, o vacío en caso contrario.
     */
    Optional<Permiso> findByCodigo(String codigo);
}

