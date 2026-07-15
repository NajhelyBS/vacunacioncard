package pe.edu.utp.vacunacioncard.service.auth.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.CuentaUsuarioRepository;
import pe.edu.utp.vacunacioncard.service.patron.singleton.ConfiguracionSistema;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
                .cuentaActiva(true)
                .bloqueada(false)
                .intentosFallidos(0)
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
        CuentaUsuario cuentaVacia = CuentaUsuario.builder().username("   ").passwordHash("x").build();
        assertThrows(ServiceException.class, () -> service.create(cuentaVacia));

        verify(repo, never()).save(any(CuentaUsuario.class));
    }

    @Test
    @DisplayName("Crear lanza ServiceException si falta la contraseña")
    void create_sinPassword() {
        CuentaUsuario cuenta = CuentaUsuario.builder().username("jperez").build();
        assertThrows(ServiceException.class, () -> service.create(cuenta));

        CuentaUsuario cuentaContraseniaVacia = CuentaUsuario.builder().username("jperez").passwordHash("").build();
        assertThrows(ServiceException.class, () -> service.create(cuentaContraseniaVacia));

        verify(repo, never()).save(any(CuentaUsuario.class));
    }

    @Test
    @DisplayName("Crear lanza ServiceException ante un fallo de persistencia en la base de datos")
    void create_fallaBaseDatos() {
        CuentaUsuario cuenta = cuentaValida();
        when(repo.findByUsername("jperez")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$hash");
        when(repo.save(any(CuentaUsuario.class))).thenThrow(new DataRetrievalFailureException("Error catastrófico de BD"));

        assertThrows(ServiceException.class, () -> service.create(cuenta));
    }

    @Test
    @DisplayName("Autenticar exitoso reinicia intentos y actualiza último acceso")
    void authenticate_ok() {
        CuentaUsuario cuenta = CuentaUsuario.builder()
                .username("jperez").passwordHash("$2a$hash").cuentaActiva(true).intentosFallidos(2).build();
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
                .username("jperez").passwordHash("$2a$hash").cuentaActiva(true).intentosFallidos(0).build();
        when(repo.findByUsername("jperez")).thenReturn(Optional.of(cuenta));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.authenticate("jperez", "malo"));
        assertEquals(1, cuenta.getIntentosFallidos());
        assertFalse(cuenta.isBloqueada());
        verify(repo, times(1)).save(cuenta);
    }

    @Test
    @DisplayName("Autenticar con contraseña nula incrementa intentos fallidos")
    void authenticate_passwordNula() {
        CuentaUsuario cuenta = CuentaUsuario.builder()
                .username("jperez").passwordHash("$2a$hash").cuentaActiva(true).intentosFallidos(0).build();
        when(repo.findByUsername("jperez")).thenReturn(Optional.of(cuenta));

        assertThrows(ServiceException.class, () -> service.authenticate("jperez", null));
        assertEquals(1, cuenta.getIntentosFallidos());
        verify(repo, times(1)).save(cuenta);
    }

    @Test
    @DisplayName("Autenticar bloquea la cuenta al alcanzar el límite de intentos")
    void authenticate_bloqueaAlLimite() {
        CuentaUsuario cuenta = CuentaUsuario.builder()
                .username("jperez").passwordHash("$2a$hash").cuentaActiva(true).intentosFallidos(2).build();
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
                .username("jperez").passwordHash("$2a$hash").cuentaActiva(true).bloqueada(true).build();
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

    @Test
    @DisplayName("getAll recupera de forma idónea la lista total de cuentas")
    void getAll_cobertura() {
        when(repo.findAll()).thenReturn(List.of(cuentaValida()));
        List<CuentaUsuario> resultado = service.getAll();
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("findByUsername recupera la cuenta de forma idónea si existe")
    void findByUsername_cobertura() {
        when(repo.findByUsername("jperez")).thenReturn(Optional.of(cuentaValida()));
        Optional<CuentaUsuario> resultado = service.findByUsername("jperez");
        assertTrue(resultado.isPresent());
    }

    @Test
    @DisplayName("deleteById elimina la cuenta de usuario delegando al repositorio")
    void deleteById_coberturaExito() {
        doNothing().when(repo).deleteById(1L);
        assertDoesNotThrow(() -> service.deleteById(1L));
    }
    @Test
    @DisplayName("deleteById lanza ServiceException ante un fallo técnico en la base de datos")
    void deleteById_coberturaFallo() {
        doThrow(new DataRetrievalFailureException("Error relacional")).when(repo).deleteById(1L);
        assertThrows(ServiceException.class, () -> service.deleteById(1L));
    }

    @Test
    @DisplayName("getById recupera la cuenta de forma idónea si existe")
    void getById_cobertura() {
        CuentaUsuario cuenta = cuentaValida();
        when(repo.findById(1L)).thenReturn(Optional.of(cuenta));
        Optional<CuentaUsuario> resultado = service.getById(1L);
        assertTrue(resultado.isPresent());
        assertEquals("jperez", resultado.get().getUsername());
        verify(repo, times(1)).findById(1L);
    }




}




