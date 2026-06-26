package pe.edu.utp.vacunacioncard.service.comun;

import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;
import java.util.List;
import java.util.Optional;

public interface IRegistroAuditoriaService {

    RegistroAuditoria registrarAccion(RegistroAuditoria auditoria);

    List<RegistroAuditoria> listarTodos();

    Optional<RegistroAuditoria> obtenerPorId(Long id);

    List<RegistroAuditoria> listarPorUsuario(Long usuarioId);

    List<RegistroAuditoria> listarPorEntidadAfectada(String entidad);
}
