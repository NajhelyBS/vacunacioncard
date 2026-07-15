package pe.edu.utp.vacunacioncard.service.usuario.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.repository.usuario.EnfermeroRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - EnfermeroServiceImpl")
class EnfermeroServiceImplTest {

    @Mock
    private EnfermeroRepository repo;

    @InjectMocks
    private EnfermeroServiceImpl service;

    private Enfermero crearEnfermero() {
        Enfermero enfermero = new Enfermero();
        enfermero.setId(1L);
        enfermero.setNombreCompleto("María Torres");
        enfermero.setColegiatura("CEP-12345");
        enfermero.setCentroTrabajo("Centro Norte");
        return enfermero;
    }

    @Test
    @DisplayName("getAll retorna la lista completa de enfermeros")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearEnfermero()));

        List<Enfermero> resultado = service.getAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna el enfermero si el identificador existe")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearEnfermero()));

        Optional<Enfermero> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("CEP-12345", resultado.get().getColegiatura());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById retorna Optional.empty si el identificador no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<Enfermero> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create registra y retorna el nuevo enfermero")
    void create() {
        Enfermero enfermero = crearEnfermero();
        when(repo.save(enfermero)).thenReturn(enfermero);

        Enfermero resultado = service.create(enfermero);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(enfermero);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        Enfermero enfermero = crearEnfermero();
        when(repo.save(enfermero)).thenThrow(new DataRetrievalFailureException("Error DB simulado"));

        assertThrows(ServiceException.class, () -> service.create(enfermero));
        verify(repo, times(1)).save(enfermero);
    }

    @Test
    @DisplayName("update modifica el enfermero si su identificador existe")
    void update_existe() {
        Enfermero enfermero = crearEnfermero();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(enfermero)).thenReturn(enfermero);

        Enfermero resultado = service.update(enfermero);

        assertNotNull(resultado);
        assertEquals("María Torres", resultado.getNombreCompleto());
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(enfermero);
    }

    @Test
    @DisplayName("update lanza ServiceException si el enfermero a modificar no existe")
    void update_noExiste() {
        Enfermero enfermero = crearEnfermero();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(enfermero));
        verify(repo, never()).save(any(Enfermero.class));
    }

    @Test
    @DisplayName("update propaga una ServiceException ante fallos de persistencia (cubre bloque catch)")
    void update_fallaPersistencia() {
        Enfermero enfermero = crearEnfermero();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(enfermero)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.update(enfermero));
        verify(repo, times(1)).save(enfermero);
    }

    @Test
    @DisplayName("deleteById delega la eliminación del enfermero al repositorio")
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
    @DisplayName("findByColegiatura retorna el enfermero identificado por su colegiatura")
    void findByColegiatura() {
        when(repo.findByColegiatura("CEP-12345")).thenReturn(Optional.of(crearEnfermero()));

        Optional<Enfermero> resultado = service.findByColegiatura("CEP-12345");

        assertTrue(resultado.isPresent());
        assertEquals("CEP-12345", resultado.get().getColegiatura());
        verify(repo, times(1)).findByColegiatura("CEP-12345");
    }

    @Test
    @DisplayName("findByCentroTrabajo lista los enfermeros de un centro ignorando mayúsculas y minúsculas")
    void findByCentroTrabajo() {
        when(repo.findByCentroTrabajoIgnoreCase("Centro Norte")).thenReturn(List.of(crearEnfermero()));

        List<Enfermero> resultado = service.findByCentroTrabajo("Centro Norte");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Centro Norte", resultado.getFirst().getCentroTrabajo());
        verify(repo, times(1)).findByCentroTrabajoIgnoreCase("Centro Norte");
    }
}
