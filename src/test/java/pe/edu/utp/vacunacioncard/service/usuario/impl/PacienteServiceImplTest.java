package pe.edu.utp.vacunacioncard.service.usuario.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
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
    @DisplayName("getAll retorna la lista de pacientes")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearPaciente()));

        List<Paciente> resultado = service.getAll();

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("getById retorna el paciente si existe")
    void getById_existe() {
        Paciente paciente = crearPaciente();
        when(repo.findById(1L)).thenReturn(Optional.of(paciente));

        Optional<Paciente> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("12345678", resultado.get().getDni());
    }

    @Test
    @DisplayName("getById retorna vacío si no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertTrue(service.getById(99L).isEmpty());
    }

    @Test
    @DisplayName("create guarda y retorna el paciente")
    void create() {
        Paciente paciente = crearPaciente();
        when(repo.save(paciente)).thenReturn(paciente);

        Paciente resultado = service.create(paciente);

        assertEquals(1L, resultado.getId());
        verify(repo).save(paciente);
    }

    @Test
    @DisplayName("create lanza ServiceException cuando falla la base de datos")
    void create_lanzaServiceException() {
        Paciente pacienteError = crearPaciente();

        when(repo.save(pacienteError))
                .thenThrow(new org.springframework.dao.DataRetrievalFailureException("Error simulado de DB"));

        assertThrows(ServiceException.class, () -> service.create(pacienteError));
    }

    @Test
    @DisplayName("update actualiza y retorna el paciente si existe")
    void update_existe() {
        Paciente paciente = crearPaciente();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(paciente)).thenReturn(paciente);

        Paciente resultado = service.update(paciente);

        assertEquals("12345678", resultado.getDni());
        verify(repo).save(paciente);
    }

    @Test
    @DisplayName("update lanza ServiceException si el paciente no existe")
    void update_noExiste() {
        Paciente paciente = crearPaciente();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(paciente));
        verify(repo, never()).save(any());
    }

    @Test
    @DisplayName("deleteById elimina el paciente")
    void deleteById() {
        doNothing().when(repo).deleteById(1L);

        service.deleteById(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById lanza ServiceException si falla la base de datos")
    void deleteById_lanzaServiceException() {
        doThrow(new org.springframework.dao.DataRetrievalFailureException("Error simulado de DB"))
                .when(repo).deleteById(1L);

        assertThrows(ServiceException.class, () -> service.deleteById(1L));
    }

    @Test
    @DisplayName("findByDni retorna el paciente")
    void findByDni() {
        Paciente paciente = crearPaciente();
        when(repo.findByDni("12345678")).thenReturn(Optional.of(paciente));

        Optional<Paciente> resultado = service.findByDni("12345678");

        assertTrue(resultado.isPresent());
        assertEquals("Juan Pérez", resultado.get().getNombreCompleto());
    }

    @Test
    @DisplayName("findByHistoriaClinicaId retorna el paciente")
    void findByHistoriaClinicaId() {
        Paciente paciente = crearPaciente();
        paciente.setHistoriaClinicaId("HC-001");
        when(repo.findByHistoriaClinicaId("HC-001")).thenReturn(Optional.of(paciente));

        assertTrue(service.findByHistoriaClinicaId("HC-001").isPresent());
    }

    @Test
    @DisplayName("findByStatus filtra correctamente")
    void findByStatus() {
        when(repo.findByActivo(true)).thenReturn(List.of(crearPaciente()));

        List<Paciente> resultado = service.findByStatus(true);

        assertEquals(1, resultado.size());
    }
}