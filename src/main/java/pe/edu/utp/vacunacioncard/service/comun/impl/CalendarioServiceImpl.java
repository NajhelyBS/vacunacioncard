package pe.edu.utp.vacunacioncard.service.comun.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceExceptionHandler;
import pe.edu.utp.vacunacioncard.model.comun.Calendario;
import pe.edu.utp.vacunacioncard.repository.comun.CalendarioRepository;
import pe.edu.utp.vacunacioncard.service.comun.ICalendarioService;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * Implementación del servicio para la gestión del calendario del sistema.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarioServiceImpl implements ICalendarioService {


    private final CalendarioRepository repo;


    @Override
    @Transactional
    public Calendario create(Calendario calendario) {
        log.info("Guardando día en calendario: {}", calendario.getFecha());
        Calendario guardado = ServiceExceptionHandler.execute(
                () -> repo.save(calendario),
                "Error al guardar día: " + calendario.getFecha()
        );
        log.info("Día guardado con ID: {}", guardado.getId());
        return guardado;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Calendario> findByDate(LocalDate fecha) {
        log.info("Buscando calendario por fecha: {}", fecha);
        return repo.findByFecha(fecha);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Calendario> findByAvailability(boolean esHabil) {
        log.info("Listando días por disponibilidad hábil: {}", esHabil);
        return repo.findByEsHabil(esHabil);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando día del calendario con ID: {}", id);
        ServiceExceptionHandler.executeVoid(
                () -> repo.deleteById(id),
                "Error al eliminar día con ID: " + id
        );
        log.info("Día eliminado con ID: {}", id);
    }
}

