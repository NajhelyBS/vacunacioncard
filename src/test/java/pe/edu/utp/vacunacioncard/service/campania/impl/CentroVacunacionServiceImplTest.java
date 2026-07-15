package pe.edu.utp.vacunacioncard.service.campania.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.campania.CentroVacunacion;
import pe.edu.utp.vacunacioncard.repository.campania.CentroVacunacionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - CentroVacunacionServiceImpl")
class CentroVacunacionServiceImplTest {

    @Mock
    private CentroVacunacionRepository repo;

    @InjectMocks
    private CentroVacunacionServiceImpl service;

    private CentroVacunacion crearCentro() {
        CentroVacunacion centro = new CentroVacunacion();
        centro.setId(1L);
        centro.setNombre("Centro de Salud Miraflores");
        centro.setCapacidadDiaria(200);
        centro.setPersonalDisponible(15);
        centro.setActivo(true);
        return centro;
    }

    @Test
    @DisplayName("getAll retorna la lista de todos los centros registrados")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearCentro()));

        List<CentroVacunacion> resultado = service.getAll();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna el centro si el ID existe en el repositorio")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearCentro()));

        Optional<CentroVacunacion> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Centro de Salud Miraflores", resultado.get().getNombre());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById devuelve Optional.empty si el identificador no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<CentroVacunacion> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create almacena y retorna con éxito el nuevo centro de vacunación")
    void create() {
        CentroVacunacion centro = crearCentro();
        when(repo.save(centro)).thenReturn(centro);

        CentroVacunacion resultado = service.create(centro);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(centro);
    }

    @Test
    @DisplayName("create captura anomalías de persistencia y propaga una ServiceException")
    void create_lanzaServiceException() {
        CentroVacunacion centro = crearCentro();
        when(repo.save(centro)).thenThrow(new DataRetrievalFailureException("Error persistencia de DB"));

        assertThrows(ServiceException.class, () -> service.create(centro));
        verify(repo, times(1)).save(centro);
    }

    @Test
    @DisplayName("update modifica y guarda los valores si el ID existe en el sistema")
    void update_existe() {
        CentroVacunacion centro = crearCentro();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(centro)).thenReturn(centro);

        CentroVacunacion resultado = service.update(centro);

        assertNotNull(resultado);
        assertEquals("Centro de Salud Miraflores", resultado.getNombre());
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(centro);
    }

    @Test
    @DisplayName("update interrumpe con ServiceException si el ID provisto no existe")
    void update_noExiste() {
        CentroVacunacion centro = crearCentro();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(centro));
        verify(repo, times(1)).existsById(1L);
        verify(repo, never()).save(any(CentroVacunacion.class));
    }

    @Test
    @DisplayName("update captura anomalías de datos relacionales y lanza una ServiceException")
    void update_fallaPersistencia() {
        CentroVacunacion centro = crearCentro();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(centro)).thenThrow(new DataRetrievalFailureException("Error de escritura"));

        assertThrows(ServiceException.class, () -> service.update(centro));
        verify(repo, times(1)).save(centro);
    }

    @Test
    @DisplayName("deleteById delega correctamente la remoción de la sede al repositorio")
    void deleteById() {
        doNothing().when(repo).deleteById(1L);

        service.deleteById(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById envuelve fallos mecánicos de la BD en una ServiceException")
    void deleteById_lanzaServiceException() {
        doThrow(new DataRetrievalFailureException("Fallo de clave simulado"))
                .when(repo).deleteById(1L);

        assertThrows(ServiceException.class, () -> service.deleteById(1L));
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("findByStatus lista de forma idónea las sedes filtradas por vigencia booleana")
    void findByStatus() {
        when(repo.findByActivo(true)).thenReturn(List.of(crearCentro()));

        List<CentroVacunacion> resultado = service.findByStatus(true);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        // SOLUCIÓN: Cambiado isActiva() por isActivo() de Lombok
        assertTrue(resultado.getFirst().isActivo());
        verify(repo, times(1)).findByActivo(true);
    }

    @Test
    @DisplayName("searchByName recupera de forma idónea las coincidencias de texto de búsqueda")
    void searchByName() {
        String queryBusqueda = "Miraflores";
        when(repo.findByNombreContainingIgnoreCase(queryBusqueda)).thenReturn(List.of(crearCentro()));

        List<CentroVacunacion> resultado = service.searchByName(queryBusqueda);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertTrue(resultado.getFirst().getNombre().contains(queryBusqueda));
        verify(repo, times(1)).findByNombreContainingIgnoreCase(queryBusqueda);
    }
}
