package pe.edu.utp.vacunacioncard.service.cita.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.exception.ServiceExceptionHandler;
import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import pe.edu.utp.vacunacioncard.repository.cita.CitaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.cita.ICitaVacunacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de citas de vacunación.
 * Proporciona la lógica de negocio para listar, buscar, registrar,
 * actualizar y remover programaciones de vacunas en el sistema.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CitaVacunacionServiceImpl implements ICitaVacunacionService {

    private final CitaVacunacionRepository repo;

    /**
     * Recupera la totalidad de las citas de vacunación registradas en el sistema.
     *
     * @return Una lista con todas las entidades {@link CitaVacunacion} encontradas.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> getAll() {
        log.info("Iniciando la búsqueda global de citas médicas en el sistema");
        return this.repo.findAll();
    }

    /**
     * Busca el registro clínico de una cita de vacunación mediante su identificador único.
     *
     * @param id El identificador único de la cita.
     * @return Un contenedor {@link Optional} que aloja la cita si se encuentra, o vacío en caso contrario.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CitaVacunacion> findById(Long id) {
        log.info("Consultando el registro clínico de la cita ID: {}", id);
        return this.repo.findById(id);
    }

    /**
     * Registra y programa una nueva cita de vacunación en el repositorio.
     *
     * @param cita La entidad {@link CitaVacunacion} con los datos de la nueva programación.
     * @return La entidad persistida con su respectivo identificador generado.
     * @throws ServiceException Si ocurre una anomalía a nivel de datos durante el registro.
     */
    @Override
    @Transactional
    public CitaVacunacion create(CitaVacunacion cita) {
        log.info("Programando cita para paciente ID: {}",
                cita.getPaciente() != null ? cita.getPaciente().getId() : "N/A");

        return ServiceExceptionHandler.execute(
                () -> this.repo.save(cita),
                "Error al programar cita de vacunación"
        );
    }

    /**
     * Actualiza los datos de una cita de vacunación ya existente en el sistema.
     * Verifica previamente la existencia de la cita antes de persistir los cambios.
     *
     * @param cita La entidad {@link CitaVacunacion} con los valores actualizados.
     * @return La entidad modificada y guardada en la base de datos.
     * @throws ServiceException Si el ID es nulo, si la cita no existe o si ocurre un fallo en la persistencia.
     */
    @Override
    @Transactional
    public CitaVacunacion update(CitaVacunacion cita) {
        log.info("Actualizando cita ID: {}", cita.getId());
        try {
            if (cita.getId() == null || !this.repo.existsById(cita.getId())) {
                throw new ServiceException("No existe la cita con ID: " + cita.getId(), null);
            }
            return this.repo.save(cita);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar cita ID: " + cita.getId(), e);
        }
    }

    /**
     * Obtiene el historial de citas médicas asociadas a un paciente en específico.
     *
     * @param patientId El identificador único del paciente.
     * @return Una lista de {@link CitaVacunacion} correspondientes al paciente indicado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> findByPatient(Long patientId) {
        log.info("Listando citas del paciente ID: {}", patientId);
        return this.repo.findByPacienteId(patientId);
    }

    /**
     * Filtra y lista las citas de vacunación según su estado operativo actual.
     *
     * @param status El estado clínico o administrativo a filtrar (ej. PENDIENTE, COMPLETADA).
     * @return Una lista de {@link CitaVacunacion} que cumplen con el estado especificado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> findByStatus(String status) {
        log.info("Obteniendo registros de citas bajo la condición operativa: {}", status);
        return this.repo.findByEstado(status);
    }

    /**
     * Elimina físicamente del repositorio una cita de vacunación mediante su ID.
     *
     * @param id El identificador único de la cita a remover.
     * @throws ServiceException Si se presenta un error al intentar eliminar el registro.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Solicitando remoción física de la cita con ID: {}", id);
        ServiceExceptionHandler.executeVoid(
                () -> this.repo.deleteById(id),
                "Error al eliminar cita con ID: " + id
        );
        log.info("La cita con ID {} fue retirada del repositorio con éxito", id);
    }
}
