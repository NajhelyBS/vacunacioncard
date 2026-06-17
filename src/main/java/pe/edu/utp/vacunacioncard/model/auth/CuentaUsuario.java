package pe.edu.utp.vacunacioncard.model.auth;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import java.time.ZoneId;
import java.util.UUID;

/**
 * Clase CuentaUsuario que representa la cuenta de acceso de un usuario en el sistema.
 * Gestiona credenciales, estado y seguridad del usuario.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class CuentaUsuario {
    private final String id = UUID.randomUUID().toString();
    private String username;
    private String passwordHash;
    private Usuario usuario;
    private Rol rol;
    private boolean cuentaActiva = true;
    private boolean bloqueada =  false;
    private final LocalDateTime fechaCreacion = LocalDateTime.now(ZoneId.of("America/Lima"));
    private LocalDateTime ultimoAcceso;
    private int intentosFallidos = 0;

    public CuentaUsuario(String username, String passwordHash, Usuario usuario, Rol rol) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.usuario = usuario;
        this.rol = rol;
    }
}
