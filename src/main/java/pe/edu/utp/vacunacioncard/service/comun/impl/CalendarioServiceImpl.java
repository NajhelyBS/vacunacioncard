package pe.edu.utp.vacunacioncard.service.comun.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.comun.Calendario;
import pe.edu.utp.vacunacioncard.repository.comun.CalendarioRepository;
import pe.edu.utp.vacunacioncard.service.comun.ICalendarioService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarioServiceImpl implements ICalendarioService {

    private final CalendarioRepository repo;

    @Override
    @Transactional
    public Calendario guardarDia(Calendario calendario) {
        log.info("Guardando día en calendario: {}", calendario.getFecha());
        try {
            Calendario guardado = repo.save(calendario);
            log.info("Día guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar día: " + calendario.getFecha(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Calendario> obtenerPorFecha(LocalDate fecha) {
        log.info("Buscando calendario por fecha: {}", fecha);
        return repo.findByFecha(fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Calendario> listarPorDisponibilidad(boolean esHabil) {
        log.info("Listando días por disponibilidad hábil: {}", esHabil);
        return repo.findByEsHabil(esHabil);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando día del calendario con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Día eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar día con ID: " + id, e);
        }
    }
}
