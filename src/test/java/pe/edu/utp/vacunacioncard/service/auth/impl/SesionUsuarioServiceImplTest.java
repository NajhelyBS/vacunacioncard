package pe.edu.utp.vacunacioncard.service.auth.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.SesionUsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - SesionUsuarioServiceImpl")
class SesionUsuarioServiceImplTest {

    @Mock
    private SesionUsuarioRepository repo;

    @InjectMocks
    private SesionUsuarioServiceImpl service;

    @Test
    @DisplayName("Crear sesión guarda y retorna correctamente")
    void crearSesion() {
        SesionUsuario sesion = SesionUsuario.builder().token("abc123").activa(true).build();
        when(repo.save(sesion)).thenReturn(sesion);

        SesionUsuario resultado = service.crearSesion(sesion);

        assertEquals("abc123", resultado.getToken());
        verify(repo).save(sesion);
    }

    @Test
    @DisplayName("Obtener por token retorna la sesión si existe")
    void obtenerPorToken_existe() {
        SesionUsuario sesion = SesionUsuario.builder().token("token123").build();
        when(repo.findByToken("token123")).thenReturn(Optional.of(sesion));

        Optional<SesionUsuario> resultado = service.obtenerPorToken("token123");

        assertTrue(resultado.isPresent());
    }

    @Test
    @DisplayName("Obtener por token retorna vacío si no existe")
    void obtenerPorToken_noExiste() {
        when(repo.findByToken("inexistente")).thenReturn(Optional.empty());

        assertTrue(service.obtenerPorToken("inexistente").isEmpty());
    }

    @Test
    @DisplayName("Cerrar sesión la desactiva")
    void cerrarSesion() {
        SesionUsuario sesion = SesionUsuario.builder().id(1L).activa(true).token("t").build();
        when(repo.findById(1L)).thenReturn(Optional.of(sesion));

        service.cerrarSesion(1L);

        assertFalse(sesion.isActiva());
        verify(repo).save(sesion);
    }

    @Test
    @DisplayName("Verificar bloqueo retorna true si excede intentos")
    void verificarBloqueo_excede() {
        assertTrue(service.verificarBloqueoCuenta(3));
        assertTrue(service.verificarBloqueoCuenta(10));
    }

    @Test
    @DisplayName("Verificar bloqueo retorna false dentro del límite")
    void verificarBloqueo_dentro() {
        assertFalse(service.verificarBloqueoCuenta(0));
        assertFalse(service.verificarBloqueoCuenta(2));
    }
}
