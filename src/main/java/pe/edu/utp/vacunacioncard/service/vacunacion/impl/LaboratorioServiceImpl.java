package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.Laboratorio;
import pe.edu.utp.vacunacioncard.repository.vacunacion.LaboratorioRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.ILaboratorioService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LaboratorioServiceImpl implements ILaboratorioService {

    private final LaboratorioRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Laboratorio> listarTodos() {
        log.info("Listando todos los laboratorios");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Laboratorio> obtenerPorId(Long id) {
        log.info("Buscando laboratorio por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Laboratorio registrar(Laboratorio laboratorio) {
        log.info("Registrando laboratorio: {}", laboratorio.getNombre());
        try {
            Laboratorio guardado = repo.save(laboratorio);
            log.info("Laboratorio registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar laboratorio: " + laboratorio.getNombre(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Laboratorio> listarPorPais(String pais) {
        log.info("Listando laboratorios por país: {}", pais);
        return repo.findByPaisOrigenIgnoreCase(pais);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando laboratorio con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Laboratorio eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar laboratorio con ID: " + id, e);
        }
    }
}
