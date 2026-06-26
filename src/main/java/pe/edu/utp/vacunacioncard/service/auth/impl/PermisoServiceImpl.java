package pe.edu.utp.vacunacioncard.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.Permiso;
import pe.edu.utp.vacunacioncard.repository.auth.PermisoRepository;
import pe.edu.utp.vacunacioncard.service.auth.IPermisoService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermisoServiceImpl implements IPermisoService {

    private final PermisoRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Permiso> listarTodos() {
        log.info("Listando todos los permisos");
        return repo.findAll();
    }

    @Override
    @Transactional
    public Permiso guardar(Permiso permiso) {
        log.info("Guardando permiso con código: {}", permiso.getCodigo());
        try {
            Permiso guardado = repo.save(permiso);
            log.info("Permiso guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar permiso: " + permiso.getCodigo(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Permiso> buscarPorCodigo(String codigo) {
        log.info("Buscando permiso por código: {}", codigo);
        return repo.findByCodigo(codigo);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando permiso con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Permiso eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar permiso con ID: " + id, e);
        }
    }
}
