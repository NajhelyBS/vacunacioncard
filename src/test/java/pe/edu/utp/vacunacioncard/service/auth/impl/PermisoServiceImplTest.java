package pe.edu.utp.vacunacioncard.service.auth.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.Permiso;
import pe.edu.utp.vacunacioncard.repository.auth.PermisoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - PermisoServiceImpl")
class PermisoServiceImplTest {

    @Mock
    private PermisoRepository repo;

    @InjectMocks
    private PermisoServiceImpl service;

    private Permiso crearPermiso() {
        Permiso permiso = new Permiso();
        permiso.setId(1L);
        permiso.setCodigo("VAC_CREATE");
        permiso.setNombre("Registrar vacunas");
        return permiso;
    }

    @Test
    @DisplayName("getAll retorna la lista completa de permisos registrados")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearPermiso()));

        List<Permiso> resultado = service.getAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("create almacena y retorna el nuevo permiso con su identificador")
    void create() {
        Permiso permiso = crearPermiso();
        when(repo.save(permiso)).thenReturn(permiso);

        Permiso resultado = service.create(permiso);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(permiso);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        Permiso permiso = crearPermiso();
        when(repo.save(permiso)).thenThrow(new DataRetrievalFailureException("Error DB simulado"));

        assertThrows(ServiceException.class, () -> service.create(permiso));
        verify(repo, times(1)).save(permiso);
    }

    @Test
    @DisplayName("findByCode retorna el permiso si el código existe")
    void findByCode_existe() {
        when(repo.findByCodigo("VAC_CREATE")).thenReturn(Optional.of(crearPermiso()));

        Optional<Permiso> resultado = service.findByCode("VAC_CREATE");

        assertTrue(resultado.isPresent());
        assertEquals("VAC_CREATE", resultado.get().getCodigo());
        verify(repo, times(1)).findByCodigo("VAC_CREATE");
    }

    @Test
    @DisplayName("findByCode retorna Optional.empty si el código no existe")
    void findByCode_noExiste() {
        when(repo.findByCodigo("INEXISTENTE")).thenReturn(Optional.empty());

        Optional<Permiso> resultado = service.findByCode("INEXISTENTE");

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findByCodigo("INEXISTENTE");
    }

    @Test
    @DisplayName("deleteById delega la eliminación del permiso al repositorio")
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
