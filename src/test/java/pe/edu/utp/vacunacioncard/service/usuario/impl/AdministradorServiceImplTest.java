package pe.edu.utp.vacunacioncard.service.usuario.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.Administrador;
import pe.edu.utp.vacunacioncard.repository.usuario.AdministradorRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - AdministradorServiceImpl")
class AdministradorServiceImplTest {

    @Mock
    private AdministradorRepository repo;

    @InjectMocks
    private AdministradorServiceImpl service;

    private Administrador crearAdministrador() {
        Administrador administrador = new Administrador();
        administrador.setId(1L);
        administrador.setNombreCompleto("Carlos Ruiz");
        administrador.setArea("Sistemas");
        administrador.setNivelAcceso("TOTAL");
        return administrador;
    }

    @Test
    @DisplayName("getAll retorna la lista completa de administradores")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearAdministrador()));

        List<Administrador> resultado = service.getAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna el administrador si el identificador existe")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearAdministrador()));

        Optional<Administrador> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Sistemas", resultado.get().getArea());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById retorna Optional.empty si el identificador no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<Administrador> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create registra y retorna el nuevo administrador")
    void create() {
        Administrador administrador = crearAdministrador();
        when(repo.save(administrador)).thenReturn(administrador);

        Administrador resultado = service.create(administrador);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(administrador);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        Administrador administrador = crearAdministrador();
        when(repo.save(administrador)).thenThrow(new DataRetrievalFailureException("Error DB simulado"));

        assertThrows(ServiceException.class, () -> service.create(administrador));
        verify(repo, times(1)).save(administrador);
    }

    @Test
    @DisplayName("update modifica el administrador si su identificador existe")
    void update_existe() {
        Administrador administrador = crearAdministrador();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(administrador)).thenReturn(administrador);

        Administrador resultado = service.update(administrador);

        assertNotNull(resultado);
        assertEquals("Carlos Ruiz", resultado.getNombreCompleto());
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(administrador);
    }

    @Test
    @DisplayName("update lanza ServiceException si el administrador a modificar no existe")
    void update_noExiste() {
        Administrador administrador = crearAdministrador();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(administrador));
        verify(repo, never()).save(any(Administrador.class));
    }

    @Test
    @DisplayName("update propaga una ServiceException ante fallos de persistencia (cubre bloque catch)")
    void update_fallaPersistencia() {
        Administrador administrador = crearAdministrador();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(administrador)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.update(administrador));
        verify(repo, times(1)).save(administrador);
    }

    @Test
    @DisplayName("deleteById delega la eliminación del administrador al repositorio")
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
    @DisplayName("findByArea lista los administradores de un área ignorando mayúsculas y minúsculas")
    void findByArea() {
        when(repo.findByAreaIgnoreCase("Sistemas")).thenReturn(List.of(crearAdministrador()));

        List<Administrador> resultado = service.findByArea("Sistemas");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sistemas", resultado.getFirst().getArea());
        verify(repo, times(1)).findByAreaIgnoreCase("Sistemas");
    }
}
