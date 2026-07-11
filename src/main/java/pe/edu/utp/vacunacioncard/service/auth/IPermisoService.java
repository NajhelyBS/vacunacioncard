package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.Permiso;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio para la gestión de permisos del sistema.
 * Define las operaciones de negocio disponibles sobre la entidad {@link Permiso}.
 */
public interface IPermisoService {

    /**
     * Obtiene todos los permisos registrados en el sistema.
     *
     * @return {@link List} con todas las entidades {@link Permiso}.
     */
    List<Permiso> getAll();

    /**
     * Registra un nuevo permiso en el sistema.
     *
     * @param permiso Entidad {@link Permiso} con los datos a persistir.
     * @return La entidad {@link Permiso} persistida con su identificador asignado.
     */
    Permiso create(Permiso permiso);

    /**
     * Busca un permiso por su código único.
     *
     * @param codigo Código identificador del permiso.
     * @return Un {@link Optional} con el {@link Permiso} si existe, o vacío en caso contrario.
     */
    Optional<Permiso> findByCode(String codigo);

    /**
     * Elimina un permiso a partir de su identificador único.
     *
     * @param id Identificador único del permiso a eliminar.
     */
    void deleteById(Long id);
}
