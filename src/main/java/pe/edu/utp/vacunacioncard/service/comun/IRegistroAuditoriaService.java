package pe.edu.utp.vacunacioncard.service.comun;


import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;
import java.util.List;
import java.util.Optional;


public interface IRegistroAuditoriaService {


    RegistroAuditoria create(RegistroAuditoria auditoria);


    List<RegistroAuditoria> getAll();


    Optional<RegistroAuditoria> findById(Long id);


    List<RegistroAuditoria> findByUser(Long usuarioId);


    List<RegistroAuditoria> findByAffectedEntity(String entidad);
}
