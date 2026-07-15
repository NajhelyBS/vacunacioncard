package pe.edu.utp.vacunacioncard.service.auth.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.Rol;
import pe.edu.utp.vacunacioncard.repository.auth.RolRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - RolServiceImpl")
class RolServiceImplTest {

    @Mock
    private RolRepository repo;

    @InjectMocks
    private RolServiceImpl service;

    private Rol crearRol() {
        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("ADMINISTRADOR");
        rol.setDescripcion("Perfil con acceso total al sistema");
        return rol;
    }

    @Test
    @DisplayName("getAll retorna la lista completa de roles registrados")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearRol()));

        List<Rol> resultado = service.getAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("create almacena y retorna el nuevo rol con su identificador")
    void create() {
        Rol rol = crearRol();
        when(repo.save(rol)).thenReturn(rol);

        Rol resultado = service.create(rol);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(rol);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        Rol rol = crearRol();
        when(repo.save(rol)).thenThrow(new DataRetrievalFailureException("Error DB simulado"));

        assertThrows(ServiceException.class, () -> service.create(rol));
        verify(repo, times(1)).save(rol);
    }

    @Test
    @DisplayName("findByName retorna el rol si el nombre existe")
    void findByName_existe() {
        when(repo.findByNombre("ADMINISTRADOR")).thenReturn(Optional.of(crearRol()));

        Optional<Rol> resultado = service.findByName("ADMINISTRADOR");

        assertTrue(resultado.isPresent());
        assertEquals("ADMINISTRADOR", resultado.get().getNombre());
        verify(repo, times(1)).findByNombre("ADMINISTRADOR");
    }

    @Test
    @DisplayName("findByName retorna Optional.empty si el nombre no coincide con ningún rol")
    void findByName_noExiste() {
        when(repo.findByNombre("INEXISTENTE")).thenReturn(Optional.empty());

        Optional<Rol> resultado = service.findByName("INEXISTENTE");

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findByNombre("INEXISTENTE");
    }

    @Test
    @DisplayName("deleteById delega la eliminación del rol al repositorio")
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
