package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import java.util.Optional;

/**
 * Contrato del servicio para el control de sesiones de usuario.
 * Define el ciclo de vida de las sesiones y la validación de bloqueo de cuentas.
 */
public interface ISesionUsuarioService {

    /**
     * Crea y registra una nueva sesión de usuario en el sistema.
     *
     * @param sesion Entidad {@link SesionUsuario} con los datos de autenticación y token.
     * @return La entidad {@link SesionUsuario} persistida con su identificador generado.
     */
    SesionUsuario createSession(SesionUsuario sesion);

    /**
     * Recupera una sesión a partir de su token de autenticación.
     *
     * @param token Cadena única que identifica la sesión (por ejemplo, JWT o UUID).
     * @return Un {@link Optional} con la {@link SesionUsuario} si el token existe, o vacío en caso contrario.
     */
    Optional<SesionUsuario> findByToken(String token);

    /**
     * Cierra (invalida) una sesión de usuario marcándola como inactiva.
     *
     * @param id Identificador único de la sesión a cerrar.
     */
    void closeSession(Long id);

    /**
     * Determina si una cuenta debe bloquearse según los intentos fallidos acumulados
     * y el límite configurado en el sistema.
     *
     * @param intentosFallidos Cantidad de intentos de inicio de sesión erróneos.
     * @return {@code true} si se alcanza o supera el límite permitido; {@code false} en caso contrario.
     */
    boolean isAccountLocked(int intentosFallidos);
}
