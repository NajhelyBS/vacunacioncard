package pe.edu.utp.vacunacioncard.service.usuario.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.SeguroMedico;
import pe.edu.utp.vacunacioncard.repository.usuario.SeguroMedicoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - SeguroMedicoServiceImpl")
class SeguroMedicoServiceImplTest {

    @Mock
    private SeguroMedicoRepository repo;

    @InjectMocks
    private SeguroMedicoServiceImpl service;

    private SeguroMedico crearSeguro() {
        SeguroMedico seguro = new SeguroMedico();
        seguro.setId(1L);
        seguro.setNombre("EsSalud");
        seguro.setNumeroPoliza("POL-98765");
        seguro.setCobertura("Integral");
        return seguro;
    }

    @Test
    @DisplayName("getAll retorna la lista completa de seguros médicos")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearSeguro()));

        List<SeguroMedico> resultado = service.getAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna el seguro médico si el identificador existe")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearSeguro()));

        Optional<SeguroMedico> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("EsSalud", resultado.get().getNombre());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById retorna Optional.empty si el identificador no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<SeguroMedico> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create registra y retorna el nuevo seguro médico")
    void create() {
        SeguroMedico seguro = crearSeguro();
        when(repo.save(seguro)).thenReturn(seguro);

        SeguroMedico resultado = service.create(seguro);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(seguro);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        SeguroMedico seguro = crearSeguro();
        when(repo.save(seguro)).thenThrow(new DataRetrievalFailureException("Error DB simulado"));

        assertThrows(ServiceException.class, () -> service.create(seguro));
        verify(repo, times(1)).save(seguro);
    }

    @Test
    @DisplayName("update modifica el seguro médico si su identificador existe")
    void update_existe() {
        SeguroMedico seguro = crearSeguro();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(seguro)).thenReturn(seguro);

        SeguroMedico resultado = service.update(seguro);

        assertNotNull(resultado);
        assertEquals("POL-98765", resultado.getNumeroPoliza());
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(seguro);
    }

    @Test
    @DisplayName("update lanza ServiceException si el seguro a modificar no existe")
    void update_noExiste() {
        SeguroMedico seguro = crearSeguro();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(seguro));
        verify(repo, never()).save(any(SeguroMedico.class));
    }

    @Test
    @DisplayName("update propaga una ServiceException ante fallos de persistencia (cubre bloque catch)")
    void update_fallaPersistencia() {
        SeguroMedico seguro = crearSeguro();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(seguro)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.update(seguro));
        verify(repo, times(1)).save(seguro);
    }

    @Test
    @DisplayName("deleteById delega la eliminación del seguro médico al repositorio")
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
    @DisplayName("findByPolicyNumber retorna el seguro identificado por su número de póliza")
    void findByPolicyNumber() {
        when(repo.findByNumeroPoliza("POL-98765")).thenReturn(Optional.of(crearSeguro()));

        Optional<SeguroMedico> resultado = service.findByPolicyNumber("POL-98765");

        assertTrue(resultado.isPresent());
        assertEquals("POL-98765", resultado.get().getNumeroPoliza());
        verify(repo, times(1)).findByNumeroPoliza("POL-98765");
    }
}
