package pe.edu.utp.vacunacioncard.service.campania.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.campania.CentroVacunacion;
import pe.edu.utp.vacunacioncard.repository.campania.CentroVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.campania.ICentroVacunacionService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CentroVacunacionServiceImpl implements ICentroVacunacionService {

    private final CentroVacunacionRepository repo;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<CentroVacunacion> getAll() {
        log.info("Listando todos los centros de vacunación");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<CentroVacunacion> getById(Long id) {
        log.info("Buscando centro de vacunación por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public CentroVacunacion create(CentroVacunacion centro) {
        log.info("Registrando centro: {}", centro.getNombre());
        try {
            CentroVacunacion guardado = repo.save(centro);
            log.info("Centro registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar centro: " + centro.getNombre(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public CentroVacunacion update(CentroVacunacion centro) {
        log.info("Actualizando centro ID: {}", centro.getId());
        try {
            if (!repo.existsById(centro.getId())) {
                throw new ServiceException("No existe el centro con ID: " + centro.getId(), null);
            }
            return repo.save(centro);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar centro ID: " + centro.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando centro de vacunación con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar centro con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<CentroVacunacion> findByStatus(boolean activo) {
        log.info("Listando centros por estado activo: {}", activo);
        return repo.findByActivo(activo);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<CentroVacunacion> searchByName(String nombre) {
        log.info("Buscando centros por nombre: '{}'", nombre);
        return repo.findByNombreContainingIgnoreCase(nombre);
    }
}