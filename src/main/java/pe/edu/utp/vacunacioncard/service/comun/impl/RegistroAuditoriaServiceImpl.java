package pe.edu.utp.vacunacioncard.service.comun.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;
import pe.edu.utp.vacunacioncard.repository.comun.RegistroAuditoriaRepository;
import pe.edu.utp.vacunacioncard.service.comun.IRegistroAuditoriaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistroAuditoriaServiceImpl implements IRegistroAuditoriaService {

    private final RegistroAuditoriaRepository repo;

    @Override
    @Transactional
    public RegistroAuditoria registrarAccion(RegistroAuditoria auditoria) {
        log.info("Registrando acción de auditoría: {}", auditoria.getAccion());
        try {
            RegistroAuditoria guardado = repo.save(auditoria);
            log.info("Auditoría registrada con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar auditoría: " + auditoria.getAccion(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> listarTodos() {
        log.info("Listando todos los registros de auditoría");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroAuditoria> obtenerPorId(Long id) {
        log.info("Buscando auditoría por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> listarPorUsuario(Long usuarioId) {
        log.info("Listando auditoría del usuario ID: {}", usuarioId);
        return repo.findByUsuarioId(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> listarPorEntidadAfectada(String entidad) {
        log.info("Listando auditoría por entidad: {}", entidad);
        return repo.findByEntidadAfectadaIgnoreCase(entidad);
    }
}
