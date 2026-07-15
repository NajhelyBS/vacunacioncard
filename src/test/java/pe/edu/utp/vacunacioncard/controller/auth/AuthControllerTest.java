package pe.edu.utp.vacunacioncard.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pe.edu.utp.vacunacioncard.controller.GlobalExceptionHandler;
import pe.edu.utp.vacunacioncard.dto.auth.LoginRequest;
import pe.edu.utp.vacunacioncard.dto.auth.LogoutRequest;
import pe.edu.utp.vacunacioncard.dto.auth.RegisterRequest;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import pe.edu.utp.vacunacioncard.service.auth.ICuentaUsuarioService;
import pe.edu.utp.vacunacioncard.service.auth.ISesionUsuarioService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller - AuthController")
class AuthControllerTest {

    @Mock
    private ICuentaUsuarioService cuentaService;

    @Mock
    private ISesionUsuarioService sesionService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthController(cuentaService, sesionService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private CuentaUsuario cuentaGuardada() {
        return CuentaUsuario.builder()
                .id(1L)
                .username("jperez")
                .passwordHash("$2a$hash")
                .build();
    }

    @Test
    @DisplayName("POST /api/auth/register crea la cuenta y responde 201 sin exponer el passwordHash")
    void register_ok() throws Exception {
        when(cuentaService.create(any(CuentaUsuario.class))).thenReturn(cuentaGuardada());

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterRequest("jperez", "secreto123"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("jperez"))
                .andExpect(jsonPath("$.passwordHash").doesNotExist());

        verify(cuentaService, times(1)).create(any(CuentaUsuario.class));
    }

    @Test
    @DisplayName("POST /api/auth/register responde 400 si el username ya existe")
    void register_duplicado() throws Exception {
        when(cuentaService.create(any(CuentaUsuario.class)))
                .thenThrow(new ServiceException("Ya existe una cuenta con el username: jperez", null));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterRequest("jperez", "secreto123"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("POST /api/auth/login autentica, crea la sesión y devuelve el token")
    void login_ok() throws Exception {
        when(cuentaService.authenticate("jperez", "secreto123")).thenReturn(cuentaGuardada());
        when(sesionService.createSession(any(SesionUsuario.class))).thenAnswer(inv -> inv.getArgument(0));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest("jperez", "secreto123"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.cuentaId").value(1))
                .andExpect(jsonPath("$.username").value("jperez"));

        verify(cuentaService, times(1)).authenticate("jperez", "secreto123");
        verify(sesionService, times(1)).createSession(any(SesionUsuario.class));
    }

    @Test
    @DisplayName("POST /api/auth/login responde 400 y no crea sesión si las credenciales son inválidas")
    void login_credencialesInvalidas() throws Exception {
        when(cuentaService.authenticate(anyString(), anyString()))
                .thenThrow(new ServiceException("Credenciales inválidas", null));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest("jperez", "malo"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());

        verify(sesionService, never()).createSession(any(SesionUsuario.class));
    }

    @Test
    @DisplayName("POST /api/auth/logout cierra la sesión asociada al token y responde 204")
    void logout_tokenExistente() throws Exception {
        SesionUsuario sesion = SesionUsuario.builder().id(9L).token("token-abc").activa(true).build();
        when(sesionService.findByToken("token-abc")).thenReturn(Optional.of(sesion));

        mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LogoutRequest("token-abc"))))
                .andExpect(status().isNoContent());

        verify(sesionService, times(1)).closeSession(9L);
    }

    @Test
    @DisplayName("POST /api/auth/logout es idempotente: responde 204 aunque el token no exista")
    void logout_tokenInexistente() throws Exception {
        when(sesionService.findByToken("token-fantasma")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LogoutRequest("token-fantasma"))))
                .andExpect(status().isNoContent());

        verify(sesionService, never()).closeSession(anyLong());
    }
}
