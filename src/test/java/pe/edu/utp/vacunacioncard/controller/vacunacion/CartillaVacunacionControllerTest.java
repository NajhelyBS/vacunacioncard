package pe.edu.utp.vacunacioncard.controller.vacunacion;

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
import pe.edu.utp.vacunacioncard.dto.vacunacion.CartillaVacunacionRequest;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import pe.edu.utp.vacunacioncard.service.vacunacion.ICartillaVacunacionService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller - CartillaVacunacionController")
class CartillaVacunacionControllerTest {

    @Mock
    private ICartillaVacunacionService service;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CartillaVacunacionController(service))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private CartillaVacunacion crearCartilla() {
        Paciente paciente = new Paciente();
        paciente.setId(2L);
        paciente.setNombreCompleto("Juan Pérez");

        CartillaVacunacion cartilla = new CartillaVacunacion();
        cartilla.setId(1L);
        cartilla.setPaciente(paciente);
        cartilla.setCodigoQR("QR-ABC-123");
        cartilla.setActiva(true);
        cartilla.setEstado("ACTIVA");
        return cartilla;
    }

    @Test
    @DisplayName("GET /api/cartillas lista todas las cartillas")
    void getAll() throws Exception {
        when(service.getAll()).thenReturn(List.of(crearCartilla()));

        mockMvc.perform(get("/api/cartillas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(service, times(1)).getAll();
    }

    @Test
    @DisplayName("GET /api/cartillas/{id} retorna la cartilla si existe")
    void getById_existe() throws Exception {
        when(service.getById(1L)).thenReturn(Optional.of(crearCartilla()));

        mockMvc.perform(get("/api/cartillas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/cartillas/{id} responde 404 si la cartilla no existe")
    void getById_noExiste() throws Exception {
        when(service.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cartillas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/cartillas/paciente/{id} retorna la cartilla del paciente")
    void getByPaciente_existe() throws Exception {
        when(service.findByPatient(2L)).thenReturn(Optional.of(crearCartilla()));

        mockMvc.perform(get("/api/cartillas/paciente/2"))
                .andExpect(status().isOk());

        verify(service, times(1)).findByPatient(2L);
    }

    @Test
    @DisplayName("GET /api/cartillas/paciente/{id} responde 404 si el paciente no tiene cartilla")
    void getByPaciente_noExiste() throws Exception {
        when(service.findByPatient(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cartillas/paciente/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/cartillas/qr/{codigo} retorna la cartilla identificada por su QR")
    void getByQr_existe() throws Exception {
        when(service.findByQrCode("QR-ABC-123")).thenReturn(Optional.of(crearCartilla()));

        mockMvc.perform(get("/api/cartillas/qr/QR-ABC-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/cartillas/qr/{codigo} responde 404 si el QR no corresponde a ninguna cartilla")
    void getByQr_noExiste() throws Exception {
        when(service.findByQrCode("QR-FALSO")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cartillas/qr/QR-FALSO"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/cartillas crea la cartilla y responde 201")
    void create_ok() throws Exception {
        when(service.create(any(CartillaVacunacion.class))).thenReturn(crearCartilla());

        mockMvc.perform(post("/api/cartillas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CartillaVacunacionRequest(2L, "QR-ABC-123", "ACTIVA", true))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        verify(service, times(1)).create(any(CartillaVacunacion.class));
    }

    @Test
    @DisplayName("PUT /api/cartillas/{id} actualiza la cartilla conservando el ID de la ruta")
    void update_ok() throws Exception {
        when(service.update(any(CartillaVacunacion.class))).thenReturn(crearCartilla());

        mockMvc.perform(put("/api/cartillas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CartillaVacunacionRequest(2L, "QR-ABC-123", "ACTIVA", true))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(service, times(1)).update(any(CartillaVacunacion.class));
    }

    @Test
    @DisplayName("PATCH /api/cartillas/{id}/desactivar realiza la baja lógica y responde 204")
    void deactivate_ok() throws Exception {
        doNothing().when(service).deactivate(1L);

        mockMvc.perform(patch("/api/cartillas/1/desactivar"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deactivate(1L);
    }

    @Test
    @DisplayName("PATCH /api/cartillas/{id}/desactivar responde 400 si la cartilla no existe")
    void deactivate_noExiste() throws Exception {
        doThrow(new ServiceException("No existe la cartilla con ID: 99", null))
                .when(service).deactivate(99L);

        mockMvc.perform(patch("/api/cartillas/99/desactivar"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}
