package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.Rol;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión y asignación de Roles del sistema.
 */
public interface IRolService {

    /**
     * Recupera una lista con todos los roles registrados en el sistema.
     * @return Lista de objetos {@link Rol}.
     */
    List<Rol> listarTodo();

    /**
     * Registra un nuevo rol o actualiza uno existente con control transaccional.
     * @param rol El objeto rol a persistir.
     * @return El {@link Rol} guardado con éxito, o null si falla.
     */
    Rol guardar(Rol rol);

    /**
     * Recupera un perfil de acceso o rol filtrado por su nombre único.
     * @param nombre El nombre identificador del rol (ej: ADMINISTRADOR, PACIENTE).
     * @return Un {@link Optional} que contiene el rol si es hallado.
     */
    Optional<Rol> buscarPorNombre(String nombre);
}
