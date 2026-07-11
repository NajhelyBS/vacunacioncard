package pe.edu.utp.vacunacioncard.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.CuentaUsuarioRepository;
import pe.edu.utp.vacunacioncard.service.auth.ICuentaUsuarioService;
import pe.edu.utp.vacunacioncard.service.patron.singleton.ConfiguracionSistema;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de cuentas de usuario.
 * Maneja la lógica de negocio, la autenticación y las transacciones para la entidad CuentaUsuario.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CuentaUsuarioServiceImpl implements ICuentaUsuarioService {

    /** Zona horaria oficial para el registro de accesos. */
    private static final String ZONE_LIMA = "America/Lima";

    private final CuentaUsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    /**
     * Obtiene una lista con todas las cuentas de usuario registradas.
     * @return {@link List} de todas las entidades {@link CuentaUsuario}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CuentaUsuario> getAll() {
        log.info("Listando todas las cuentas de usuario");
        return repo.findAll();
    }

    /**
     * Busca una cuenta de usuario específica mediante su identificador único.
     * @param id Identificador único de la cuenta de usuario.
     * @return Un {@link Optional} que contiene la {@link CuentaUsuario} si se encuentra,
     *         o un contenedor vacío si no existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaUsuario> getById(Long id) {
        log.info("Buscando cuenta por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Busca una cuenta de usuario a partir de su nombre de usuario (username).
     * Resulta esencial para los flujos de autenticación e inicio de sesión.
     *
     * @param username Nombre de usuario único asociado a la cuenta.
     * @return Un {@link Optional} con la {@link CuentaUsuario} si existe, o vacío en caso contrario.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaUsuario> findByUsername(String username) {
        log.info("Buscando cuenta por username: {}", username);
        return repo.findByUsername(username);
    }

    /**
     * Registra una nueva cuenta de usuario en el sistema.
     * Valida que el username y la contraseña no estén vacíos, que el username no exista
     * previamente y codifica la contraseña con BCrypt antes de persistirla.
     *
     * @param cuenta Entidad {@link CuentaUsuario} cuyo campo {@code passwordHash} contiene
     *               la contraseña en texto plano.
     * @return La entidad {@link CuentaUsuario} persistida con su ID asignado.
     * @throws ServiceException Si faltan datos obligatorios, el username ya existe o falla el acceso a datos.
     */
    @Override
    @Transactional
    public CuentaUsuario create(CuentaUsuario cuenta) {
        log.info("Registrando cuenta para username: {}", cuenta.getUsername());

        if (cuenta.getUsername() == null || cuenta.getUsername().isBlank()) {
            throw new ServiceException("El username es obligatorio", null);
        }
        if (cuenta.getPasswordHash() == null || cuenta.getPasswordHash().isBlank()) {
            throw new ServiceException("La contraseña es obligatoria", null);
        }
        if (repo.findByUsername(cuenta.getUsername()).isPresent()) {
            throw new ServiceException("Ya existe una cuenta con el username: " + cuenta.getUsername(), null);
        }

        cuenta.setPasswordHash(passwordEncoder.encode(cuenta.getPasswordHash()));

        try {
            CuentaUsuario guardada = repo.save(cuenta);
            log.info("Cuenta registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar cuenta de usuario: " + cuenta.getUsername(), e);
        }
    }

    /**
     * Autentica a un usuario validando su contraseña contra el hash almacenado.
     * En caso de fallo incrementa los intentos fallidos y bloquea la cuenta si se supera el
     * límite configurado en {@link ConfiguracionSistema}; en caso de éxito reinicia el contador
     * y actualiza el último acceso.
     * <p>
     * Se usa {@code noRollbackFor = ServiceException.class} para que el incremento de intentos
     * fallidos persista aunque el método finalice lanzando la excepción de credenciales inválidas.
     *
     * @param username    Nombre de usuario de la cuenta.
     * @param rawPassword Contraseña en texto plano a verificar.
     * @return La entidad {@link CuentaUsuario} autenticada.
     * @throws ServiceException Si la cuenta no existe, está inactiva o bloqueada, o la contraseña es incorrecta.
     */
    @Override
    @Transactional(noRollbackFor = ServiceException.class)
    public CuentaUsuario authenticate(String username, String rawPassword) {
        log.info("Autenticando cuenta para username: {}", username);

        CuentaUsuario cuenta = repo.findByUsername(username)
                .orElseThrow(() -> new ServiceException("Credenciales inválidas", null));

        if (!cuenta.isCuentaActiva()) {
            throw new ServiceException("La cuenta está inactiva", null);
        }
        if (cuenta.isBloqueada()) {
            throw new ServiceException("La cuenta está bloqueada por múltiples intentos fallidos", null);
        }

        if (rawPassword == null || !passwordEncoder.matches(rawPassword, cuenta.getPasswordHash())) {
            registrarIntentoFallido(cuenta);
            throw new ServiceException("Credenciales inválidas", null);
        }

        cuenta.setIntentosFallidos(0);
        cuenta.setUltimoAcceso(LocalDateTime.now(ZoneId.of(ZONE_LIMA)));
        log.info("Autenticación exitosa para username: {}", username);
        return repo.save(cuenta);
    }

    /**
     * Incrementa el contador de intentos fallidos de una cuenta y la bloquea si se
     * alcanza el límite configurado.
     *
     * @param cuenta Cuenta sobre la que se registra el intento fallido.
     */
    private void registrarIntentoFallido(CuentaUsuario cuenta) {
        int intentos = cuenta.getIntentosFallidos() + 1;
        cuenta.setIntentosFallidos(intentos);

        if (ConfiguracionSistema.getInstancia().isLoginBloqueado(intentos)) {
            cuenta.setBloqueada(true);
            log.warn("Cuenta '{}' bloqueada tras {} intentos fallidos", cuenta.getUsername(), intentos);
        }
        repo.save(cuenta);
    }

    /**
     * Elimina una cuenta de usuario del sistema mediante su ID.
     *
     * @param id Identificador único de la cuenta a eliminar.
     * @throws ServiceException Si ocurre un error de acceso a datos al intentar eliminar el registro.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando cuenta con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Cuenta eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar cuenta con ID: " + id, e);
        }
    }
}
