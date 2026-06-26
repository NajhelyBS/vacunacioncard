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

    @Override
    @Transactional(readOnly = true)
    public List<Administrador> listarTodos() {
        log.info("Listando todos los administradores");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Administrador> obtenerPorId(Long id) {
        log.info("Buscando administrador por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Administrador registrar(Administrador administrador) {
        log.info("Registrando administrador del área: {}", administrador.getArea());
        try {
            Administrador guardado = repo.save(administrador);
            log.info("Administrador registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar administrador", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Administrador> listarPorArea(String area) {
        log.info("Listando administradores por área: {}", area);
        return repo.findByAreaIgnoreCase(area);
    }
}
