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

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> listarTodos() {
        log.info("Listando todos los pacientes");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorId(Long id) {
        log.info("Buscando paciente por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Paciente registrar(Paciente paciente) {
        log.info("Registrando paciente DNI: {}", paciente.getDni());
        try {
            Paciente guardado = repo.save(paciente);
            log.info("Paciente registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar paciente DNI: " + paciente.getDni(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorDni(String dni) {
        log.info("Buscando paciente por DNI: {}", dni);
        return repo.findByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorHistoriaClinica(String historiaClinicaId) {
        log.info("Buscando paciente por historia clínica: {}", historiaClinicaId);
        return repo.findByHistoriaClinicaId(historiaClinicaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> listarPorEstado(boolean activo) {
        log.info("Listando pacientes por estado activo: {}", activo);
        return repo.findByActivo(activo);
    }
}
