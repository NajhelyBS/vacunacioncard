package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import java.util.Optional;

/**
 * Interfaz de servicio para el control y ciclo de vida de las Sesiones de Usuario.
 */
public interface ISesionUsuarioService {

    /**
     * Registra una nueva sesión en el sistema al iniciar sesión.
     * @param sesion El objeto sesión a persistir.
     * @return El objeto {@link SesionUsuario} persistido, o null si ocurre un fallo.
     */
    SesionUsuario crearSesion(SesionUsuario sesion);

    /**
     * Busca una sesión activa o histórica mediante su token único de acceso.
     * @param token El token JWT o de sesión.
     * @return Un {@link Optional} que contiene la sesión si existe.
     */
    Optional<SesionUsuario> obtenerPorToken(String token);

    /**
     * Inactiva una sesión del sistema cambiando su estado a falso (Logout).
     * @param id El identificador único de la sesión a cerrar.
     */
    void cerrarSesion(Long id);

    /**
     * Verifica si la cuenta del usuario debe ser bloqueada según el número de intentos fallidos.
     */
    boolean verificarBloqueoCuenta(int intentosFallidos);
}
