package pe.edu.utp.vacunacioncard.service.salud.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import pe.edu.utp.vacunacioncard.repository.salud.CondicionMedicaRepository;
import pe.edu.utp.vacunacioncard.service.salud.ICondicionMedicaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CondicionMedicaServiceImpl implements ICondicionMedicaService {

    private final CondicionMedicaRepository repo;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<CondicionMedica> getAll() {
        log.info("Listando todas las condiciones médicas");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<CondicionMedica> getById(Long id) {
        log.info("Buscando condición médica por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public CondicionMedica create(CondicionMedica condicion) {
        log.info("Registrando condición médica: {}", condicion.getNombre());
        try {
            CondicionMedica guardada = repo.save(condicion);
            log.info("Condición médica registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar condición médica: " + condicion.getNombre(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public CondicionMedica update(CondicionMedica condicion) {
        log.info("Actualizando condición médica ID: {}", condicion.getId());
        try {
            if (!repo.existsById(condicion.getId())) {
                throw new ServiceException("No existe la condición médica con ID: " + condicion.getId(), null);
            }
            return repo.save(condicion);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar condición médica ID: " + condicion.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando condición médica con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar condición médica con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<CondicionMedica> findByIcd10Code(String codigoCIE10) {
        log.info("Buscando condición por código CIE-10: {}", codigoCIE10);
        return repo.findByCodigoCIE10IgnoreCase(codigoCIE10);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<CondicionMedica> findByStatus(boolean activa) {
        log.info("Listando condiciones médicas por estado activa: {}", activa);
        return repo.findByActiva(activa);
    }
}