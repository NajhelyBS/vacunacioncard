package pe.edu.utp.vacunacioncard.controller.comun;

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
import pe.edu.utp.vacunacioncard.dto.comun.RegistroAuditoriaRequest;
import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.repository.usuario.UsuarioRepository;
import pe.edu.utp.vacunacioncard.service.comun.IRegistroAuditoriaService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller - RegistroAuditoriaController")
class RegistroAuditoriaControllerTest {

    @Mock
    private IRegistroAuditoriaService service;

    @Mock
    private UsuarioRepository usuarioRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /** Marca temporal fija para garantizar pruebas deterministas. */
    private static final LocalDateTime FECHA_FIJA = LocalDateTime.of(2026, Month.JULY, 28, 9, 30);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RegistroAuditoriaController(service, usuarioRepository))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private Usuario crearUsuario() {
        Enfermero enfermero = new Enfermero();
        enfermero.setId(2L);
        enfermero.setNombreCompleto("María Torres");
        return enfermero;
    }

    private RegistroAuditoria crearAuditoria() {
        RegistroAuditoria auditoria = new RegistroAuditoria();
        auditoria.setId(1L);
        auditoria.setUsuario(crearUsuario());
        auditoria.setAccion("REGISTRAR_DOSIS");
        auditoria.setEntidadAfectada("RegistroVacuna");
        auditoria.setFechaHora(FECHA_FIJA);
        return auditoria;
    }

    @Test
    @DisplayName("GET /api/auditoria lista todo el historial de auditoría")
    void getAll() throws Exception {
        when(service.getAll()).thenReturn(List.of(crearAuditoria()));

        mockMvc.perform(get("/api/auditoria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accion").value("REGISTRAR_DOSIS"))
                .andExpect(jsonPath("$[0].usuarioNombre").value("María Torres"));

        verify(service, times(1)).getAll();
    }

    @Test
    @DisplayName("GET /api/auditoria/{id} retorna la traza si existe")
    void getById_existe() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.of(crearAuditoria()));

        mockMvc.perform(get("/api/auditoria/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/auditoria/{id} responde 404 si la traza no existe")
    void getById_noExiste() throws Exception {
        when(service.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/auditoria/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/auditoria/usuario/{id} lista la auditoría de un usuario")
    void getByUsuario() throws Exception {
        when(service.findByUser(2L)).thenReturn(List.of(crearAuditoria()));

        mockMvc.perform(get("/api/auditoria/usuario/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuarioId").value(2));

        verify(service, times(1)).findByUser(2L);
    }

    @Test
    @DisplayName("GET /api/auditoria/entidad/{entidad} filtra la auditoría por entidad afectada")
    void getByEntidad() throws Exception {
        when(service.findByAffectedEntity("RegistroVacuna")).thenReturn(List.of(crearAuditoria()));

        mockMvc.perform(get("/api/auditoria/entidad/RegistroVacuna"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].entidadAfectada").value("RegistroVacuna"));

        verify(service, times(1)).findByAffectedEntity("RegistroVacuna");
    }

    @Test
    @DisplayName("POST /api/auditoria registra la acción asociándola al usuario y responde 201")
    void create_conUsuario() throws Exception {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(crearUsuario()));
        when(service.create(any(RegistroAuditoria.class))).thenReturn(crearAuditoria());

        mockMvc.perform(post("/api/auditoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegistroAuditoriaRequest(
                                2L, "REGISTRAR_DOSIS", "RegistroVacuna", "10", "Dosis aplicada"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accion").value("REGISTRAR_DOSIS"));

        verify(service, times(1)).create(any(RegistroAuditoria.class));
    }

    @Test
    @DisplayName("POST /api/auditoria registra la acción sin usuario cuando no se envía usuarioId")
    void create_sinUsuario() throws Exception {
        when(service.create(any(RegistroAuditoria.class))).thenReturn(crearAuditoria());

        mockMvc.perform(post("/api/auditoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegistroAuditoriaRequest(
                                null, "LOGIN_FALLIDO", "CuentaUsuario", "5", "Intento fallido"))))
                .andExpect(status().isCreated());

        verify(usuarioRepository, never()).findById(any());
        verify(service, times(1)).create(any(RegistroAuditoria.class));
    }

    @Test
    @DisplayName("POST /api/auditoria responde 400 si el usuario indicado no existe")
    void create_usuarioNoExiste() throws Exception {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auditoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegistroAuditoriaRequest(
                                99L, "REGISTRAR_DOSIS", "RegistroVacuna", "10", "Detalle"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());

        verify(service, never()).create(any(RegistroAuditoria.class));
    }
}
