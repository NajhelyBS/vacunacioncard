package pe.edu.utp.vacunacioncard.service.auth.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.CuentaUsuarioRepository;
import pe.edu.utp.vacunacioncard.service.patron.singleton.ConfiguracionSistema;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - CuentaUsuarioServiceImpl")
class CuentaUsuarioServiceImplTest {

    @Mock
    private CuentaUsuarioRepository repo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CuentaUsuarioServiceImpl service;

    @BeforeEach
    void setUp() {
        ConfiguracionSistema.getInstancia().reset();
    }

    private CuentaUsuario cuentaValida() {
        return CuentaUsuario.builder()
                .username("jperez")
                .passwordHash("secreto123")
                .build();
    }

    @Test
    @DisplayName("Crear codifica la contraseña y persiste cuando el username es nuevo")
    void create_ok() {
        CuentaUsuario cuenta = cuentaValida();
        when(repo.findByUsername("jperez")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("secreto123")).thenReturn("$2a$hash");
        when(repo.save(any(CuentaUsuario.class))).thenAnswer(inv -> inv.getArgument(0));

        CuentaUsuario resultado = service.create(cuenta);

        assertEquals("$2a$hash", resultado.getPasswordHash());
        verify(passwordEncoder, times(1)).encode("secreto123");
        verify(repo, times(1)).save(cuenta);
    }

    @Test
    @DisplayName("Crear lanza ServiceException si el username ya existe")
    void create_duplicado() {
        CuentaUsuario cuenta = cuentaValida();
        when(repo.findByUsername("jperez")).thenReturn(Optional.of(new CuentaUsuario()));

        assertThrows(ServiceException.class, () -> service.create(cuenta));
        verify(repo, never()).save(any(CuentaUsuario.class));
    }

    @Test
    @DisplayName("Crear lanza ServiceException si falta el username")
    void create_sinUsername() {
        CuentaUsuario cuenta = CuentaUsuario.builder().passwordHash("x").build();

        assertThrows(ServiceException.class, () -> service.create(cuenta));
        verify(repo, never()).save(any(CuentaUsuario.class));
    }

    @Test
    @DisplayName("Crear lanza ServiceException si falta la contraseña")
    void create_sinPassword() {
        CuentaUsuario cuenta = CuentaUsuario.builder().username("jperez").build();

        assertThrows(ServiceException.class, () -> service.create(cuenta));
        verify(repo, never()).save(any(CuentaUsuario.class));
    }

    @Test
    @DisplayName("Autenticar exitoso reinicia intentos y actualiza último acceso")
    void authenticate_ok() {
        CuentaUsuario cuenta = CuentaUsuario.builder()
                .username("jperez").passwordHash("$2a$hash").intentosFallidos(2).build();
        when(repo.findByUsername("jperez")).thenReturn(Optional.of(cuenta));
        when(passwordEncoder.matches("secreto123", "$2a$hash")).thenReturn(true);
        when(repo.save(any(CuentaUsuario.class))).thenAnswer(inv -> inv.getArgument(0));

        CuentaUsuario resultado = service.authenticate("jperez", "secreto123");

        assertEquals(0, resultado.getIntentosFallidos());
        assertNotNull(resultado.getUltimoAcceso());
        verify(repo, times(1)).save(cuenta);
    }

    @Test
    @DisplayName("Autenticar con contraseña incorrecta incrementa intentos fallidos")
    void authenticate_passwordIncorrecta() {
        CuentaUsuario cuenta = CuentaUsuario.builder()
                .username("jperez").passwordHash("$2a$hash").intentosFallidos(0).build();
        when(repo.findByUsername("jperez")).thenReturn(Optional.of(cuenta));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.authenticate("jperez", "malo"));
        assertEquals(1, cuenta.getIntentosFallidos());
        assertFalse(cuenta.isBloqueada());
        verify(repo, times(1)).save(cuenta);
    }

    @Test
    @DisplayName("Autenticar bloquea la cuenta al alcanzar el límite de intentos")
    void authenticate_bloqueaAlLimite() {
        // Límite por defecto = 3; con 2 intentos previos, el tercero fallido bloquea.
        CuentaUsuario cuenta = CuentaUsuario.builder()
                .username("jperez").passwordHash("$2a$hash").intentosFallidos(2).build();
        when(repo.findByUsername("jperez")).thenReturn(Optional.of(cuenta));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.authenticate("jperez", "malo"));
        assertEquals(3, cuenta.getIntentosFallidos());
        assertTrue(cuenta.isBloqueada());
    }

    @Test
    @DisplayName("Autenticar lanza ServiceException si la cuenta no existe")
    void authenticate_noExiste() {
        when(repo.findByUsername("fantasma")).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> service.authenticate("fantasma", "x"));
    }

    @Test
    @DisplayName("Autenticar lanza ServiceException si la cuenta está bloqueada")
    void authenticate_bloqueada() {
        CuentaUsuario cuenta = CuentaUsuario.builder()
                .username("jperez").passwordHash("$2a$hash").bloqueada(true).build();
        when(repo.findByUsername("jperez")).thenReturn(Optional.of(cuenta));

        assertThrows(ServiceException.class, () -> service.authenticate("jperez", "secreto123"));
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("Autenticar lanza ServiceException si la cuenta está inactiva")
    void authenticate_inactiva() {
        CuentaUsuario cuenta = CuentaUsuario.builder()
                .username("jperez").passwordHash("$2a$hash").cuentaActiva(false).build();
        when(repo.findByUsername("jperez")).thenReturn(Optional.of(cuenta));

        assertThrows(ServiceException.class, () -> service.authenticate("jperez", "secreto123"));
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }
}
