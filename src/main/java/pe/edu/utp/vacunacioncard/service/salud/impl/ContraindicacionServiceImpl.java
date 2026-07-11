package pe.edu.utp.vacunacioncard.service.salud.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;
import pe.edu.utp.vacunacioncard.repository.salud.ContraindicacionRepository;
import pe.edu.utp.vacunacioncard.service.salud.IContraindicacionService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContraindicacionServiceImpl implements IContraindicacionService {

    private final ContraindicacionRepository repo;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Contraindicacion> getAll() {
        log.info("Listando todas las contraindicaciones");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<Contraindicacion> getById(Long id) {
        log.info("Buscando contraindicación por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Contraindicacion create(Contraindicacion contraindicacion) {
        log.info("Registrando contraindicación");
        try {
            Contraindicacion guardada = repo.save(contraindicacion);
            log.info("Contraindicación registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar contraindicación", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Contraindicacion update(Contraindicacion contraindicacion) {
        log.info("Actualizando contraindicación ID: {}", contraindicacion.getId());
        try {
            if (!repo.existsById(contraindicacion.getId())) {
                throw new ServiceException("No existe la contraindicación con ID: " + contraindicacion.getId(), null);
            }
            return repo.save(contraindicacion);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar contraindicación ID: " + contraindicacion.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando contraindicación con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar contraindicación con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Contraindicacion> findByAffectedVaccine(Long vacunaId) {
        log.info("Listando contraindicaciones de vacuna ID: {}", vacunaId);
        return repo.findByVacunaAfectadaId(vacunaId);
    }
}
