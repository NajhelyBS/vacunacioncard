package pe.edu.utp.vacunacioncard.service.salud.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import pe.edu.utp.vacunacioncard.repository.salud.CondicionMedicaRepository;
import pe.edu.utp.vacunacioncard.service.salud.ICondicionMedicaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CondicionMedicaServiceImpl implements ICondicionMedicaService {

    private final CondicionMedicaRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<CondicionMedica> listarTodas() {
        log.info("Listando todas las condiciones médicas");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CondicionMedica> obtenerPorId(Long id) {
        log.info("Buscando condición médica por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public CondicionMedica registrar(CondicionMedica condicion) {
        log.info("Registrando condición médica: {}", condicion.getNombre());
        try {
            CondicionMedica guardada = repo.save(condicion);
            log.info("Condición médica registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar condición médica: " + condicion.getNombre(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CondicionMedica> obtenerPorCodigoCIE10(String codigoCIE10) {
        log.info("Buscando condición por código CIE-10: {}", codigoCIE10);
        return repo.findByCodigoCIE10IgnoreCase(codigoCIE10);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CondicionMedica> listarPorEstado(boolean activa) {
        log.info("Listando condiciones médicas por estado activa: {}", activa);
        return repo.findByActiva(activa);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando condición médica con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Condición médica eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar condición médica con ID: " + id, e);
        }
    }
}
