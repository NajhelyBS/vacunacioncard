package pe.edu.utp.vacunacioncard.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.SesionUsuarioRepository;
import pe.edu.utp.vacunacioncard.service.auth.ISesionUsuarioService;
import pe.edu.utp.vacunacioncard.service.patron.singleton.ConfiguracionSistema;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SesionUsuarioServiceImpl implements ISesionUsuarioService {

    private final SesionUsuarioRepository repo;

    @Override
    @Transactional
    public SesionUsuario crearSesion(SesionUsuario sesion) {
        log.info("Creando sesión para cuenta ID: {}",
                sesion.getCuenta() != null ? sesion.getCuenta().getId() : "N/A");
        try {
            SesionUsuario guardada = repo.save(sesion);
            log.info("Sesión creada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al crear sesión de usuario", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SesionUsuario> obtenerPorToken(String token) {
        log.info("Buscando sesión por token");
        return repo.findByToken(token);
    }

    @Override
    @Transactional
    public void cerrarSesion(Long id) {
        log.info("Cerrando sesión ID: {}", id);
        try {
            repo.findById(id).ifPresent(s -> {
                s.setActiva(false);
                repo.save(s);
                log.info("Sesión cerrada ID: {}", id);
            });
        } catch (DataAccessException e) {
            throw new ServiceException("Error al cerrar sesión ID: " + id, e);
        }
    }

    /**
     * Uso del patrón Singleton para obtener el límite de intentos desde la configuración global.
     */
    @Override
    @Transactional
    public boolean verificarBloqueoCuenta(int intentosFallidos) {
        int limiteMaximo = ConfiguracionSistema.getInstancia().getMaxIntentosLogin();

        if (intentosFallidos >= limiteMaximo) {
            log.warn("Cuenta excede el límite de {} intentos permitidos", limiteMaximo);
            return true;
        }
        return false;
    }
}
