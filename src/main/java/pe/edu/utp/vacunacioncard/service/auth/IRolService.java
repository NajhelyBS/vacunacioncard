package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.Rol;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio para la gestión de roles de usuario.
 * Define las operaciones de negocio disponibles sobre la entidad {@link Rol}.
 */
public interface IRolService {

    /**
     * Obtiene todos los roles registrados en el sistema.
     *
     * @return {@link List} con todas las entidades {@link Rol}.
     */
    List<Rol> getAll();

    /**
     * Registra un nuevo rol en el sistema.
     *
     * @param rol Entidad {@link Rol} con los datos a persistir.
     * @return La entidad {@link Rol} persistida con su identificador asignado.
     */
    Rol create(Rol rol);

    /**
     * Busca un rol por su nombre único.
     *
     * @param nombre Nombre descriptivo del rol.
     * @return Un {@link Optional} con el {@link Rol} si existe, o vacío en caso contrario.
     */
    Optional<Rol> findByName(String nombre);

    /**
     * Elimina un rol a partir de su identificador único.
     *
     * @param id Identificador único del rol a eliminar.
     */
    void deleteById(Long id);
}
