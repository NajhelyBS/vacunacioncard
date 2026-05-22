package pe.edu.utp.vacunacioncard.model.auth;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

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
    private String id;
    private String username;
    private String passwordHash;
    private Usuario usuario;
    private Rol rol;
    private boolean cuentaActiva;
    private boolean bloqueada;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimoAcceso;
    private int intentosFallidos;

    public CuentaUsuario(String username, String passwordHash, Usuario usuario, Rol rol) {
        this.id = java.util.UUID.randomUUID().toString();
        this.username = username;
        this.passwordHash = passwordHash;
        this.usuario = usuario;
        this.rol = rol;
        this.cuentaActiva = true;
        this.bloqueada = false;
        this.fechaCreacion = LocalDateTime.now();
        this.intentosFallidos = 0;
    }
}
