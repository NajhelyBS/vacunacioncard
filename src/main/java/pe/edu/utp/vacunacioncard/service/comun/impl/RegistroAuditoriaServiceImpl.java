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
 * Proporciona la lógica de negocio para capturar, almacenar y consultar las operaciones,
 * accesos y modificaciones críticas realizadas por los usuarios en la plataforma.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegistroAuditoriaServiceImpl implements IRegistroAuditoriaService {

    private final RegistroAuditoriaRepository repo;

    /**
     * Registra y persiste una nueva acción de auditoría en la base de datos.
     *
     * @param auditoria La entidad {@link RegistroAuditoria} con los datos de la acción ejecutada.
     * @return La entidad guardada con su respectivo identificador autogenerado.
     */
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

    /**
     * Obtiene el historial completo de todas las entradas de auditoría del sistema.
     *
     * @return Una lista con la totalidad de entidades {@link RegistroAuditoria} guardadas.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> getAll() {
        log.info("Listando todos los registros de auditoría");
        return repo.findAll();
    }

    /**
     * Busca una entrada específica de auditoría a través de su identificador único.
     *
     * @param id El identificador único del registro de auditoría.
     * @return Un contenedor {@link Optional} que contiene la auditoría si es hallada, o vacío si no.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroAuditoria> findById(Long id) {
        log.info("Buscando auditoría por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Recupera todas las acciones de auditoría generadas por un usuario específico.
     *
     * @param usuarioId El identificador único del usuario consultado.
     * @return Una lista de {@link RegistroAuditoria} vinculadas al usuario indicado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> findByUser(Long usuarioId) {
        log.info("Listando auditoría del usuario ID: {}", usuarioId);
        return repo.findByUsuarioId(usuarioId);
    }

    /**
     * Filtra los registros de auditoría basándose en el nombre de la entidad que fue afectada por la acción.
     * Ignora las diferencias entre mayúsculas y minúsculas durante la búsqueda.
     *
     * @param entidad El nombre de la tabla o entidad (ej. "CitaVacunacion", "Alergia") consultada.
     * @return Una lista de {@link RegistroAuditoria} asociadas al componente clínico o del sistema indicado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> findByAffectedEntity(String entidad) {
        log.info("Listando auditoría por entidad: {}", entidad);
        return repo.findByEntidadAfectadaIgnoreCase(entidad);
    }
}
