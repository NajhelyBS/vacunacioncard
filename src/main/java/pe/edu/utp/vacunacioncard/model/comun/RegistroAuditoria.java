package pe.edu.utp.vacunacioncard.model.comun;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Clase RegistroAuditoria que representa el registro de auditoria de acciones en el sistema.
 *
 */

@Builder
@Entity
@Table(name = "mae_registro_auditoria")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistroAuditoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "accion", nullable = false)
    private String accion;

    @Column(name = "entidad_afectada")
    private String entidadAfectada;

    @Column(name = "id_entidad")
    private String idEntidad;

    @Column(name = "detalles")
    private String detalles;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

}