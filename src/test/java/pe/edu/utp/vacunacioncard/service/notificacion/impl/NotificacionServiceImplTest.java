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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.Month;


@ExtendWith(MockitoExtension.class)
@DisplayName("Service - NotificacionServiceImpl")
class NotificacionServiceImplTest {

    @Mock
    private NotificacionRepository repo;

    @InjectMocks
    private NotificacionServiceImpl service;

    private static final LocalDateTime FECHA_TEST_FIJA = LocalDateTime.of(2026, Month.AUGUST, 1, 10, 0);
    private Usuario crearUsuario() {
        Enfermero enfermero = new Enfermero();
        enfermero.setId(1L);
        return enfermero;
    }

    @Test
    @DisplayName("Enviar notificación establece estado ENVIADA y fecha")
    void sendNotification() {
        Usuario usuario = crearUsuario();
        NotificacionSistema notif = new NotificacionSistema(usuario, "Test", "SISTEMA");

        when(repo.save(any(Notificacion.class))).thenAnswer(inv -> inv.getArgument(0));

        Notificacion resultado = service.sendNotification(notif);

        assertNotNull(resultado);
        assertEquals("ENVIADA", resultado.getEstado());
        assertNotNull(resultado.getFechaEnvio());
        verify(repo, times(1)).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("Listar por usuario delega al repositorio")
    void findByUser() {
        when(repo.findByDestinatarioId(1L)).thenReturn(List.of());

        List<Notificacion> resultado = service.findByUser(1L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findByDestinatarioId(1L);
    }

    @Test
    @DisplayName("Listar por usuario y estado delega al repositorio")
    void findByUserAndStatus() {
        when(repo.findByDestinatarioIdAndEstadoIgnoreCase(1L, "PENDIENTE")).thenReturn(List.of());

        List<Notificacion> resultado = service.findByUserAndStatus(1L, "PENDIENTE");

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findByDestinatarioIdAndEstadoIgnoreCase(1L, "PENDIENTE");
    }

    @Test
    @DisplayName("Marcar como leída cambia el estado a LEIDA y guarda")
    void markAsRead() {
        Usuario usuario = crearUsuario();
        NotificacionSistema notif = new NotificacionSistema(usuario, "Test", "SISTEMA");
        when(repo.findById(5L)).thenReturn(Optional.of(notif));
        when(repo.save(any(Notificacion.class))).thenAnswer(inv -> inv.getArgument(0));

        Notificacion resultado = service.markAsRead(5L);

        assertEquals("LEIDA", resultado.getEstado());
        verify(repo, times(1)).findById(5L);
        verify(repo, times(1)).save(notif);
    }

    @Test
    @DisplayName("Marcar como leída lanza ServiceException si la notificación no existe")
    void markAsRead_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> service.markAsRead(99L));
        verify(repo, never()).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("Registrar alerta usa la Factory y guarda")
    void registerAlert() {
        Usuario usuario = crearUsuario();
        when(repo.save(any(Notificacion.class))).thenAnswer(inv -> inv.getArgument(0));

        service.registerAlert(usuario, "Alerta importante");

        verify(repo, times(1)).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("Registrar recordatorio usa la Factory y guarda")
    void registerVaccineReminder() {
        Usuario usuario = crearUsuario();
        RegistroVacuna registro = new RegistroVacuna();

        when(repo.save(any(Notificacion.class))).thenAnswer(inv -> inv.getArgument(0));

        service.registerVaccineReminder(usuario, registro, FECHA_TEST_FIJA);

        verify(repo, times(1)).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("Enviar notificación lanza ServiceException ante fallo de persistencia")
    void sendNotification_lanzaServiceException() {
        Usuario usuario = crearUsuario();
        NotificacionSistema notif = new NotificacionSistema(usuario, "Test", "SISTEMA");
        when(repo.save(any(Notificacion.class))).thenThrow(new DataRetrievalFailureException("Error BD"));

        assertThrows(ServiceException.class, () -> service.sendNotification(notif));
    }

    @Test
    @DisplayName("Registrar alerta lanza ServiceException ante fallo de persistencia")
    void registerAlert_lanzaServiceException() {
        Usuario usuario = crearUsuario();
        when(repo.save(any(Notificacion.class))).thenThrow(new DataRetrievalFailureException("Error BD"));

        assertThrows(ServiceException.class, () -> service.registerAlert(usuario, "Alerta"));
    }

    @Test
    @DisplayName("Registrar recordatorio lanza ServiceException ante fallo de persistencia")
    void registerVaccineReminder_lanzaServiceException() {
        Usuario usuario = crearUsuario();
        RegistroVacuna registro = new RegistroVacuna();

        when(repo.save(any(Notificacion.class))).thenThrow(new DataRetrievalFailureException("Error BD"));

        assertThrows(ServiceException.class, () -> service.registerVaccineReminder(usuario, registro, FECHA_TEST_FIJA));
    }

    @Test
    @DisplayName("Marcar como leída lanza ServiceException si la base de datos falla al actualizar el estado")
    void markAsRead_fallaPersistencia() {
        Usuario usuario = crearUsuario();
        NotificacionSistema notif = new NotificacionSistema(usuario, "Test", "SISTEMA");

        when(repo.findById(5L)).thenReturn(Optional.of(notif));
        when(repo.save(any(Notificacion.class))).thenThrow(new DataRetrievalFailureException("Error persistencia de BD"));

        assertThrows(ServiceException.class, () -> service.markAsRead(5L));
        verify(repo, times(1)).save(any(Notificacion.class));
    }

}
