package pe.edu.utp.vacunacioncard.service.usuario.impl;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.repository.usuario.PacienteRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - PacienteServiceImpl")
class PacienteServiceImplTest {

    @Mock
    private PacienteRepository repo;

    @InjectMocks
    private PacienteServiceImpl service;

    private Paciente crearPaciente() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setDni("12345678");
        paciente.setNombreCompleto("Juan Pérez");
        return paciente;
    }

    @Test
    @DisplayName("Listar todos retorna la lista de pacientes")
    void listarTodos() {
        when(repo.findAll()).thenReturn(List.of(crearPaciente()));

        List<Paciente> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Obtener por ID retorna el paciente si existe")
    void obtenerPorId_existe() {
        Paciente paciente = crearPaciente();
        when(repo.findById(1L)).thenReturn(Optional.of(paciente));

        Optional<Paciente> resultado = service.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("12345678", resultado.get().getDni());
    }

    @Test
    @DisplayName("Obtener por ID retorna vacío si no existe")
    void obtenerPorId_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertTrue(service.obtenerPorId(99L).isEmpty());
    }

    @Test
    @DisplayName("Registrar guarda y retorna el paciente")
    void registrar() {
        Paciente paciente = crearPaciente();
        when(repo.save(paciente)).thenReturn(paciente);

        Paciente resultado = service.registrar(paciente);

        assertEquals(1L, resultado.getId());
        verify(repo).save(paciente);
    }

    @Test
    @DisplayName("Obtener por DNI retorna el paciente")
    void obtenerPorDni() {
        Paciente paciente = crearPaciente();
        when(repo.findByDni("12345678")).thenReturn(Optional.of(paciente));

        Optional<Paciente> resultado = service.obtenerPorDni("12345678");

        assertTrue(resultado.isPresent());
        assertEquals("Juan Pérez", resultado.get().getNombreCompleto());
    }

    @Test
    @DisplayName("Obtener por historia clínica retorna el paciente")
    void obtenerPorHistoriaClinica() {
        Paciente paciente = crearPaciente();
        paciente.setHistoriaClinicaId("HC-001");
        when(repo.findByHistoriaClinicaId("HC-001")).thenReturn(Optional.of(paciente));

        assertTrue(service.obtenerPorHistoriaClinica("HC-001").isPresent());
    }

    @Test
    @DisplayName("Listar por estado filtra correctamente")
    void listarPorEstado() {
        when(repo.findByActivo(true)).thenReturn(List.of(crearPaciente()));

        List<Paciente> resultado = service.listarPorEstado(true);

        assertEquals(1, resultado.size());
    }


        @Test
    @DisplayName("Registrar lanza ServiceException cuando falla la base de datos")
    void registrarDeberiaLanzarServiceExceptionCuandoFallaBaseDatos() {
        Paciente pacienteError = crearPaciente();

        when(repo.save(pacienteError))
            .thenThrow(new org.springframework.dao.DataRetrievalFailureException("Error simulado de DB"));

        assertThrows(ServiceException.class, () -> service.registrar(pacienteError));
    }

}
