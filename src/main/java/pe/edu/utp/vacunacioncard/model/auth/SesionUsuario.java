package pe.edu.utp.vacunacioncard.model.auth;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase SesionUsuario que representa una sesión activa de un usuario en el sistema.
 * Controla acceso, autenticación y tiempo de uso.
 *
 * @author Grupo 1
 * @version 1.0
 */


@Getter
@Setter
@NoArgsConstructor
public class SesionUsuario {
    private String id;
    private CuentaUsuario cuenta;
    private String token;
    private LocalDateTime inicioSesion;
    private LocalDateTime expiracion;
    private String ipAddress;
    private String userAgent;
    private boolean activa;

    public SesionUsuario(CuentaUsuario cuenta, String ipAddress, String userAgent) {
        this.id = java.util.UUID.randomUUID().toString();
        this.cuenta = cuenta;
        this.token = generarToken();
        this.inicioSesion = LocalDateTime.now();
        this.expiracion = this.inicioSesion.plusHours(8);
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.activa = true;
    }

    private String generarToken() {
        return java.util.UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
    }
}
