package pe.edu.utp.vacunacioncard.service.salud.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import pe.edu.utp.vacunacioncard.repository.salud.ContraindicacionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - ContraindicacionServiceImpl")
class ContraindicacionServiceImplTest {

    @Mock
    private ContraindicacionRepository repo;

    @InjectMocks
    private ContraindicacionServiceImpl service;

    private Contraindicacion crearContraindicacion() {
        Vacuna vacuna = new Vacuna();
        vacuna.setId(10L);

        Contraindicacion contraindicacion = new Contraindicacion();
        contraindicacion.setId(1L);
        contraindicacion.setDescripcion("Alergia severa a componentes de la vacuna");
        contraindicacion.setSeveridad("ALTA");
        contraindicacion.setVacunaAfectada(vacuna);
        return contraindicacion;
    }

    @Test
    @DisplayName("getAll retorna la lista de todas las contraindicaciones registradas")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearContraindicacion()));

        List<Contraindicacion> resultado = service.getAll();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna la contraindicación si el ID se encuentra en el repositorio")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearContraindicacion()));

        Optional<Contraindicacion> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("ALTA", resultado.get().getSeveridad());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById devuelve Optional.empty si el identificador no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<Contraindicacion> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create almacena y retorna con éxito la nueva contraindicación")
    void create() {
        Contraindicacion contraindicacion = crearContraindicacion();
        when(repo.save(contraindicacion)).thenReturn(contraindicacion);

        Contraindicacion resultado = service.create(contraindicacion);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(contraindicacion);
    }

    @Test
    @DisplayName("create captura anomalías de persistencia y propaga una ServiceException controlada")
    void create_lanzaServiceException() {
        Contraindicacion contraindicacion = crearContraindicacion();
        when(repo.save(contraindicacion)).thenThrow(new DataRetrievalFailureException("Error persistencia"));

        assertThrows(ServiceException.class, () -> service.create(contraindicacion));
        verify(repo, times(1)).save(contraindicacion);
    }

    @Test
    @DisplayName("update modifica y guarda los valores si el ID existe en el sistema")
    void update_existe() {
        Contraindicacion contraindicacion = crearContraindicacion();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(contraindicacion)).thenReturn(contraindicacion);

        Contraindicacion resultado = service.update(contraindicacion);

        assertNotNull(resultado);
        assertEquals("Alergia severa a componentes de la vacuna", resultado.getDescripcion());
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(contraindicacion);
    }

    @Test
    @DisplayName("update interrumpe con ServiceException si el ID provisto no existe")
    void update_noExiste() {
        Contraindicacion contraindicacion = crearContraindicacion();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(contraindicacion));
        verify(repo, times(1)).existsById(1L);
        verify(repo, never()).save(any(Contraindicacion.class));
    }

    @Test
    @DisplayName("update captura anomalías de datos y lanza una ServiceException (Cubre bloque catch)")
    void update_fallaPersistencia() {
        Contraindicacion contraindicacion = crearContraindicacion();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(contraindicacion)).thenThrow(new DataRetrievalFailureException("Fallo de escritura"));

        assertThrows(ServiceException.class, () -> service.update(contraindicacion));
        verify(repo, times(1)).save(contraindicacion);
    }

    @Test
    @DisplayName("deleteById delega de manera idónea el borrado al repositorio")
    void deleteById() {
        doNothing().when(repo).deleteById(1L);

        service.deleteById(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById envuelve fallos del motor relacional en una ServiceException")
    void deleteById_lanzaServiceException() {
        doThrow(new DataRetrievalFailureException("Fallo foráneo de base de datos"))
                .when(repo).deleteById(1L);

        assertThrows(ServiceException.class, () -> service.deleteById(1L));
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("findByAffectedVaccine retorna la lista de restricciones filtradas por vacuna afectada")
    void findByAffectedVaccine() {
        when(repo.findByVacunaAfectadaId(10L)).thenReturn(List.of(crearContraindicacion()));

        List<Contraindicacion> resultado = service.findByAffectedVaccine(10L);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.getFirst().getVacunaAfectada().getId());
        verify(repo, times(1)).findByVacunaAfectadaId(10L);
    }
}
