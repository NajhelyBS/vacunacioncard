package pe.edu.utp.vacunacioncard.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.CuentaUsuarioRepository;
import pe.edu.utp.vacunacioncard.service.auth.ICuentaUsuarioService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CuentaUsuarioServiceImpl implements ICuentaUsuarioService {

    private final CuentaUsuarioRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<CuentaUsuario> listarTodas() {
        log.info("Listando todas las cuentas de usuario");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaUsuario> buscarPorId(Long id) {
        log.info("Buscando cuenta por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public CuentaUsuario registrar(CuentaUsuario cuenta) {
        log.info("Registrando cuenta para username: {}", cuenta.getUsername());
        try {
            CuentaUsuario guardada = repo.save(cuenta);
            log.info("Cuenta registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar cuenta de usuario: " + cuenta.getUsername(), e);
        }
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando cuenta con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Cuenta eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar cuenta con ID: " + id, e);
        }
    }
}
