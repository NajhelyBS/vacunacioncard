package pe.edu.utp.vacunacioncard.service.cita.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.exception.ServiceExceptionHandler;
import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import pe.edu.utp.vacunacioncard.repository.cita.HorarioRepository;
import pe.edu.utp.vacunacioncard.service.cita.IHorarioService;


import java.util.List;
import java.util.Optional;


/**
 * Implementación del servicio para la gestión de los horarios de atención.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HorarioServiceImpl implements IHorarioService {


    private final HorarioRepository repo;


    @Override
    @Transactional(readOnly = true)
    public List<Horario> getAll() {
        log.info("Listando todos los horarios");
        return repo.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Horario> findById(Long id) {
        log.info("Buscando horario por ID: {}", id);
        return repo.findById(id);
    }


    @Override
    @Transactional
    public Horario create(Horario horario) {
        log.info("Guardando horario para día: {}", horario.getDiaSemana());
        Horario guardado = ServiceExceptionHandler.execute(
                () -> repo.save(horario),
                "Error al guardar horario"
        );
        log.info("Horario guardado con ID: {}", guardado.getId());
        return guardado;
    }


    @Override
    @Transactional
    public Horario update(Horario horario) {
        log.info("Actualizando horario ID: {}", horario.getId());
        try {
            if (horario.getId() == null || !repo.existsById(horario.getId())) {
                throw new ServiceException("No existe el horario con ID: " + horario.getId(), null);
            }
            return repo.save(horario);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar horario ID: " + horario.getId(), e);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<Horario> findByDay(DiaSemana day) {
        log.info("Listando horarios del día: {}", day);
        return repo.findByDiaSemana(day);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando horario con ID: {}", id);
        ServiceExceptionHandler.executeVoid(
                () -> repo.deleteById(id),
                "Error al eliminar horario con ID: " + id
        );
        log.info("Horario eliminado con ID: {}", id);
    }
}


