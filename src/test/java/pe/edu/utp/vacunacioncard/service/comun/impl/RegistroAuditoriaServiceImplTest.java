package pe.edu.utp.vacunacioncard.service.comun.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.repository.comun.RegistroAuditoriaRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - RegistroAuditoriaServiceImpl")
class RegistroAuditoriaServiceImplTest {

    @Mock
    private RegistroAuditoriaRepository repo;

    @InjectMocks
    private RegistroAuditoriaServiceImpl service;

    /** Marca temporal fija para garantizar pruebas deterministas. */
    private static final LocalDateTime FECHA_HORA_FIJA = LocalDateTime.of(2026, Month.JULY, 28, 9, 30);

    private RegistroAuditoria crearAuditoria() {
        Usuario usuario = new Enfermero();
        usuario.setId(2L);

        RegistroAuditoria auditoria = new RegistroAuditoria();
        auditoria.setId(1L);
        auditoria.setUsuario(usuario);
        auditoria.setAccion("REGISTRAR_DOSIS");
        auditoria.setEntidadAfectada("RegistroVacuna");
        auditoria.setFechaHora(FECHA_HORA_FIJA);
        return auditoria;
    }

    @Test
    @DisplayName("create guarda y retorna la nueva traza de auditoría")
    void create() {
        RegistroAuditoria auditoria = crearAuditoria();
        when(repo.save(auditoria)).thenReturn(auditoria);

        RegistroAuditoria resultado = service.create(auditoria);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("REGISTRAR_DOSIS", resultado.getAccion());
        verify(repo, times(1)).save(auditoria);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        RegistroAuditoria auditoria = crearAuditoria();
        when(repo.save(auditoria)).thenThrow(new DataRetrievalFailureException("Error DB simulado"));

        assertThrows(ServiceException.class, () -> service.create(auditoria));
        verify(repo, times(1)).save(auditoria);
    }

    @Test
    @DisplayName("getAll retorna el historial completo de auditoría")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearAuditoria()));

        List<RegistroAuditoria> resultado = service.getAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("findById retorna la traza de auditoría si el identificador existe")
    void findById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearAuditoria()));

        Optional<RegistroAuditoria> resultado = service.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(FECHA_HORA_FIJA, resultado.get().getFechaHora());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById retorna Optional.empty si el identificador no existe")
    void findById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<RegistroAuditoria> resultado = service.findById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("findByUser lista las acciones auditadas de un usuario específico")
    void findByUser() {
        when(repo.findByUsuarioId(2L)).thenReturn(List.of(crearAuditoria()));

        List<RegistroAuditoria> resultado = service.findByUser(2L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findByUsuarioId(2L);
    }

    @Test
    @DisplayName("findByAffectedEntity filtra la auditoría por entidad ignorando mayúsculas y minúsculas")
    void findByAffectedEntity() {
        when(repo.findByEntidadAfectadaIgnoreCase("RegistroVacuna")).thenReturn(List.of(crearAuditoria()));

        List<RegistroAuditoria> resultado = service.findByAffectedEntity("RegistroVacuna");

        assertNotNull(resultado);
        assertEquals("RegistroVacuna", resultado.getFirst().getEntidadAfectada());
        verify(repo, times(1)).findByEntidadAfectadaIgnoreCase("RegistroVacuna");
    }
}
