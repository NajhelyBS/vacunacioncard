package pe.edu.utp.vacunacioncard.model.comun;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Clase RegistroAuditoria que representa el registro de auditoría de acciones en el sistema.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class RegistroAuditoria {
    private String id;
    private Usuario usuario;
    private String accion;
    private String entidadAfectada;
    private String idEntidad;
    private String detalles;
    private LocalDateTime fechaHora;
    private String ipAddress;

    public RegistroAuditoria(Usuario usuario, String accion, String entidadAfectada, String detalles) {
        this.id = java.util.UUID.randomUUID().toString();
        this.usuario = usuario;
        this.accion = accion;
        this.entidadAfectada = entidadAfectada;
        this.detalles = detalles;
        this.fechaHora = LocalDateTime.now();
    }
}
