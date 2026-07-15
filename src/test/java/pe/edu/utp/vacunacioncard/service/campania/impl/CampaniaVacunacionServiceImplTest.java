package pe.edu.utp.vacunacioncard.service.campania.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import pe.edu.utp.vacunacioncard.repository.campania.CampaniaVacunacionRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - CampaniaVacunacionServiceImpl")
class CampaniaVacunacionServiceImplTest {

    @Mock
    private CampaniaVacunacionRepository repo;

    @InjectMocks
    private CampaniaVacunacionServiceImpl service;

    private CampaniaVacunacion crearCampania() {
        CampaniaVacunacion campania = new CampaniaVacunacion();
        campania.setId(1L);
        campania.setNombre("Vacunación Escolar 2026");
        campania.setFechaInicio(LocalDate.of(2026, Month.MARCH, 1));
        campania.setFechaFin(LocalDate.of(2026, Month.MARCH, 31));
        campania.setMetaVacunacion(500);
        campania.setEstado("PLANEADA");
        return campania;
    }

    @Test
    @DisplayName("getAll retorna la lista de todas las campañas de vacunación registradas")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearCampania()));

        List<CampaniaVacunacion> resultado = service.getAll();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna la campaña si el ID se encuentra en el repositorio")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearCampania()));

        Optional<CampaniaVacunacion> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Vacunación Escolar 2026", resultado.get().getNombre());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById devuelve Optional.empty si el identificador no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<CampaniaVacunacion> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create almacena y retorna de manera exitosa la nueva campaña")
    void create() {
        CampaniaVacunacion campania = crearCampania();
        when(repo.save(campania)).thenReturn(campania);

        CampaniaVacunacion resultado = service.create(campania);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(campania);
    }

    @Test
    @DisplayName("create interrumpe el flujo y lanza una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        CampaniaVacunacion campania = crearCampania();
        when(repo.save(campania)).thenThrow(new DataRetrievalFailureException("Fallo de red simulado"));

        assertThrows(ServiceException.class, () -> service.create(campania));
        verify(repo, times(1)).save(campania);
    }

    @Test
    @DisplayName("update modifica y guarda los valores si el ID existe en el sistema")
    void update_existe() {
        CampaniaVacunacion campania = crearCampania();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(campania)).thenReturn(campania);

        CampaniaVacunacion resultado = service.update(campania);

        assertNotNull(resultado);
        assertEquals("Vacunación Escolar 2026", resultado.getNombre());
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(campania);
    }

    @Test
    @DisplayName("update interrumpe con ServiceException si el ID provisto no existe en los registros")
    void update_noExiste() {
        CampaniaVacunacion campania = crearCampania();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(campania));
        verify(repo, times(1)).existsById(1L);
        verify(repo, never()).save(any(CampaniaVacunacion.class));
    }

    @Test
    @DisplayName("update captura anomalías de datos de BD y lanza una ServiceException controlada")
    void update_fallaPersistencia() {
        CampaniaVacunacion campania = crearCampania();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(campania)).thenThrow(new DataRetrievalFailureException("Error de escritura"));

        assertThrows(ServiceException.class, () -> service.update(campania));
        verify(repo, times(1)).save(campania);
    }

    @Test
    @DisplayName("deleteById delega correctamente la remoción de la campaña al repositorio")
    void deleteById() {
        doNothing().when(repo).deleteById(1L);

        service.deleteById(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById envuelve fallos de acceso a datos en una ServiceException")
    void deleteById_lanzaServiceException() {
        doThrow(new DataRetrievalFailureException("Fallo relacional de llave externa"))
                .when(repo).deleteById(1L);

        assertThrows(ServiceException.class, () -> service.deleteById(1L));
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("findByStatus lista de forma correcta las campañas usando la optimización getFirst de Java 21")
    void findByStatus() {
        when(repo.findByEstado("PLANEADA")).thenReturn(List.of(crearCampania()));

        List<CampaniaVacunacion> resultado = service.findByStatus("PLANEADA");

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals("PLANEADA", resultado.getFirst().getEstado());
        verify(repo, times(1)).findByEstado("PLANEADA");
    }
}
