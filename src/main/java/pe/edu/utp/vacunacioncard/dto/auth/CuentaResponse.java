package pe.edu.utp.vacunacioncard.dto.auth;

import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;

import java.time.LocalDateTime;

/**
 * Vista pública de una cuenta de usuario. Omite deliberadamente el {@code passwordHash}
 * para no exponer credenciales.
 *
 * @param id           Identificador de la cuenta.
 * @param username     Nombre de usuario.
 * @param rol          Nombre del rol asignado, o {@code null} si no tiene.
 * @param cuentaActiva Indica si la cuenta está activa.
 * @param bloqueada    Indica si la cuenta está bloqueada.
 * @param ultimoAcceso Fecha y hora del último acceso exitoso.
 */
public record CuentaResponse(
        Long id,
        String username,
        String rol,
        boolean cuentaActiva,
        boolean bloqueada,
        LocalDateTime ultimoAcceso) {

    /**
     * Construye una {@link CuentaResponse} a partir de la entidad {@link CuentaUsuario}.
     *
     * @param cuenta Entidad origen.
     * @return La representación pública de la cuenta.
     */
    public static CuentaResponse from(CuentaUsuario cuenta) {
        return new CuentaResponse(
                cuenta.getId(),
                cuenta.getUsername(),
                cuenta.getRol() != null ? cuenta.getRol().getNombre() : null,
                cuenta.isCuentaActiva(),
                cuenta.isBloqueada(),
                cuenta.getUltimoAcceso());
    }
}
