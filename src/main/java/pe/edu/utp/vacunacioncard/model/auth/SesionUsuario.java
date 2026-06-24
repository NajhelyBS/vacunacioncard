package pe.edu.utp.vacunacioncard.model.auth;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
/**
 * Entidad SesionUsuario que representa la persistencia de sesiones activas.
 */
@Builder
@Entity
@Table(name = "mae_sesion_usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SesionUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    private CuentaUsuario cuenta;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "inicio_sesion")
    private final LocalDateTime inicioSesion = LocalDateTime.now(ZoneId.of("America/Lima"));

    @Column(name = "expiracion")
    private LocalDateTime expiracion;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    private boolean activa;
}