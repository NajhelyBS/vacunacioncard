package pe.edu.utp.vacunacioncard.dto.comun;


import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;
import java.time.LocalDateTime;


public record RegistroAuditoriaResponse(
        Long id,
        Long usuarioId,
        String usuarioNombre,
        String accion,
        String entidadAfectada,
        String idEntidad,
        String detalles,
        LocalDateTime fechaHora,
        String ipAddress) {


    public static RegistroAuditoriaResponse from(RegistroAuditoria r) {
        return new RegistroAuditoriaResponse(
                r.getId(),
                r.getUsuario() != null ? r.getUsuario().getId() : null,
                r.getUsuario() != null ? r.getUsuario().getNombreCompleto() : null,
                r.getAccion(),
                r.getEntidadAfectada(),
                r.getIdEntidad(),
                r.getDetalles(),
                r.getFechaHora(),
                r.getIpAddress());
    }
}

