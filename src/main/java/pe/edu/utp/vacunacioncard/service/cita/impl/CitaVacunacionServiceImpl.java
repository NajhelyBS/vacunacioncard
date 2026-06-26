package pe.edu.utp.vacunacioncard.service.cita.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import pe.edu.utp.vacunacioncard.repository.cita.CitaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.cita.ICitaVacunacionService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CitaVacunacionServiceImpl implements ICitaVacunacionService {

    private final CitaVacunacionRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> listarTodas() {
        log.info("Listando todas las citas de vacunación");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CitaVacunacion> obtenerPorId(Long id) {
        log.info("Buscando cita por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public CitaVacunacion programar(CitaVacunacion cita) {
        log.info("Programando cita para paciente ID: {}",
                cita.getPaciente() != null ? cita.getPaciente().getId() : "N/A");
        try {
            CitaVacunacion guardada = repo.save(cita);
            log.info("Cita programada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al programar cita de vacunación", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> listarPorPaciente(Long pacienteId) {
        log.info("Listando citas del paciente ID: {}", pacienteId);
        return repo.findByPacienteId(pacienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> listarPorEstado(String estado) {
        log.info("Listando citas por estado: {}", estado);
        return repo.findByEstado(estado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando cita con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Cita eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar cita con ID: " + id, e);
        }
    }
}
