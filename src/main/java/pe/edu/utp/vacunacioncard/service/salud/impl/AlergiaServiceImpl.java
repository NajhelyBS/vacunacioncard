package pe.edu.utp.vacunacioncard.service.salud.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import pe.edu.utp.vacunacioncard.repository.salud.AlergiaRepository;
import pe.edu.utp.vacunacioncard.service.salud.IAlergiaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiaServiceImpl implements IAlergiaService {

    private final AlergiaRepository repo;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Alergia> getAll() {
        log.info("Listando todas las alergias");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<Alergia> getById(Long id) {
        log.info("Buscando alergia por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Alergia create(Alergia alergia) {
        log.info("Registrando alergia: {}", alergia.getNombre());
        try {
            Alergia guardada = repo.save(alergia);
            log.info("Alergia registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar alergia: " + alergia.getNombre(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Alergia update(Alergia alergia) {
        log.info("Actualizando alergia ID: {}", alergia.getId());
        try {
            if (!repo.existsById(alergia.getId())) {
                throw new ServiceException("No existe la alergia con ID: " + alergia.getId(), null);
            }
            return repo.save(alergia);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar alergia ID: " + alergia.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando alergia con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar alergia con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Alergia> findBySeverity(String severidad) {
        log.info("Listando alergias por severidad: {}", severidad);
        return repo.findBySeveridadIgnoreCase(severidad);
    }
}
