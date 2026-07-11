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
 * Proporciona la lógica de negocio para administrar las fechas operativas,
 * controlando los días laborables, feriados y la disponibilidad general.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarioServiceImpl implements ICalendarioService {

    private final CalendarioRepository repo;

    /**
     * Registra y persiste un nuevo día en el calendario general del sistema.
     *
     * @param calendario La entidad {@link Calendario} con los datos de la fecha a registrar.
     * @return La entidad persistida con su identificador único asignado.
     */
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

    /**
     * Busca la configuración operativa de un día en específico mediante su fecha.
     *
     * @param fecha La fecha exacta {@link LocalDate} que se desea consultar.
     * @return Un contenedor {@link Optional} con los datos del día si existe, o vacío en caso contrario.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Calendario> findByDate(LocalDate fecha) {
        log.info("Buscando calendario por fecha: {}", fecha);
        return repo.findByFecha(fecha);
    }

    /**
     * Filtra y lista los días registrados en el sistema según su condición de disponibilidad.
     * Permite segregar los días útiles o hábiles de los fines de semana o feriados.
     *
     * @param esHabil Indicador booleano; {@code true} para obtener días hábiles, {@code false} para no hábiles.
     * @return Una lista de entidades {@link Calendario} que coinciden con el criterio de disponibilidad.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Calendario> findByAvailability(boolean esHabil) {
        log.info("Listando días por disponibilidad hábil: {}", esHabil);
        return repo.findByEsHabil(esHabil);
    }

    /**
     * Remueve físicamente un día configurado en el calendario utilizando su ID único.
     *
     * @param id El identificador único del registro del calendario a eliminar.
     */
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

