package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.Laboratorio;
import pe.edu.utp.vacunacioncard.repository.vacunacion.LaboratorioRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - LaboratorioServiceImpl")
class LaboratorioServiceImplTest {

    @Mock
    private LaboratorioRepository repo;

    @InjectMocks
    private LaboratorioServiceImpl service;

    private Laboratorio crearLaboratorio() {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNombre("Pfizer");
        laboratorio.setPaisOrigen("Estados Unidos");
        return laboratorio;
    }

    @Test
    @DisplayName("getAll retorna la lista completa de laboratorios")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearLaboratorio()));

        List<Laboratorio> resultado = service.getAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna el laboratorio si el identificador existe")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearLaboratorio()));

        Optional<Laboratorio> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Pfizer", resultado.get().getNombre());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById retorna Optional.empty si el identificador no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<Laboratorio> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create registra y retorna el nuevo laboratorio")
    void create() {
        Laboratorio laboratorio = crearLaboratorio();
        when(repo.save(laboratorio)).thenReturn(laboratorio);

        Laboratorio resultado = service.create(laboratorio);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(laboratorio);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        Laboratorio laboratorio = crearLaboratorio();
        when(repo.save(laboratorio)).thenThrow(new DataRetrievalFailureException("Error DB simulado"));

        assertThrows(ServiceException.class, () -> service.create(laboratorio));
        verify(repo, times(1)).save(laboratorio);
    }

    @Test
    @DisplayName("update modifica el laboratorio si su identificador existe")
    void update_existe() {
        Laboratorio laboratorio = crearLaboratorio();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(laboratorio)).thenReturn(laboratorio);

        Laboratorio resultado = service.update(laboratorio);

        assertNotNull(resultado);
        assertEquals("Pfizer", resultado.getNombre());
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(laboratorio);
    }

    @Test
    @DisplayName("update lanza ServiceException si el laboratorio a modificar no existe")
    void update_noExiste() {
        Laboratorio laboratorio = crearLaboratorio();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(laboratorio));
        verify(repo, times(1)).existsById(1L);
        verify(repo, never()).save(any(Laboratorio.class));
    }

    @Test
    @DisplayName("update propaga una ServiceException ante fallos de persistencia (cubre bloque catch)")
    void update_fallaPersistencia() {
        Laboratorio laboratorio = crearLaboratorio();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(laboratorio)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.update(laboratorio));
        verify(repo, times(1)).save(laboratorio);
    }

    @Test
    @DisplayName("deleteById delega la eliminación del laboratorio al repositorio")
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
    @DisplayName("findByCountry lista los laboratorios por país ignorando mayúsculas y minúsculas")
    void findByCountry() {
        when(repo.findByPaisOrigenIgnoreCase("Estados Unidos")).thenReturn(List.of(crearLaboratorio()));

        List<Laboratorio> resultado = service.findByCountry("Estados Unidos");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pfizer", resultado.getFirst().getNombre());
        verify(repo, times(1)).findByPaisOrigenIgnoreCase("Estados Unidos");
    }
}
