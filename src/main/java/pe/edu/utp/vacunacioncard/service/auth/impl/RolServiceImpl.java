package pe.edu.utp.vacunacioncard.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.Rol;
import pe.edu.utp.vacunacioncard.repository.auth.RolRepository;
import pe.edu.utp.vacunacioncard.service.auth.IRolService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolServiceImpl implements IRolService {

    private final RolRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Rol> listarTodo() {
        log.info("Listando todos los roles");
        return repo.findAll();
    }

    @Override
    @Transactional
    public Rol guardar(Rol rol) {
        log.info("Guardando rol: {}", rol.getNombre());
        try {
            Rol guardado = repo.save(rol);
            log.info("Rol guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar rol: " + rol.getNombre(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> buscarPorNombre(String nombre) {
        log.info("Buscando rol por nombre: {}", nombre);
        return repo.findByNombre(nombre);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando rol con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Rol eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar rol con ID: " + id, e);
        }
    }
}
