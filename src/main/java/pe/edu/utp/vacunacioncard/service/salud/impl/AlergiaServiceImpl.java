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

    @Override
    @Transactional(readOnly = true)
    public List<Alergia> listarTodas() {
        log.info("Listando todas las alergias");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Alergia> obtenerPorId(Long id) {
        log.info("Buscando alergia por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Alergia registrar(Alergia alergia) {
        log.info("Registrando alergia: {}", alergia.getNombre());
        try {
            Alergia guardada = repo.save(alergia);
            log.info("Alergia registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar alergia: " + alergia.getNombre(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alergia> listarPorSeveridad(String severidad) {
        log.info("Listando alergias por severidad: {}", severidad);
        return repo.findBySeveridadIgnoreCase(severidad);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando alergia con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Alergia eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar alergia con ID: " + id, e);
        }
    }
}
