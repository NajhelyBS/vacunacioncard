package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.repository.usuario.PacienteRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IPacienteService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements IPacienteService {

    private final PacienteRepository repo;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Paciente> getAll() {
        log.info("Listando todos los pacientes");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> getById(Long id) {
        log.info("Buscando paciente por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Paciente create(Paciente paciente) {
        log.info("Registrando paciente DNI: {}", paciente.getDni());
        try {
            Paciente guardado = repo.save(paciente);
            log.info("Paciente registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar paciente DNI: " + paciente.getDni(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Paciente update(Paciente paciente) {
        log.info("Actualizando paciente ID: {}", paciente.getId());
        try {
            if (!repo.existsById(paciente.getId())) {
                throw new ServiceException("No existe el paciente con ID: " + paciente.getId(), null);
            }
            return repo.save(paciente);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar paciente ID: " + paciente.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando paciente con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar paciente con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> findByDni(String dni) {
        log.info("Buscando paciente por DNI: {}", dni);
        return repo.findByDni(dni);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> findByHistoriaClinicaId(String historiaClinicaId) {
        log.info("Buscando paciente por historia clínica: {}", historiaClinicaId);
        return repo.findByHistoriaClinicaId(historiaClinicaId);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Paciente> findByStatus(boolean activo) {
        log.info("Listando pacientes por estado activo: {}", activo);
        return repo.findByActivo(activo);
    }
}
