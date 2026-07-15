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
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.repository.usuario.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - UsuarioServiceImpl")
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repo;

    @InjectMocks
    private UsuarioServiceImpl service;

    /** Usuario es abstracta: se usa Enfermero como implementación concreta para las pruebas. */
    private Usuario crearUsuario() {
        Enfermero enfermero = new Enfermero();
        enfermero.setId(1L);
        enfermero.setNombreCompleto("María Torres");
        enfermero.setDni("45678912");
        enfermero.setActivo(true);
        return enfermero;
    }

    @Test
    @DisplayName("getActiveUsers retorna únicamente los usuarios con estado activo")
    void getActiveUsers() {
        when(repo.findByActivo(true)).thenReturn(List.of(crearUsuario()));

        List<Usuario> resultado = service.getActiveUsers();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.getFirst().isActivo());
        verify(repo, times(1)).findByActivo(true);
    }

    @Test
    @DisplayName("findByDni retorna el usuario si el documento existe")
    void findByDni_existe() {
        when(repo.findByDni("45678912")).thenReturn(Optional.of(crearUsuario()));

        Optional<Usuario> resultado = service.findByDni("45678912");

        assertTrue(resultado.isPresent());
        assertEquals("45678912", resultado.get().getDni());
        verify(repo, times(1)).findByDni("45678912");
    }

    @Test
    @DisplayName("findByDni retorna Optional.empty si el documento no está registrado")
    void findByDni_noExiste() {
        when(repo.findByDni("00000000")).thenReturn(Optional.empty());

        Optional<Usuario> resultado = service.findByDni("00000000");

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findByDni("00000000");
    }

    @Test
    @DisplayName("toggleStatus invierte el estado de un usuario activo dejándolo inactivo")
    void toggleStatus_deActivoAInactivo() {
        Usuario usuario = crearUsuario();
        when(repo.findById(1L)).thenReturn(Optional.of(usuario));
        when(repo.save(usuario)).thenReturn(usuario);

        service.toggleStatus(1L);

        assertFalse(usuario.isActivo());
        verify(repo, times(1)).save(usuario);
    }

    @Test
    @DisplayName("toggleStatus invierte el estado de un usuario inactivo dejándolo activo")
    void toggleStatus_deInactivoAActivo() {
        Usuario usuario = crearUsuario();
        usuario.setActivo(false);
        when(repo.findById(1L)).thenReturn(Optional.of(usuario));
        when(repo.save(usuario)).thenReturn(usuario);

        service.toggleStatus(1L);

        assertTrue(usuario.isActivo());
        verify(repo, times(1)).save(usuario);
    }

    @Test
    @DisplayName("toggleStatus no realiza cambios si el usuario no existe")
    void toggleStatus_usuarioNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        service.toggleStatus(99L);

        verify(repo, times(1)).findById(99L);
        verify(repo, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("toggleStatus propaga una ServiceException ante fallos de persistencia")
    void toggleStatus_lanzaServiceException() {
        Usuario usuario = crearUsuario();
        when(repo.findById(1L)).thenReturn(Optional.of(usuario));
        when(repo.save(usuario)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.toggleStatus(1L));
        verify(repo, times(1)).save(usuario);
    }
}
