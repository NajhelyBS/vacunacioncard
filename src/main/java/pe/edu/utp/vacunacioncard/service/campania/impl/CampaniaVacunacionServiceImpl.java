package pe.edu.utp.vacunacioncard.service.campania.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import pe.edu.utp.vacunacioncard.repository.campania.CampaniaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.campania.ICampaniaVacunacionService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampaniaVacunacionServiceImpl implements ICampaniaVacunacionService {

    private final CampaniaVacunacionRepository repo;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<CampaniaVacunacion> getAll() {
        log.info("Listando todas las campañas de vacunación");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<CampaniaVacunacion> getById(Long id) {
        log.info("Buscando campaña con ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public CampaniaVacunacion create(CampaniaVacunacion campania) {
        log.info("Registrando campaña: {}", campania.getNombre());
        try {
            CampaniaVacunacion guardada = repo.save(campania);
            log.info("Campaña registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar campaña: " + campania.getNombre(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public CampaniaVacunacion update(CampaniaVacunacion campania) {
        log.info("Actualizando campaña ID: {}", campania.getId());
        try {
            if (!repo.existsById(campania.getId())) {
                throw new ServiceException("No existe la campaña con ID: " + campania.getId(), null);
            }
            return repo.save(campania);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar campaña ID: " + campania.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando campaña con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar campaña con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<CampaniaVacunacion> findByStatus(String estado) {
        log.info("Listando campañas por estado: {}", estado);
        return repo.findByEstado(estado);
    }
}
