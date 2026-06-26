package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import pe.edu.utp.vacunacioncard.repository.vacunacion.VacunaRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.IVacunaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacunaServiceImpl implements IVacunaService {

    private final VacunaRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> listarTodas() {
        log.info("Listando todas las vacunas");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vacuna> obtenerPorId(Long id) {
        log.info("Buscando vacuna por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Vacuna registrar(Vacuna vacuna) {
        log.info("Registrando vacuna: {}", vacuna.getNombre());
        try {
            Vacuna guardada = repo.save(vacuna);
            log.info("Vacuna registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar vacuna: " + vacuna.getNombre(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> listarPorDisponibilidad(boolean disponible) {
        log.info("Listando vacunas por disponibilidad: {}", disponible);
        return repo.findByDisponible(disponible);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> listarPorLaboratorio(Long laboratorioId) {
        log.info("Listando vacunas del laboratorio ID: {}", laboratorioId);
        return repo.findByLaboratorioId(laboratorioId);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando vacuna con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Vacuna eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar vacuna con ID: " + id, e);
        }
    }
}
