package pe.edu.utp.vacunacioncard.controller.notificacion;

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
import pe.edu.utp.vacunacioncard.dto.notificacion.AlertaRequest;
import pe.edu.utp.vacunacioncard.dto.notificacion.RecordatorioRequest;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionSistema;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.repository.usuario.UsuarioRepository;
import pe.edu.utp.vacunacioncard.repository.vacunacion.RegistroVacunaRepository;
import pe.edu.utp.vacunacioncard.service.notificacion.INotificacionService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller - NotificacionController")
class NotificacionControllerTest {

    @Mock
    private INotificacionService notificacionService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RegistroVacunaRepository registroVacunaRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /** Fecha fija para garantizar pruebas deterministas. */
    private static final LocalDateTime FECHA_FIJA = LocalDateTime.of(2026, Month.SEPTEMBER, 1, 10, 0);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new NotificacionController(
                        notificacionService, usuarioRepository, registroVacunaRepository))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private Usuario crearUsuario() {
        Enfermero enfermero = new Enfermero();
        enfermero.setId(1L);
        return enfermero;
    }

    private Notificacion crearNotificacion() {
        NotificacionSistema notif = new NotificacionSistema(crearUsuario(), "Mensaje de prueba", "ALERTA");
        notif.setEstado("ENVIADA");
        return notif;
    }

    @Test
    @DisplayName("GET /api/notificaciones/usuario/{id} lista todas las notificaciones del usuario")
    void findByUser_sinEstado() throws Exception {
        when(notificacionService.findByUser(1L)).thenReturn(List.of(crearNotificacion()));

        mockMvc.perform(get("/api/notificaciones/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mensaje").value("Mensaje de prueba"))
                .andExpect(jsonPath("$[0].destinatarioId").value(1));

        verify(notificacionService, times(1)).findByUser(1L);
        verify(notificacionService, never()).findByUserAndStatus(anyLong(), anyString());
    }

    @Test
    @DisplayName("GET /api/notificaciones/usuario/{id}?estado= filtra por estado cuando se envía el parámetro")
    void findByUser_conEstado() throws Exception {
        when(notificacionService.findByUserAndStatus(1L, "ENVIADA")).thenReturn(List.of(crearNotificacion()));

        mockMvc.perform(get("/api/notificaciones/usuario/1").param("estado", "ENVIADA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("ENVIADA"));

        verify(notificacionService, times(1)).findByUserAndStatus(1L, "ENVIADA");
        verify(notificacionService, never()).findByUser(anyLong());
    }

    @Test
    @DisplayName("GET /api/notificaciones/usuario/{id}?estado= vacío usa el listado completo")
    void findByUser_estadoEnBlanco() throws Exception {
        when(notificacionService.findByUser(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/notificaciones/usuario/1").param("estado", "  "))
                .andExpect(status().isOk());

        verify(notificacionService, times(1)).findByUser(1L);
    }

    @Test
    @DisplayName("PATCH /api/notificaciones/{id}/leida marca la notificación como leída")
    void markAsRead_ok() throws Exception {
        Notificacion leida = crearNotificacion();
        leida.setEstado("LEIDA");
        when(notificacionService.markAsRead(5L)).thenReturn(leida);

        mockMvc.perform(patch("/api/notificaciones/5/leida"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("LEIDA"));

        verify(notificacionService, times(1)).markAsRead(5L);
    }

    @Test
    @DisplayName("PATCH /api/notificaciones/{id}/leida responde 400 si la notificación no existe")
    void markAsRead_noExiste() throws Exception {
        when(notificacionService.markAsRead(99L))
                .thenThrow(new ServiceException("No existe una notificación con ID: 99", null));

        mockMvc.perform(patch("/api/notificaciones/99/leida"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("POST /api/notificaciones/alerta registra la alerta mediante el Factory Method y responde 201")
    void registerAlert_ok() throws Exception {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(crearUsuario()));

        mockMvc.perform(post("/api/notificaciones/alerta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AlertaRequest(1L, "Alerta importante"))))
                .andExpect(status().isCreated());

        verify(notificacionService, times(1)).registerAlert(any(Usuario.class), eq("Alerta importante"));
    }

    @Test
    @DisplayName("POST /api/notificaciones/alerta responde 400 si el usuario destinatario no existe")
    void registerAlert_usuarioNoExiste() throws Exception {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/notificaciones/alerta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AlertaRequest(99L, "Alerta"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());

        verify(notificacionService, never()).registerAlert(any(Usuario.class), anyString());
    }

    @Test
    @DisplayName("POST /api/notificaciones/recordatorio registra el recordatorio y responde 201")
    void registerRecordatorio_ok() throws Exception {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(crearUsuario()));
        when(registroVacunaRepository.findById(3L)).thenReturn(Optional.of(new RegistroVacuna()));

        mockMvc.perform(post("/api/notificaciones/recordatorio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RecordatorioRequest(1L, 3L, FECHA_FIJA))))
                .andExpect(status().isCreated());

        verify(notificacionService, times(1))
                .registerVaccineReminder(any(Usuario.class), any(RegistroVacuna.class), eq(FECHA_FIJA));
    }

    @Test
    @DisplayName("POST /api/notificaciones/recordatorio responde 400 si el registro de vacuna no existe")
    void registerRecordatorio_registroNoExiste() throws Exception {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(crearUsuario()));
        when(registroVacunaRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/notificaciones/recordatorio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RecordatorioRequest(1L, 99L, FECHA_FIJA))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());

        verify(notificacionService, never())
                .registerVaccineReminder(any(Usuario.class), any(RegistroVacuna.class), any(LocalDateTime.class));
    }
}
