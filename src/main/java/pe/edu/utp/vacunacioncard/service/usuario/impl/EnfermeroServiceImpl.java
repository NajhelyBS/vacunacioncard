package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.repository.usuario.EnfermeroRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IEnfermeroService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnfermeroServiceImpl implements IEnfermeroService {

    private final EnfermeroRepository repo;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Enfermero> getAll() {
        log.info("Listando todos los enfermeros");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<Enfermero> getById(Long id) {
        log.info("Buscando enfermero por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Enfermero create(Enfermero enfermero) {
        log.info("Registrando enfermero con colegiatura: {}", enfermero.getColegiatura());
        try {
            Enfermero guardado = repo.save(enfermero);
            log.info("Enfermero registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar enfermero", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Enfermero update(Enfermero enfermero) {
        log.info("Actualizando enfermero ID: {}", enfermero.getId());
        try {
            if (!repo.existsById(enfermero.getId())) {
                throw new ServiceException("No existe el enfermero con ID: " + enfermero.getId(), null);
            }
            return repo.save(enfermero);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar enfermero ID: " + enfermero.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando enfermero con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar enfermero con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<Enfermero> findByColegiatura(String colegiatura) {
        log.info("Buscando enfermero por colegiatura: {}", colegiatura);
        return repo.findByColegiatura(colegiatura);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Enfermero> findByCentroTrabajo(String centroTrabajo) {
        log.info("Listando enfermeros por centro: {}", centroTrabajo);
        return repo.findByCentroTrabajoIgnoreCase(centroTrabajo);
    }
}