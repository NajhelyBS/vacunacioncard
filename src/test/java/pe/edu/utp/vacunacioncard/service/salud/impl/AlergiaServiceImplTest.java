package pe.edu.utp.vacunacioncard.service.salud.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import pe.edu.utp.vacunacioncard.repository.salud.AlergiaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - AlergiaServiceImpl")
class AlergiaServiceImplTest {

    @Mock
    private AlergiaRepository repo;

    @InjectMocks
    private AlergiaServiceImpl service;

    /**
     * Helper metodológico para aislar la creación de objetos mocked sin duplicar literales.
     */
    private Alergia crearAlergia() {
        Alergia alergia = new Alergia();
        alergia.setId(1L);
        alergia.setNombre("Penicilina");
        alergia.setSeveridad("ALTA");
        return alergia;
    }

    @Test
    @DisplayName("getAll retorna la lista completa de alergias registradas")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearAlergia()));

        List<Alergia> resultado = service.getAll();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna la entidad Alergia si existe en base de datos")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearAlergia()));

        Optional<Alergia> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Penicilina", resultado.get().getNombre());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById retorna una estructura vacía Optional.empty si el identificador no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<Alergia> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create almacena y retorna exitosamente la nueva alergia")
    void create() {
        Alergia alergia = crearAlergia();
        when(repo.save(alergia)).thenReturn(alergia);

        Alergia resultado = service.create(alergia);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(alergia);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos transaccionales de persistencia")
    void create_lanzaServiceException() {
        Alergia alergia = crearAlergia();
        when(repo.save(alergia)).thenThrow(new DataRetrievalFailureException("Fallo de conexión simulado"));

        assertThrows(ServiceException.class, () -> service.create(alergia));
        verify(repo, times(1)).save(alergia);
    }

    @Test
    @DisplayName("update modifica y guarda la alergia de forma correcta si su ID existe")
    void update_existe() {
        Alergia alergia = crearAlergia();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(alergia)).thenReturn(alergia);

        Alergia resultado = service.update(alergia);

        assertNotNull(resultado);
        assertEquals("Penicilina", resultado.getNombre());
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(alergia);
    }

    @Test
    @DisplayName("update interrumpe el flujo con ServiceException si el identificador a modificar no existe")
    void update_noExiste() {
        Alergia alergia = crearAlergia();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(alergia));
        verify(repo, times(1)).existsById(1L);
        verify(repo, never()).save(any(Alergia.class));
    }

    @Test
    @DisplayName("deleteById delega correctamente el borrado al repositorio")
    void deleteById() {
        doNothing().when(repo).deleteById(1L);

        service.deleteById(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById intercepta anomalías de acceso y lanza una ServiceException controlada")
    void deleteById_lanzaServiceException() {
        doThrow(new DataRetrievalFailureException("Error catastrófico simulado"))
                .when(repo).deleteById(1L);

        assertThrows(ServiceException.class, () -> service.deleteById(1L));
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("findBySeverity recupera los registros ignorando mayúsculas y minúsculas")
    void findBySeverity() {
        when(repo.findBySeveridadIgnoreCase("ALTA")).thenReturn(List.of(crearAlergia()));

        List<Alergia> resultado = service.findBySeverity("ALTA");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Penicilina", resultado.get(0).getNombre());
        verify(repo, times(1)).findBySeveridadIgnoreCase("ALTA");
    }
}
