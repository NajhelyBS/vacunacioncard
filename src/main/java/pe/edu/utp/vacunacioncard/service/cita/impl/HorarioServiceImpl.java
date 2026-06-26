package pe.edu.utp.vacunacioncard.service.cita.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import pe.edu.utp.vacunacioncard.repository.cita.HorarioRepository;
import pe.edu.utp.vacunacioncard.service.cita.IHorarioService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HorarioServiceImpl implements IHorarioService {

    private final HorarioRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Horario> listarTodos() {
        log.info("Listando todos los horarios");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Horario> obtenerPorId(Long id) {
        log.info("Buscando horario por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Horario guardar(Horario horario) {
        log.info("Guardando horario para día: {}", horario.getDiaSemana());
        try {
            Horario guardado = repo.save(horario);
            log.info("Horario guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar horario", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Horario> listarPorDia(DiaSemana diaSemana) {
        log.info("Listando horarios del día: {}", diaSemana);
        return repo.findByDiaSemana(diaSemana);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando horario con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Horario eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar horario con ID: " + id, e);
        }
    }
}
