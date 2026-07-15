package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion;
import pe.edu.utp.vacunacioncard.repository.vacunacion.EsquemaVacunacionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - EsquemaVacunacionServiceImpl")
class EsquemaVacunacionServiceImplTest {

    @Mock
    private EsquemaVacunacionRepository repo;

    @InjectMocks
    private EsquemaVacunacionServiceImpl service;

    private EsquemaVacunacion crearEsquema() {
        Paciente paciente = new Paciente();
        paciente.setId(2L);

        EsquemaVacunacion esquema = new EsquemaVacunacion();
        esquema.setId(1L);
        esquema.setNombre("Esquema Infantil 0-5 años");
        esquema.setEstado("PENDIENTE");
        esquema.setPacienteAsignado(paciente);
        return esquema;
    }

    @Test
    @DisplayName("getAll retorna todos los esquemas de vacunación registrados")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearEsquema()));

        List<EsquemaVacunacion> resultado = service.getAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna el esquema si el identificador existe")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearEsquema()));

        Optional<EsquemaVacunacion> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Esquema Infantil 0-5 años", resultado.get().getNombre());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById retorna Optional.empty si el identificador no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<EsquemaVacunacion> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create guarda y retorna el nuevo esquema asignado al paciente")
    void create() {
        EsquemaVacunacion esquema = crearEsquema();
        when(repo.save(esquema)).thenReturn(esquema);

        EsquemaVacunacion resultado = service.create(esquema);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(esquema);
    }

    @Test
    @DisplayName("create guarda correctamente aunque el esquema no tenga paciente asignado")
    void create_sinPacienteAsignado() {
        EsquemaVacunacion esquema = crearEsquema();
        esquema.setPacienteAsignado(null);
        when(repo.save(esquema)).thenReturn(esquema);

        EsquemaVacunacion resultado = service.create(esquema);

        assertNotNull(resultado);
        verify(repo, times(1)).save(esquema);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        EsquemaVacunacion esquema = crearEsquema();
        when(repo.save(esquema)).thenThrow(new DataRetrievalFailureException("Error DB simulado"));

        assertThrows(ServiceException.class, () -> service.create(esquema));
        verify(repo, times(1)).save(esquema);
    }

    @Test
    @DisplayName("update modifica el esquema si su identificador existe")
    void update_existe() {
        EsquemaVacunacion esquema = crearEsquema();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(esquema)).thenReturn(esquema);

        EsquemaVacunacion resultado = service.update(esquema);

        assertNotNull(resultado);
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(esquema);
    }

    @Test
    @DisplayName("update lanza ServiceException si el esquema a modificar no existe")
    void update_noExiste() {
        EsquemaVacunacion esquema = crearEsquema();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(esquema));
        verify(repo, never()).save(any(EsquemaVacunacion.class));
    }

    @Test
    @DisplayName("update propaga una ServiceException ante fallos de persistencia (cubre bloque catch)")
    void update_fallaPersistencia() {
        EsquemaVacunacion esquema = crearEsquema();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(esquema)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.update(esquema));
        verify(repo, times(1)).save(esquema);
    }

    @Test
    @DisplayName("deleteById delega la eliminación del esquema al repositorio")
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
    @DisplayName("findByPatient lista los esquemas asignados a un paciente")
    void findByPatient() {
        when(repo.findByPacienteAsignadoId(2L)).thenReturn(List.of(crearEsquema()));

        List<EsquemaVacunacion> resultado = service.findByPatient(2L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findByPacienteAsignadoId(2L);
    }

    @Test
    @DisplayName("findByStatus filtra los esquemas por estado ignorando mayúsculas y minúsculas")
    void findByStatus() {
        when(repo.findByEstadoIgnoreCase("PENDIENTE")).thenReturn(List.of(crearEsquema()));

        List<EsquemaVacunacion> resultado = service.findByStatus("PENDIENTE");

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getFirst().getEstado());
        verify(repo, times(1)).findByEstadoIgnoreCase("PENDIENTE");
    }
}
