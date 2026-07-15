package pe.edu.utp.vacunacioncard.service.salud.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import pe.edu.utp.vacunacioncard.repository.salud.CondicionMedicaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - CondicionMedicaServiceImpl")
class CondicionMedicaServiceImplTest {

    @Mock
    private CondicionMedicaRepository repo;

    @InjectMocks
    private CondicionMedicaServiceImpl service;

    private CondicionMedica crearCondicion() {
        CondicionMedica condicion = new CondicionMedica();
        condicion.setId(1L);
        condicion.setNombre("Diabetes tipo 2");
        condicion.setCodigoCIE10("E11");
        condicion.setActiva(true);
        return condicion;
    }

    @Test
    @DisplayName("getAll retorna la lista de todas las condiciones médicas registradas")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearCondicion()));

        List<CondicionMedica> resultado = service.getAll();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna la condición médica si el ID existe en el repositorio")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearCondicion()));

        Optional<CondicionMedica> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Diabetes tipo 2", resultado.get().getNombre());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById retorna Optional.empty si el identificador no se encuentra")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<CondicionMedica> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create almacena de manera exitosa y retorna la condición médica")
    void create() {
        CondicionMedica condicion = crearCondicion();
        when(repo.save(condicion)).thenReturn(condicion);

        CondicionMedica resultado = service.create(condicion);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(condicion);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia en la BD")
    void create_lanzaServiceException() {
        CondicionMedica condicion = crearCondicion();
        when(repo.save(condicion)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.create(condicion));
        verify(repo, times(1)).save(condicion);
    }

    @Test
    @DisplayName("update modifica y guarda la condición médica si el ID existe")
    void update_existe() {
        CondicionMedica condicion = crearCondicion();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(condicion)).thenReturn(condicion);

        CondicionMedica resultado = service.update(condicion);

        assertNotNull(resultado);
        assertEquals("E11", resultado.getCodigoCIE10());
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(condicion);
    }

    @Test
    @DisplayName("update interrumpe con ServiceException si el ID a modificar no existe en el sistema")
    void update_noExiste() {
        CondicionMedica condicion = crearCondicion();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(condicion));
        verify(repo, times(1)).existsById(1L);
        verify(repo, never()).save(any(CondicionMedica.class));
    }

    @Test
    @DisplayName("update captura anomalías de datos y propaga una ServiceException controlada (Cubre el bloque catch)")
    void update_fallaPersistencia() {
        CondicionMedica condicion = crearCondicion();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(condicion)).thenThrow(new DataRetrievalFailureException("Error de escritura"));

        assertThrows(ServiceException.class, () -> service.update(condicion));
        verify(repo, times(1)).save(condicion);
    }

    @Test
    @DisplayName("deleteById delega correctamente la remoción de la patología al repositorio")
    void deleteById() {
        doNothing().when(repo).deleteById(1L);

        service.deleteById(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById envuelve fallos de acceso a datos en una ServiceException")
    void deleteById_lanzaServiceException() {
        doThrow(new DataRetrievalFailureException("Restricción de clave foránea simulada"))
                .when(repo).deleteById(1L);

        assertThrows(ServiceException.class, () -> service.deleteById(1L));
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("findByIcd10Code recupera una patología si el código existe")
    void findByIcd10Code_existe() {
        when(repo.findByCodigoCIE10IgnoreCase("E11")).thenReturn(Optional.of(crearCondicion()));

        Optional<CondicionMedica> resultado = service.findByIcd10Code("E11");

        assertTrue(resultado.isPresent());
        assertEquals("Diabetes tipo 2", resultado.get().getNombre());
        verify(repo, times(1)).findByCodigoCIE10IgnoreCase("E11");
    }

    @Test
    @DisplayName("findByIcd10Code retorna vacío si el código no se encuentra")
    void findByIcd10Code_noExiste() {
        when(repo.findByCodigoCIE10IgnoreCase("Z99")).thenReturn(Optional.empty());

        Optional<CondicionMedica> resultado = service.findByIcd10Code("Z99");

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findByCodigoCIE10IgnoreCase("Z99");
    }

    @Test
    @DisplayName("findByStatus lista de forma idónea las condiciones filtradas por vigencia booleana")
    void findByStatus() {
        when(repo.findByActiva(true)).thenReturn(List.of(crearCondicion()));

        List<CondicionMedica> resultado = service.findByStatus(true);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isActiva());
        verify(repo, times(1)).findByActiva(true);
    }
}
