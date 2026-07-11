package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion;
import pe.edu.utp.vacunacioncard.repository.vacunacion.EsquemaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.IEsquemaVacunacionService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EsquemaVacunacionServiceImpl implements IEsquemaVacunacionService {

    private final EsquemaVacunacionRepository repo;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<EsquemaVacunacion> getAll() {
        log.info("Listando todos los esquemas de vacunación");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<EsquemaVacunacion> getById(Long id) {
        log.info("Buscando esquema por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public EsquemaVacunacion create(EsquemaVacunacion esquema) {
        log.info("Creando esquema del paciente ID: {}",
                esquema.getPacienteAsignado() != null ? esquema.getPacienteAsignado().getId() : "N/A");
        try {
            EsquemaVacunacion guardado = repo.save(esquema);
            log.info("Esquema guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar esquema de vacunación", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public EsquemaVacunacion update(EsquemaVacunacion esquema) {
        log.info("Actualizando esquema ID: {}", esquema.getId());
        try {
            if (!repo.existsById(esquema.getId())) {
                throw new ServiceException("No existe el esquema con ID: " + esquema.getId(), null);
            }
            return repo.save(esquema);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar esquema ID: " + esquema.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando esquema de vacunación con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar esquema con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<EsquemaVacunacion> findByPatient(Long pacienteId) {
        log.info("Listando esquemas del paciente ID: {}", pacienteId);
        return repo.findByPacienteAsignadoId(pacienteId);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<EsquemaVacunacion> findByStatus(String estado) {
        log.info("Listando esquemas por estado: {}", estado);
        return repo.findByEstadoIgnoreCase(estado);
    }
}