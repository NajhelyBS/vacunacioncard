package pe.edu.utp.vacunacioncard.service.comun.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceExceptionHandler;
import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;
import pe.edu.utp.vacunacioncard.repository.comun.RegistroAuditoriaRepository;
import pe.edu.utp.vacunacioncard.service.comun.IRegistroAuditoriaService;


import java.util.List;
import java.util.Optional;


/**
 * Implementación del servicio para la gestión de registros de auditoría del sistema.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegistroAuditoriaServiceImpl implements IRegistroAuditoriaService {


    private final RegistroAuditoriaRepository repo;


    @Override
    @Transactional
    public RegistroAuditoria create(RegistroAuditoria auditoria) {
        log.info("Registrando acción de auditoría: {}", auditoria.getAccion());
        RegistroAuditoria guardado = ServiceExceptionHandler.execute(
                () -> repo.save(auditoria),
                "Error al registrar auditoría: " + auditoria.getAccion()
        );
        log.info("Auditoría registrada con ID: {}", guardado.getId());
        return guardado;
    }


    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> getAll() {
        log.info("Listando todos los registros de auditoría");
        return repo.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroAuditoria> findById(Long id) {
        log.info("Buscando auditoría por ID: {}", id);
        return repo.findById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> findByUser(Long usuarioId) {
        log.info("Listando auditoría del usuario ID: {}", usuarioId);
        return repo.findByUsuarioId(usuarioId);
    }


    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> findByAffectedEntity(String entidad) {
        log.info("Listando auditoría por entidad: {}", entidad);
        return repo.findByEntidadAfectadaIgnoreCase(entidad);
    }
}

