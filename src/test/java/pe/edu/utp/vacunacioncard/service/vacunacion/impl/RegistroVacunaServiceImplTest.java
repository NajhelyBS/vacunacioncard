package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.repository.vacunacion.RegistroVacunaRepository;
import pe.edu.utp.vacunacioncard.service.patron.observer.NotificadorVacunacionConcreto;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - RegistroVacunaServiceImpl")
class RegistroVacunaServiceImplTest {

    @Mock
    private RegistroVacunaRepository repo;

    @Mock
    private NotificadorVacunacionConcreto notificador;

    @InjectMocks
    private RegistroVacunaServiceImpl service;

    private static final LocalDateTime PROXIMA_DOSIS_FIJA = LocalDateTime.of(2026, Month.SEPTEMBER, 1, 10, 0);

    private RegistroVacuna crearRegistro() {
        RegistroVacuna registro = new RegistroVacuna();
        registro.setId(1L);
        registro.setNumeroDosis(1);
        registro.setLote("LOTE-ABC-123");
        return registro;
    }

    @Test
    @DisplayName("getAll retorna todos los registros de dosis aplicadas")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearRegistro()));

        List<RegistroVacuna> resultado = service.getAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna el registro de dosis si el identificador existe")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearRegistro()));

        Optional<RegistroVacuna> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("LOTE-ABC-123", resultado.get().getLote());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById retorna Optional.empty si el identificador no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<RegistroVacuna> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create guarda la dosis y notifica el evento DOSIS_APLICADA del Observer")
    void create_sinProximaDosis() {
        RegistroVacuna registro = crearRegistro();
        when(repo.save(registro)).thenReturn(registro);

        RegistroVacuna resultado = service.create(registro);

        assertNotNull(resultado);
        verify(repo, times(1)).save(registro);
        verify(notificador, times(1)).dosisAplicada(registro);
        verify(notificador, never()).programarProximaDosis(any(RegistroVacuna.class));
    }

    @Test
    @DisplayName("create también notifica PROXIMA_DOSIS cuando la dosis tiene fecha programada")
    void create_conProximaDosis() {
        RegistroVacuna registro = crearRegistro();
        registro.setProximaDosis(PROXIMA_DOSIS_FIJA);
        when(repo.save(registro)).thenReturn(registro);

        service.create(registro);

        verify(notificador, times(1)).dosisAplicada(registro);
        verify(notificador, times(1)).programarProximaDosis(registro);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        RegistroVacuna registro = crearRegistro();
        when(repo.save(registro)).thenThrow(new DataRetrievalFailureException("Error DB simulado"));

        assertThrows(ServiceException.class, () -> service.create(registro));
        verify(notificador, never()).dosisAplicada(any(RegistroVacuna.class));
    }

    @Test
    @DisplayName("update modifica el registro si su identificador existe")
    void update_existe() {
        RegistroVacuna registro = crearRegistro();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(registro)).thenReturn(registro);

        RegistroVacuna resultado = service.update(registro);

        assertNotNull(resultado);
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(registro);
    }

    @Test
    @DisplayName("update lanza ServiceException si el registro a modificar no existe")
    void update_noExiste() {
        RegistroVacuna registro = crearRegistro();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(registro));
        verify(repo, never()).save(any(RegistroVacuna.class));
    }

    @Test
    @DisplayName("update propaga una ServiceException ante fallos de persistencia (cubre bloque catch)")
    void update_fallaPersistencia() {
        RegistroVacuna registro = crearRegistro();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(registro)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.update(registro));
        verify(repo, times(1)).save(registro);
    }

    @Test
    @DisplayName("deleteById delega la eliminación del registro al repositorio")
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

    @Test
    @DisplayName("findByBatch lista los registros por lote ignorando mayúsculas y minúsculas")
    void findByBatch() {
        when(repo.findByLoteIgnoreCase("LOTE-ABC-123")).thenReturn(List.of(crearRegistro()));

        List<RegistroVacuna> resultado = service.findByBatch("LOTE-ABC-123");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("LOTE-ABC-123", resultado.getFirst().getLote());
        verify(repo, times(1)).findByLoteIgnoreCase("LOTE-ABC-123");
    }

    @Test
    @DisplayName("findByNurse lista los registros aplicados por un enfermero específico")
    void findByNurse() {
        when(repo.findByEnfermeroAplicadorId(7L)).thenReturn(List.of(crearRegistro()));

        List<RegistroVacuna> resultado = service.findByNurse(7L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findByEnfermeroAplicadorId(7L);
    }
}
