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

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Laboratorio> getAll() {
        log.info("Listando todos los laboratorios");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<Laboratorio> getById(Long id) {
        log.info("Buscando laboratorio por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Laboratorio create(Laboratorio laboratorio) {
        log.info("Registrando laboratorio: {}", laboratorio.getNombre());
        try {
            Laboratorio guardado = repo.save(laboratorio);
            log.info("Laboratorio registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar laboratorio: " + laboratorio.getNombre(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Laboratorio update(Laboratorio laboratorio) {
        log.info("Actualizando laboratorio ID: {}", laboratorio.getId());
        try {
            if (!repo.existsById(laboratorio.getId())) {
                throw new ServiceException("No existe el laboratorio con ID: " + laboratorio.getId(), null);
            }
            return repo.save(laboratorio);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar laboratorio ID: " + laboratorio.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando laboratorio con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar laboratorio con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Laboratorio> findByCountry(String pais) {
        log.info("Listando laboratorios por país: {}", pais);
        return repo.findByPaisOrigenIgnoreCase(pais);
    }
}