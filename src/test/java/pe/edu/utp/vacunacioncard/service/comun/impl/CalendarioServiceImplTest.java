package pe.edu.utp.vacunacioncard.service.comun.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.comun.Calendario;
import pe.edu.utp.vacunacioncard.repository.comun.CalendarioRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - CalendarioServiceImpl")
class CalendarioServiceImplTest {

    @Mock
    private CalendarioRepository repo;

    @InjectMocks
    private CalendarioServiceImpl service;

    /** Fecha fija para garantizar pruebas deterministas e independientes del día de ejecución. */
    private static final LocalDate FECHA_FIJA = LocalDate.of(2026, Month.JULY, 28);

    private Calendario crearCalendario() {
        Calendario calendario = new Calendario();
        calendario.setId(1L);
        calendario.setFecha(FECHA_FIJA);
        calendario.setEsHabil(false);
        calendario.setDescripcionFeriado("Fiestas Patrias");
        return calendario;
    }

    @Test
    @DisplayName("create guarda y retorna el nuevo día registrado en el calendario")
    void create() {
        Calendario calendario = crearCalendario();
        when(repo.save(calendario)).thenReturn(calendario);

        Calendario resultado = service.create(calendario);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(FECHA_FIJA, resultado.getFecha());
        verify(repo, times(1)).save(calendario);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        Calendario calendario = crearCalendario();
        when(repo.save(calendario)).thenThrow(new DataRetrievalFailureException("Error DB simulado"));

        assertThrows(ServiceException.class, () -> service.create(calendario));
        verify(repo, times(1)).save(calendario);
    }

    @Test
    @DisplayName("findByDate retorna la configuración del día si la fecha existe")
    void findByDate_existe() {
        when(repo.findByFecha(FECHA_FIJA)).thenReturn(Optional.of(crearCalendario()));

        Optional<Calendario> resultado = service.findByDate(FECHA_FIJA);

        assertTrue(resultado.isPresent());
        assertEquals("Fiestas Patrias", resultado.get().getDescripcionFeriado());
        verify(repo, times(1)).findByFecha(FECHA_FIJA);
    }

    @Test
    @DisplayName("findByDate retorna Optional.empty si la fecha no está registrada")
    void findByDate_noExiste() {
        LocalDate fechaInexistente = LocalDate.of(2026, Month.JANUARY, 15);
        when(repo.findByFecha(fechaInexistente)).thenReturn(Optional.empty());

        Optional<Calendario> resultado = service.findByDate(fechaInexistente);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findByFecha(fechaInexistente);
    }

    @Test
    @DisplayName("findByAvailability filtra los días no hábiles (feriados)")
    void findByAvailability_noHabiles() {
        when(repo.findByEsHabil(false)).thenReturn(List.of(crearCalendario()));

        List<Calendario> resultado = service.findByAvailability(false);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertFalse(resultado.getFirst().isEsHabil());
        verify(repo, times(1)).findByEsHabil(false);
    }

    @Test
    @DisplayName("findByAvailability retorna una lista vacía si no hay días hábiles registrados")
    void findByAvailability_habiles() {
        when(repo.findByEsHabil(true)).thenReturn(List.of());

        List<Calendario> resultado = service.findByAvailability(true);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findByEsHabil(true);
    }

    @Test
    @DisplayName("deleteById delega la eliminación del día al repositorio")
    void deleteById() {
        doNothing().when(repo).deleteById(1L);

        service.deleteById(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById intercepta fallos de acceso y lanza una ServiceException controlada")
    void deleteById_lanzaServiceException() {
        doThrow(new DataRetrievalFailureException("Error borrado simulado"))
                .when(repo).deleteById(1L);

        assertThrows(ServiceException.class, () -> service.deleteById(1L));
        verify(repo, times(1)).deleteById(1L);
    }
}
