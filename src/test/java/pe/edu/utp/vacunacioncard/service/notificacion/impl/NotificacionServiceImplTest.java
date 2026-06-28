package pe.edu.utp.vacunacioncard.service.notificacion.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionSistema;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.repository.notificacion.NotificacionRepository;

import java.time.LocalDateTime;
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
        when(repo.save(notif)).thenReturn(notif);

        Notificacion resultado = service.enviarNotificacion(notif);

        assertEquals("ENVIADA", resultado.getEstado());
        assertNotNull(resultado.getFechaEnvio());
    }

    @Test
    @DisplayName("Listar por usuario delega al repositorio")
    void listarPorUsuario() {
        when(repo.findByDestinatarioId(1L)).thenReturn(List.of());

        List<Notificacion> resultado = service.listarPorUsuario(1L);

        assertTrue(resultado.isEmpty());
        verify(repo).findByDestinatarioId(1L);
    }

    @Test
    @DisplayName("Registrar alerta usa la Factory y guarda")
    void registrarNuevaAlerta() {
        Usuario usuario = crearUsuario();
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.registrarNuevaAlerta(usuario, "Alerta importante");

        verify(repo).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("Registrar recordatorio usa la Factory y guarda")
    void registrarRecordatorio() {
        Usuario usuario = crearUsuario();
        RegistroVacuna registro = new RegistroVacuna();
        LocalDateTime fecha = LocalDateTime.now().plusDays(30);
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.registrarRecordatorioVacuna(usuario, registro, fecha);

        verify(repo).save(any(Notificacion.class));
    }
}
