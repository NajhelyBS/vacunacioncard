package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.Administrador;
import pe.edu.utp.vacunacioncard.repository.usuario.AdministradorRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IAdministradorService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdministradorServiceImpl implements IAdministradorService {

    private final AdministradorRepository repo;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Administrador> getAll() {
        log.info("Listando todos los administradores");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<Administrador> getById(Long id) {
        log.info("Buscando administrador por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Administrador create(Administrador administrador) {
        log.info("Registrando administrador del área: {}", administrador.getArea());
        try {
            Administrador guardado = repo.save(administrador);
            log.info("Administrador registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar administrador", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Administrador update(Administrador administrador) {
        log.info("Actualizando administrador ID: {}", administrador.getId());
        try {
            if (!repo.existsById(administrador.getId())) {
                throw new ServiceException("No existe el administrador con ID: " + administrador.getId(), null);
            }
            return repo.save(administrador);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar administrador ID: " + administrador.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando administrador con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar administrador con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Administrador> findByArea(String area) {
        log.info("Listando administradores por área: {}", area);
        return repo.findByAreaIgnoreCase(area);
    }
}
