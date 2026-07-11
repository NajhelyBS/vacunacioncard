package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import pe.edu.utp.vacunacioncard.repository.vacunacion.VacunaRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.IVacunaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacunaServiceImpl implements IVacunaService {

    private final VacunaRepository repo;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> getAll() {
        log.info("Listando todas las vacunas");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vacuna> getById(Long id) {
        log.info("Buscando vacuna por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Vacuna create(Vacuna vacuna) {
        log.info("Registrando vacuna: {}", vacuna.getNombre());
        try {
            Vacuna guardada = repo.save(vacuna);
            log.info("Vacuna registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar vacuna: " + vacuna.getNombre(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Vacuna update(Vacuna vacuna) {
        log.info("Actualizando vacuna ID: {}", vacuna.getId());
        try {
            if (!repo.existsById(vacuna.getId())) {
                throw new ServiceException("No existe la vacuna con ID: " + vacuna.getId(), null);
            }
            return repo.save(vacuna);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar vacuna ID: " + vacuna.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando vacuna con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar vacuna con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> findByAvailability(boolean disponible) {
        log.info("Listando vacunas por disponibilidad: {}", disponible);
        return repo.findByDisponible(disponible);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> findByLaboratory(Long laboratorioId) {
        log.info("Listando vacunas del laboratorio ID: {}", laboratorioId);
        return repo.findByLaboratorioId(laboratorioId);
    }
}