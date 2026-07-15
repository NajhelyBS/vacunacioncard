package pe.edu.utp.vacunacioncard.service.cita.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.repository.cita.CitaVacunacionRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - CitaVacunacionServiceImpl")
class CitaVacunacionServiceImplTest {

    @Mock
    private CitaVacunacionRepository repo;

    @InjectMocks
    private CitaVacunacionServiceImpl service;

    private CitaVacunacion crearCita() {
        Paciente paciente = new Paciente();
        paciente.setId(5L);

        CitaVacunacion cita = new CitaVacunacion();
        cita.setId(1L);
        cita.setPaciente(paciente);
        cita.setFechaHora(LocalDateTime.of(2026, Month.AUGUST, 20, 10, 0));
        cita.setEstado("PROGRAMADA");
        return cita;
    }

    @Test
    @DisplayName("getAll retorna la lista de todas las citas médicas registradas")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearCita()));

        List<CitaVacunacion> resultado = service.getAll();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("findById retorna la cita si el identificador existe en el repositorio")
    void findById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearCita()));

        Optional<CitaVacunacion> resultado = service.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("PROGRAMADA", resultado.get().getEstado());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById devuelve Optional.empty si el identificador consultado no existe")
    void findById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<CitaVacunacion> resultado = service.findById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create almacena y retorna con éxito la nueva cita de vacunación")
    void create() {
        CitaVacunacion cita = crearCita();
        when(repo.save(cita)).thenReturn(cita);

        CitaVacunacion resultado = service.create(cita);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(cita);
    }

    @Test
    @DisplayName("create maneja anomalías de persistencia y propaga una ServiceException controlada")
    void create_lanzaServiceException() {
        CitaVacunacion cita = crearCita();
        when(repo.save(cita)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.create(cita));
        verify(repo, times(1)).save(cita);
    }

    @Test
    @DisplayName("update modifica y guarda la cita médica si el ID es válido y existe")
    void update_existe() {
        CitaVacunacion cita = crearCita();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(cita)).thenReturn(cita);

        CitaVacunacion resultado = service.update(cita);

        assertNotNull(resultado);
        assertEquals("PROGRAMADA", resultado.getEstado());
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(cita);
    }

    @Test
    @DisplayName("update interrumpe con ServiceException si el ID es nulo o no se encuentra en el repositorio")
    void update_noExiste() {
        CitaVacunacion cita = crearCita();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(cita));
        verify(repo, times(1)).existsById(1L);
        verify(repo, never()).save(any(CitaVacunacion.class));
    }

    @Test
    @DisplayName("update captura fallos mecánicos de la base de datos y lanza una ServiceException (Cubre bloque catch)")
    void update_fallaPersistencia() {
        CitaVacunacion cita = crearCita();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(cita)).thenThrow(new DataRetrievalFailureException("Error escritura"));

        assertThrows(ServiceException.class, () -> service.update(cita));
        verify(repo, times(1)).save(cita);
    }

    @Test
    @DisplayName("deleteById delega correctamente la remoción de la cita médica al repositorio")
    void deleteById() {
        doNothing().when(repo).deleteById(1L);

        service.deleteById(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById intercepta anomalías de datos y lanza una ServiceException")
    void deleteById_lanzaServiceException() {
        doThrow(new DataRetrievalFailureException("Fallo relacional simulado"))
                .when(repo).deleteById(1L);

        assertThrows(ServiceException.class, () -> service.deleteById(1L));
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("findByPatient retorna el historial de citas asociadas a un paciente específico")
    void findByPatient() {
        when(repo.findByPacienteId(5L)).thenReturn(List.of(crearCita()));

        List<CitaVacunacion> resultado = service.findByPatient(5L);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(5L, resultado.getFirst().getPaciente().getId());
        verify(repo, times(1)).findByPacienteId(5L);
    }

    @Test
    @DisplayName("findByStatus recupera los registros clínicos filtrados por estado operativo")
    void findByStatus() {
        when(repo.findByEstado("PROGRAMADA")).thenReturn(List.of(crearCita()));

        List<CitaVacunacion> resultado = service.findByStatus("PROGRAMADA");

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals("PROGRAMADA", resultado.getFirst().getEstado());
        verify(repo, times(1)).findByEstado("PROGRAMADA");
    }

    @Test
    @DisplayName("create maneja de forma idónea el flujo cuando la entidad Paciente es nula (Elimina amarillo del ternario)")
    void create_pacienteNulo() {
        CitaVacunacion citaSinPaciente = new CitaVacunacion();
        citaSinPaciente.setId(2L);
        citaSinPaciente.setPaciente(null);
        citaSinPaciente.setEstado("PENDIENTE");

        when(repo.save(any(CitaVacunacion.class))).thenReturn(citaSinPaciente);

        CitaVacunacion resultado = service.create(citaSinPaciente);

        assertNotNull(resultado);
        assertNull(resultado.getPaciente());
        verify(repo, times(1)).save(citaSinPaciente);
    }

    @Test
    @DisplayName("update lanza ServiceException de forma inmediata si el ID de la cita es nulo (Elimina amarillo del OR)")
    void update_idNulo() {
        CitaVacunacion citaConIdNulo = new CitaVacunacion();
        citaConIdNulo.setId(null);
        citaConIdNulo.setEstado("PROGRAMADA");

        assertThrows(ServiceException.class, () -> service.update(citaConIdNulo));

        verify(repo, never()).existsById(anyLong());
        verify(repo, never()).save(any(CitaVacunacion.class));
    }

}
