package pe.edu.utp.vacunacioncard.model.comun;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Clase RegistroAuditoria que representa el registro de auditoría de acciones en el sistema.
 * No es una entidad persistente: se usa como DTO/evento de log en memoria o enviado
 * a un sistema externo (ej. logging, mensajería).
 *
 * @author Grupo 1
 * @version 1.0
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistroAuditoria {

    private String id = UUID.randomUUID().toString();
    private Usuario usuario;
    private String accion;
    private String entidadAfectada;
    private String idEntidad;
    private String detalles;
    private LocalDateTime fechaHora = LocalDateTime.now(ZoneId.of("America/Lima"));
    private String ipAddress;

    public RegistroAuditoria(Usuario usuario, String accion, String entidadAfectada, String detalles) {
        if (usuario == null) throw new IllegalArgumentException("Usuario no puede ser nulo");
        this.usuario = usuario;
        this.accion = accion;
        this.entidadAfectada = entidadAfectada;
        this.detalles = detalles;
    }
}