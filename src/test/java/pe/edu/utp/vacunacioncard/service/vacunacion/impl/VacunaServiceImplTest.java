package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import pe.edu.utp.vacunacioncard.repository.vacunacion.VacunaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - VacunaServiceImpl")
class VacunaServiceImplTest {

    @Mock
    private VacunaRepository repo;

    @InjectMocks
    private VacunaServiceImpl service;

    private Vacuna crearVacuna() {
        Vacuna vacuna = new Vacuna();
        vacuna.setId(1L);
        vacuna.setNombre("Influenza");
        vacuna.setDosisRequeridas(2);
        vacuna.setDisponible(true);
        return vacuna;
    }

    @Test
    @DisplayName("getAll retorna el catálogo completo de vacunas")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearVacuna()));

        List<Vacuna> resultado = service.getAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna la vacuna si el identificador existe")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearVacuna()));

        Optional<Vacuna> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Influenza", resultado.get().getNombre());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById retorna Optional.empty si el identificador no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<Vacuna> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create registra y retorna la nueva vacuna del catálogo")
    void create() {
        Vacuna vacuna = crearVacuna();
        when(repo.save(vacuna)).thenReturn(vacuna);

        Vacuna resultado = service.create(vacuna);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(vacuna);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        Vacuna vacuna = crearVacuna();
        when(repo.save(vacuna)).thenThrow(new DataRetrievalFailureException("Error DB simulado"));

        assertThrows(ServiceException.class, () -> service.create(vacuna));
        verify(repo, times(1)).save(vacuna);
    }

    @Test
    @DisplayName("update modifica la vacuna si su identificador existe")
    void update_existe() {
        Vacuna vacuna = crearVacuna();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(vacuna)).thenReturn(vacuna);

        Vacuna resultado = service.update(vacuna);

        assertNotNull(resultado);
        assertEquals("Influenza", resultado.getNombre());
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(vacuna);
    }

    @Test
    @DisplayName("update lanza ServiceException si la vacuna a modificar no existe")
    void update_noExiste() {
        Vacuna vacuna = crearVacuna();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(vacuna));
        verify(repo, times(1)).existsById(1L);
        verify(repo, never()).save(any(Vacuna.class));
    }

    @Test
    @DisplayName("update propaga una ServiceException ante fallos de persistencia (cubre bloque catch)")
    void update_fallaPersistencia() {
        Vacuna vacuna = crearVacuna();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(vacuna)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.update(vacuna));
        verify(repo, times(1)).save(vacuna);
    }

    @Test
    @DisplayName("deleteById delega la eliminación de la vacuna al repositorio")
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
    @DisplayName("findByAvailability filtra las vacunas según su disponibilidad")
    void findByAvailability() {
        when(repo.findByDisponible(true)).thenReturn(List.of(crearVacuna()));

        List<Vacuna> resultado = service.findByAvailability(true);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.getFirst().isDisponible());
        verify(repo, times(1)).findByDisponible(true);
    }

    @Test
    @DisplayName("findByLaboratory lista las vacunas asociadas a un laboratorio")
    void findByLaboratory() {
        when(repo.findByLaboratorioId(5L)).thenReturn(List.of(crearVacuna()));

        List<Vacuna> resultado = service.findByLaboratory(5L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findByLaboratorioId(5L);
    }
}
