package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion;
import pe.edu.utp.vacunacioncard.repository.vacunacion.EsquemaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.IEsquemaVacunacionService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EsquemaVacunacionServiceImpl implements IEsquemaVacunacionService {

    private final EsquemaVacunacionRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<EsquemaVacunacion> listarTodos() {
        log.info("Listando todos los esquemas de vacunación");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EsquemaVacunacion> obtenerPorId(Long id) {
        log.info("Buscando esquema por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public EsquemaVacunacion guardar(EsquemaVacunacion esquema) {
        log.info("Guardando esquema del paciente ID: {}",
                esquema.getPacienteAsignado() != null ? esquema.getPacienteAsignado().getId() : "N/A");
        try {
            EsquemaVacunacion guardado = repo.save(esquema);
            log.info("Esquema guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar esquema de vacunación", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EsquemaVacunacion> listarPorPaciente(Long pacienteId) {
        log.info("Listando esquemas del paciente ID: {}", pacienteId);
        return repo.findByPacienteAsignadoId(pacienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EsquemaVacunacion> listarPorEstado(String estado) {
        log.info("Listando esquemas por estado: {}", estado);
        return repo.findByEstadoIgnoreCase(estado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando esquema de vacunación con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Esquema eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar esquema con ID: " + id, e);
        }
    }
}
