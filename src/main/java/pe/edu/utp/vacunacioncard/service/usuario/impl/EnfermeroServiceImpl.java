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

    @Override
    @Transactional(readOnly = true)
    public List<Enfermero> listarTodos() {
        log.info("Listando todos los enfermeros");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Enfermero> obtenerPorId(Long id) {
        log.info("Buscando enfermero por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Enfermero registrar(Enfermero enfermero) {
        log.info("Registrando enfermero con colegiatura: {}", enfermero.getColegiatura());
        try {
            Enfermero guardado = repo.save(enfermero);
            log.info("Enfermero registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar enfermero", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Enfermero> obtenerPorColegiatura(String colegiatura) {
        log.info("Buscando enfermero por colegiatura: {}", colegiatura);
        return repo.findByColegiatura(colegiatura);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enfermero> listarPorCentroTrabajo(String centroTrabajo) {
        log.info("Listando enfermeros por centro: {}", centroTrabajo);
        return repo.findByCentroTrabajoIgnoreCase(centroTrabajo);
    }
}
