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
 * Proporciona la lógica de negocio para la administración del calendario base,
 * permitiendo listar, buscar, registrar, actualizar y eliminar turnos operativos.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HorarioServiceImpl implements IHorarioService {

    private final HorarioRepository repo;

    /**
     * Obtiene el catálogo completo de horarios de atención configurados en el sistema.
     *
     * @return Una lista con todas las entidades {@link Horario} existentes.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Horario> getAll() {
        log.info("Listando todos los horarios");
        return repo.findAll();
    }

    /**
     * Recupera un horario de atención específico a partir de su identificador único.
     *
     * @param id El identificador único del horario a buscar.
     * @return Un contenedor {@link Optional} con el horario si existe, o vacío si no es encontrado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Horario> findById(Long id) {
        log.info("Buscando horario por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra un nuevo horario o turno de atención médica en el repositorio.
     *
     * @param horario La entidad {@link Horario} que contiene los parámetros del nuevo turno.
     * @return La entidad persistida con su ID autogenerado asignado.
     * @throws ServiceException Si se genera algún inconveniente técnico durante la inserción en la base de datos.
     */
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

    /**
     * Actualiza la información de un bloque de horario ya existente en el sistema.
     * Realiza un control previo de existencia antes de procesar el guardado.
     *
     * @param horario La entidad {@link Horario} con las modificaciones requeridas.
     * @return La entidad modificada y actualizada en el almacenamiento.
     * @throws ServiceException Si el ID proporcionado es nulo, si el horario no existe o por fallos en el motor de persistencia.
     */
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

    /**
     * Filtra y lista los turnos o bloques de atención programados para un día de la semana en particular.
     *
     * @param day El día de la semana bajo el enumerado {@link DiaSemana} a consultar.
     * @return Una lista de entidades {@link Horario} asignadas al día especificado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Horario> findByDay(DiaSemana day) {
        log.info("Listando horarios del día: {}", day);
        return repo.findByDiaSemana(day);
    }

    /**
     * Remueve físicamente un bloque de horario del sistema utilizando su identificador único.
     *
     * @param id El identificador único del registro de horario que se desea eliminar.
     * @throws ServiceException Si ocurre una excepción de acceso a datos al borrar el registro.
     */
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



