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
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CitaVacunacionServiceImpl implements ICitaVacunacionService {


    private final CitaVacunacionRepository repo;


    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> getAll() {
        log.info("Iniciando la búsqueda global de citas médicas en el sistema");
        return this.repo.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CitaVacunacion> findById(Long id) {
        log.info("Consultando el registro clínico de la cita ID: {}", id);
        return this.repo.findById(id);
    }


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


    // Actualización de una cita existente
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


    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> findByPatient(Long patientId) {
        log.info("Listando citas del paciente ID: {}", patientId);
        return this.repo.findByPacienteId(patientId);
    }


    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> findByStatus(String status) {
        log.info("Obteniendo registros de citas bajo la condición operativa: {}", status);
        return this.repo.findByEstado(status);
    }


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
