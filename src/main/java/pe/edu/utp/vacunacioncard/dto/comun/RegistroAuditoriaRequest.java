package pe.edu.utp.vacunacioncard.dto.comun;


import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;




public record RegistroAuditoriaRequest(
        Long usuarioId,
        String accion,
        String entidadAfectada,
        String idEntidad,
        String detalles) {


    public RegistroAuditoria toEntity() {
        RegistroAuditoria auditoria = new RegistroAuditoria();
        auditoria.setAccion(accion);
        auditoria.setEntidadAfectada(entidadAfectada);
        auditoria.setIdEntidad(idEntidad);
        auditoria.setDetalles(detalles);
        return auditoria;
    }
}
