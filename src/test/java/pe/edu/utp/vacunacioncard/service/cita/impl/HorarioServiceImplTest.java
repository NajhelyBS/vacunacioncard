package pe.edu.utp.vacunacioncard.service.cita.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import pe.edu.utp.vacunacioncard.repository.cita.HorarioRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - HorarioServiceImpl")
class HorarioServiceImplTest {

    @Mock
    private HorarioRepository repo;

    @InjectMocks
    private HorarioServiceImpl service;

    private Horario crearHorario() {
        Horario horario = new Horario();
        horario.setId(1L);
        horario.setDiaSemana(DiaSemana.LUNES);
        horario.setHoraInicio(LocalTime.of(8, 0));
        horario.setHoraFin(LocalTime.of(12, 0));
        return horario;
    }

    @Test
    @DisplayName("getAll retorna la lista de todos los horarios operativos registrados")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearHorario()));

        List<Horario> resultado = service.getAll();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("findById retorna el bloque de horario si el ID existe en el repositorio")
    void findById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearHorario()));

        Optional<Horario> resultado = service.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(DiaSemana.LUNES, resultado.get().getDiaSemana());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById devuelve Optional.empty si el identificador consultado no existe")
    void findById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<Horario> resultado = service.findById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create almacena y retorna con éxito el nuevo horario de atención")
    void create() {
        Horario horario = crearHorario();
        when(repo.save(horario)).thenReturn(horario);

        Horario resultado = service.create(horario);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(horario);
    }

    @Test
    @DisplayName("create captura anomalías transaccionales y lanza una ServiceException")
    void create_lanzaServiceException() {
        Horario horario = crearHorario();
        when(repo.save(horario)).thenThrow(new DataRetrievalFailureException("Error DB"));

        assertThrows(ServiceException.class, () -> service.create(horario));
        verify(repo, times(1)).save(horario);
    }

    @Test
    @DisplayName("update modifica y guarda el bloque de horario si el ID es válido y existe")
    void update_existe() {
        Horario horario = crearHorario();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(horario)).thenReturn(horario);

        Horario resultado = service.update(horario);

        assertNotNull(resultado);
        assertEquals(LocalTime.of(8, 0), resultado.getHoraInicio());
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(horario);
    }

    @Test
    @DisplayName("update interrumpe con ServiceException si el ID es nulo o no existe en la base de datos")
    void update_noExiste() {
        Horario horario = crearHorario();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(horario));
        verify(repo, times(1)).existsById(1L);
        verify(repo, never()).save(any(Horario.class));
    }

    @Test
    @DisplayName("update captura anomalías mecánicas de la BD y propaga una ServiceException (Cubre bloque catch)")
    void update_fallaPersistencia() {
        Horario horario = crearHorario();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(horario)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.update(horario));
        verify(repo, times(1)).save(horario);
    }

    @Test
    @DisplayName("deleteById delega correctamente la remoción física del horario al repositorio")
    void deleteById() {
        doNothing().when(repo).deleteById(1L);

        service.deleteById(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById maneja fallos de base de datos y lanza una ServiceException controlada")
    void deleteById_lanzaServiceException() {
        doThrow(new DataRetrievalFailureException("Fallo borrado simulado"))
                .when(repo).deleteById(1L);

        assertThrows(ServiceException.class, () -> service.deleteById(1L));
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("findByDay filtra de forma correcta los turnos según el día de la semana usando getFirst de Java 21")
    void findByDay() {
        when(repo.findByDiaSemana(DiaSemana.LUNES)).thenReturn(List.of(crearHorario()));

        List<Horario> resultado = service.findByDay(DiaSemana.LUNES);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(DiaSemana.LUNES, resultado.getFirst().getDiaSemana());
        verify(repo, times(1)).findByDiaSemana(DiaSemana.LUNES);
    }
}
