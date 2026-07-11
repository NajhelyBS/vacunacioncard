package pe.edu.utp.vacunacioncard.service.auth.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.SesionUsuarioRepository;
import pe.edu.utp.vacunacioncard.service.patron.singleton.ConfiguracionSistema;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - SesionUsuarioServiceImpl")
class SesionUsuarioServiceImplTest {

    @Mock
    private SesionUsuarioRepository repo;

    @InjectMocks
    private SesionUsuarioServiceImpl service;

    @BeforeEach
    void setUp() {
        ConfiguracionSistema.getInstancia().reset();
    }

        @Test
    @DisplayName("Crear sesión guarda y retorna correctamente cuando tiene cuenta")
    void createSession_ConCuenta() {
        pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario cuentaFake = new pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario();
        cuentaFake.setId(99L);

        SesionUsuario sesion = SesionUsuario.builder().token("abc123").cuenta(cuentaFake).activa(true).build();
        when(repo.save(sesion)).thenReturn(sesion);

        SesionUsuario resultado = service.createSession(sesion);

        assertNotNull(resultado);
        assertEquals("abc123", resultado.getToken());
        verify(repo, times(1)).save(sesion);
    }

    @Test
    @DisplayName("Crear sesión guarda correctamente cuando la cuenta es nula")
    void createSession_SinCuenta() {
        SesionUsuario sesionSinCuenta = SesionUsuario.builder().token("xyz789").cuenta(null).activa(true).build();
        when(repo.save(sesionSinCuenta)).thenReturn(sesionSinCuenta);

        SesionUsuario resultado = service.createSession(sesionSinCuenta);

        assertNotNull(resultado);
        verify(repo, times(1)).save(sesionSinCuenta);
    }


    @Test
    @DisplayName("Crear sesión lanza ServiceException si falla el repositorio")
    void createSession_lanzaServiceException() {
        SesionUsuario sesion = SesionUsuario.builder().token("error123").build();
        when(repo.save(sesion)).thenThrow(new DataRetrievalFailureException("Error de BD simulado"));

        assertThrows(ServiceException.class, () -> service.createSession(sesion)); 
    }

    @Test
    @DisplayName("Obtener por token retorna la sesión si existe")
    void findByToken_existe() {
        SesionUsuario sesion = SesionUsuario.builder().token("token123").build();
        when(repo.findByToken("token123")).thenReturn(Optional.of(sesion));

        Optional<SesionUsuario> resultado = service.findByToken("token123");

        assertTrue(resultado.isPresent());
        verify(repo, times(1)).findByToken("token123");
    }

    @Test
    @DisplayName("Obtener por token retorna vacío si no existe")
    void findByToken_noExiste() {
        when(repo.findByToken("inexistente")).thenReturn(Optional.empty());

        assertTrue(service.findByToken("inexistente").isEmpty());
        verify(repo, times(1)).findByToken("inexistente");
    }

    @Test
    @DisplayName("Cerrar sesión la desactiva")
    void closeSession() {
        SesionUsuario sesion = SesionUsuario.builder().id(1L).activa(true).token("t").build();
        when(repo.findById(1L)).thenReturn(Optional.of(sesion));
        when(repo.save(any(SesionUsuario.class))).thenReturn(sesion); 

        service.closeSession(1L);

        assertFalse(sesion.isActiva());
        verify(repo, times(1)).findById(1L);
        verify(repo, times(1)).save(sesion);
    }

    @Test
    @DisplayName("Cerrar sesión lanza ServiceException si falla el repositorio")
    void closeSession_lanzaServiceException() {
        SesionUsuario sesion = SesionUsuario.builder().id(2L).activa(true).build();
        when(repo.findById(2L)).thenReturn(Optional.of(sesion));
        when(repo.save(any(SesionUsuario.class))).thenThrow(new DataRetrievalFailureException("Error de BD simulado"));

        assertThrows(ServiceException.class, () -> service.closeSession(2L)); 
    }

    @Test
    @DisplayName("Verificar bloqueo retorna true si excede intentos")
    void verificarBloqueo_excede() {
        assertTrue(service.isAccountLocked(3));
        assertTrue(service.isAccountLocked(10));
    }

    @Test
    @DisplayName("Verificar bloqueo retorna false dentro del límite")
    void verificarBloqueo_dentro() {
        assertFalse(service.isAccountLocked(0));
        assertFalse(service.isAccountLocked(2));
    }
}
