package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión y reglas de negocio de Cuentas de Usuario.
 */
public interface ICuentaUsuarioService {

    /**
     * Obtiene una lista con todas las cuentas de usuario registradas en el sistema.
     * @return Lista de objetos {@link CuentaUsuario}.
     */
    List<CuentaUsuario> listarTodas();

    /**
     * Recupera una cuenta de usuario específica mediante su identificador único.
     * @param id El identificador único de la cuenta.
     * @return Un {@link Optional} que contiene la cuenta si se encuentra, o vacío si no.
     */
    Optional<CuentaUsuario> buscarPorId(Long id);

    /**
     * Registra o actualiza una cuenta de usuario en la base de datos de persistencia.
     * @param cuenta El objeto cuenta con la información a persistir.
     * @return El objeto {@link CuentaUsuario} guardado con su ID generado, o null si falla.
     */
    CuentaUsuario registrar(CuentaUsuario cuenta);

    /**
     * Remueve físicamente una cuenta de usuario del sistema mediante su ID.
     * @param id El identificador único de la cuenta a eliminar.
     */
    void eliminar(Long id);
}
