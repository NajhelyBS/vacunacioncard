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

    @Override
    @Transactional(readOnly = true)
    public List<CampaniaVacunacion> listarTodas() {
        log.info("Listando todas las campañas de vacunación");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CampaniaVacunacion> obtenerPorId(Long id) {
        log.info("Buscando campaña por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public CampaniaVacunacion registrar(CampaniaVacunacion campania) {
        log.info("Registrando campaña: {}", campania.getNombre());
        try {
            CampaniaVacunacion guardada = repo.save(campania);
            log.info("Campaña registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar campaña: " + campania.getNombre(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaniaVacunacion> listarPorEstado(String estado) {
        log.info("Listando campañas por estado: {}", estado);
        return repo.findByEstado(estado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando campaña con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Campaña eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar campaña con ID: " + id, e);
        }
    }
}
