package pe.edu.utp.vacunacioncard.controller.cita;

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
import pe.edu.utp.vacunacioncard.dto.cita.CitaVacunacionRequest;
import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import pe.edu.utp.vacunacioncard.repository.campania.CentroVacunacionRepository;
import pe.edu.utp.vacunacioncard.repository.usuario.PacienteRepository;
import pe.edu.utp.vacunacioncard.repository.vacunacion.VacunaRepository;
import pe.edu.utp.vacunacioncard.service.cita.ICitaVacunacionService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller - CitaVacunacionController")
class CitaVacunacionControllerTest {

    @Mock
    private ICitaVacunacionService service;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private VacunaRepository vacunaRepository;

    @Mock
    private CentroVacunacionRepository centroVacunacionRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /** Fecha fija para garantizar pruebas deterministas. */
    private static final LocalDateTime FECHA_FIJA = LocalDateTime.of(2026, Month.AUGUST, 20, 9, 0);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CitaVacunacionController(
                        service, pacienteRepository, vacunaRepository, centroVacunacionRepository))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private CitaVacunacion crearCita() {
        Paciente paciente = new Paciente();
        paciente.setId(2L);
        paciente.setNombreCompleto("Juan Pérez");

        Vacuna vacuna = new Vacuna();
        vacuna.setId(3L);
        vacuna.setNombre("Influenza");

        CitaVacunacion cita = new CitaVacunacion();
        cita.setId(1L);
        cita.setPaciente(paciente);
        cita.setVacuna(vacuna);
        cita.setFechaHora(FECHA_FIJA);
        cita.setEstado("PROGRAMADA");
        return cita;
    }

    private CitaVacunacionRequest requestValido() {
        return new CitaVacunacionRequest(2L, 3L, FECHA_FIJA, null, "PROGRAMADA", "Sin observaciones");
    }

    @Test
    @DisplayName("GET /api/citas lista todas las citas programadas")
    void getAll() throws Exception {
        when(service.getAll()).thenReturn(List.of(crearCita()));

        mockMvc.perform(get("/api/citas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].pacienteNombre").value("Juan Pérez"))
                .andExpect(jsonPath("$[0].vacunaNombre").value("Influenza"));

        verify(service, times(1)).getAll();
    }

    @Test
    @DisplayName("GET /api/citas/{id} retorna la cita si existe")
    void getById_existe() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.of(crearCita()));

        mockMvc.perform(get("/api/citas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/citas/{id} responde 404 si la cita no existe")
    void getById_noExiste() throws Exception {
        when(service.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/citas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/citas/paciente/{id} lista las citas de un paciente")
    void getByPaciente() throws Exception {
        when(service.findByPatient(2L)).thenReturn(List.of(crearCita()));

        mockMvc.perform(get("/api/citas/paciente/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pacienteId").value(2));

        verify(service, times(1)).findByPatient(2L);
    }

    @Test
    @DisplayName("GET /api/citas/estado/{estado} lista las citas según su estado")
    void getByEstado() throws Exception {
        when(service.findByStatus("PROGRAMADA")).thenReturn(List.of(crearCita()));

        mockMvc.perform(get("/api/citas/estado/PROGRAMADA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("PROGRAMADA"));

        verify(service, times(1)).findByStatus("PROGRAMADA");
    }

    @Test
    @DisplayName("POST /api/citas programa la cita y responde 201 cuando las relaciones son válidas")
    void create_ok() throws Exception {
        when(pacienteRepository.existsById(2L)).thenReturn(true);
        when(vacunaRepository.existsById(3L)).thenReturn(true);
        when(service.create(any(CitaVacunacion.class))).thenReturn(crearCita());

        mockMvc.perform(post("/api/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        verify(service, times(1)).create(any(CitaVacunacion.class));
    }

    @Test
    @DisplayName("POST /api/citas responde 400 si el paciente no existe")
    void create_pacienteNoExiste() throws Exception {
        when(pacienteRepository.existsById(2L)).thenReturn(false);

        mockMvc.perform(post("/api/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());

        verify(service, never()).create(any(CitaVacunacion.class));
    }

    @Test
    @DisplayName("POST /api/citas responde 400 si la vacuna no existe")
    void create_vacunaNoExiste() throws Exception {
        when(pacienteRepository.existsById(2L)).thenReturn(true);
        when(vacunaRepository.existsById(3L)).thenReturn(false);

        mockMvc.perform(post("/api/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido())))
                .andExpect(status().isBadRequest());

        verify(service, never()).create(any(CitaVacunacion.class));
    }

    @Test
    @DisplayName("POST /api/citas responde 400 si el centro de vacunación indicado no existe")
    void create_centroNoExiste() throws Exception {
        when(pacienteRepository.existsById(2L)).thenReturn(true);
        when(vacunaRepository.existsById(3L)).thenReturn(true);
        when(centroVacunacionRepository.existsById(7L)).thenReturn(false);

        CitaVacunacionRequest conCentro =
                new CitaVacunacionRequest(2L, 3L, FECHA_FIJA, 7L, "PROGRAMADA", null);

        mockMvc.perform(post("/api/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conCentro)))
                .andExpect(status().isBadRequest());

        verify(service, never()).create(any(CitaVacunacion.class));
    }

    @Test
    @DisplayName("PUT /api/citas/{id} reprograma la cita conservando el ID de la ruta")
    void update_ok() throws Exception {
        when(pacienteRepository.existsById(2L)).thenReturn(true);
        when(vacunaRepository.existsById(3L)).thenReturn(true);
        when(service.update(any(CitaVacunacion.class))).thenReturn(crearCita());

        mockMvc.perform(put("/api/citas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(service, times(1)).update(any(CitaVacunacion.class));
    }

    @Test
    @DisplayName("PUT /api/citas/{id} responde 400 si las relaciones son inválidas")
    void update_relacionInvalida() throws Exception {
        when(pacienteRepository.existsById(2L)).thenReturn(false);

        mockMvc.perform(put("/api/citas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido())))
                .andExpect(status().isBadRequest());

        verify(service, never()).update(any(CitaVacunacion.class));
    }

    @Test
    @DisplayName("DELETE /api/citas/{id} cancela la cita y responde 204")
    void deleteById() throws Exception {
        doNothing().when(service).deleteById(1L);

        mockMvc.perform(delete("/api/citas/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteById(1L);
    }
}
