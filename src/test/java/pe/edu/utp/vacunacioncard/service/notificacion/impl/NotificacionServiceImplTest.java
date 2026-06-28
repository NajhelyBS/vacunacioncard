package pe.edu.utp.vacunacioncard.service.notificacion.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionSistema;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.repository.notificacion.NotificacionRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - NotificacionServiceImpl")
class NotificacionServiceImplTest {

    @Mock
    private NotificacionRepository repo;

    @InjectMocks
    private NotificacionServiceImpl service;
    
    private static final String ZONE_LIMA = "America/Lima";

    private Usuario crearUsuario() {
        Enfermero enfermero = new Enfermero();
        enfermero.setId(1L);
        return enfermero;
    }

    @Test
    @DisplayName("Enviar notificación establece estado ENVIADA y fecha")
    void enviarNotificacion() {
        Usuario usuario = crearUsuario();
        NotificacionSistema notif = new NotificacionSistema(usuario, "Test", "SISTEMA");
        
        when(repo.save(any(Notificacion.class))).thenAnswer(inv -> inv.getArgument(0));

        Notificacion resultado = service.enviarNotificacion(notif);

        assertNotNull(resultado);
        assertEquals("ENVIADA", resultado.getEstado());
        assertNotNull(resultado.getFechaEnvio());
        verify(repo, times(1)).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("Listar por usuario delega al repositorio")
    void listarPorUsuario() {
        when(repo.findByDestinatarioId(1L)).thenReturn(List.of());

        List<Notificacion> resultado = service.listarPorUsuario(1L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findByDestinatarioId(1L);
    }

    @Test
    @DisplayName("Registrar alerta usa la Factory y guarda")
    void registrarNuevaAlerta() {
        Usuario usuario = crearUsuario();
        when(repo.save(any(Notificacion.class))).thenAnswer(inv -> inv.getArgument(0));

        service.registrarNuevaAlerta(usuario, "Alerta importante");

        verify(repo, times(1)).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("Registrar recordatorio usa la Factory y guarda")
    void registrarRecordatorio() {
        Usuario usuario = crearUsuario();
        RegistroVacuna registro = new RegistroVacuna();
        
        LocalDateTime fecha = LocalDateTime.now(ZoneId.of(ZONE_LIMA)).plusDays(30);
        when(repo.save(any(Notificacion.class))).thenAnswer(inv -> inv.getArgument(0));

        service.registrarRecordatorioVacuna(usuario, registro, fecha);

        verify(repo, times(1)).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("Enviar notificación lanza ServiceException ante fallo de persistencia")
    void enviarNotificacion_lanzaServiceException() {
        Usuario usuario = crearUsuario();
        NotificacionSistema notif = new NotificacionSistema(usuario, "Test", "SISTEMA");
        when(repo.save(any(Notificacion.class))).thenThrow(new DataRetrievalFailureException("Error BD"));

        assertThrows(ServiceException.class, () -> service.enviarNotificacion(notif));
    }

    @Test
    @DisplayName("Registrar alerta lanza ServiceException ante fallo de persistencia")
    void registrarNuevaAlerta_lanzaServiceException() {
        Usuario usuario = crearUsuario();
        when(repo.save(any(Notificacion.class))).thenThrow(new DataRetrievalFailureException("Error BD"));

        assertThrows(ServiceException.class, () -> service.registrarNuevaAlerta(usuario, "Alerta"));
    }

    @Test
    @DisplayName("Registrar recordatorio lanza ServiceException ante fallo de persistencia")
    void registrarRecordatorioVacuna_lanzaServiceException() {
        Usuario usuario = crearUsuario();
        RegistroVacuna registro = new RegistroVacuna();
        LocalDateTime fecha = LocalDateTime.now(ZoneId.of(ZONE_LIMA));
        when(repo.save(any(Notificacion.class))).thenThrow(new DataRetrievalFailureException("Error BD"));

        assertThrows(ServiceException.class, () -> service.registrarRecordatorioVacuna(usuario, registro, fecha));
    }
}
