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

    @Override
    @Transactional(readOnly = true)
    public List<Contraindicacion> listarTodas() {
        log.info("Listando todas las contraindicaciones");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contraindicacion> obtenerPorId(Long id) {
        log.info("Buscando contraindicación por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Contraindicacion registrar(Contraindicacion contraindicacion) {
        log.info("Registrando contraindicación");
        try {
            Contraindicacion guardada = repo.save(contraindicacion);
            log.info("Contraindicación registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar contraindicación", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contraindicacion> listarPorVacunaAfectada(Long vacunaId) {
        log.info("Listando contraindicaciones de vacuna ID: {}", vacunaId);
        return repo.findByVacunaAfectadaId(vacunaId);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando contraindicación con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Contraindicación eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar contraindicación con ID: " + id, e);
        }
    }
}
