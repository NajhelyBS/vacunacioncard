package pe.edu.utp.vacunacioncard.model.auth;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Entidad CuentaUsuario que representa la cuenta de usuario en el sistema de vacunación.
 */
@Builder
@Entity
@Table(name = "mae_cuenta_usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CuentaUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; 

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol; 

    @Builder.Default 
    private boolean cuentaActiva = true;

    @Builder.Default
    private boolean bloqueada = false;

    @Builder.Default
    private final LocalDateTime fechaCreacion = LocalDateTime.now(ZoneId.of("America/Lima"));

    private LocalDateTime ultimoAcceso;

    @Builder.Default
    private int intentosFallidos = 0;
}