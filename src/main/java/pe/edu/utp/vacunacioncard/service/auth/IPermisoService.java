package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.Permiso;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de permisos del sistema de vacunación.
 */
public interface IPermisoService {

    /**
     * Recupera una lista con todos los permisos configurados en el sistema.
     * @return Lista de objetos {@link Permiso}.
     */
    List<Permiso> listarTodos();

    /**
     * Registra un nuevo permiso en la base de datos maestra con control de errores.
     * @param permiso El objeto permiso a guardar.
     * @return El {@link Permiso} persistido, o null si ocurre una anomalía.
     */
    Permiso guardar(Permiso permiso);

    /**
     * Busca un permiso específico mediante su código único.
     * @param codigo El código identificador del permiso.
     * @return Un {@link Optional} con el permiso si existe.
     */
    Optional<Permiso> buscarPorCodigo(String codigo);
}