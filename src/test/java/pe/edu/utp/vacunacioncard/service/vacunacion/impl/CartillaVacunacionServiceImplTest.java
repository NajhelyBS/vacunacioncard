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
import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import pe.edu.utp.vacunacioncard.repository.vacunacion.CartillaVacunacionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - CartillaVacunacionServiceImpl")
class CartillaVacunacionServiceImplTest {

    @Mock
    private CartillaVacunacionRepository repo;

    @InjectMocks
    private CartillaVacunacionServiceImpl service;

    private CartillaVacunacion crearCartilla() {
        Paciente paciente = new Paciente();
        paciente.setId(2L);

        CartillaVacunacion cartilla = new CartillaVacunacion();
        cartilla.setId(1L);
        cartilla.setCodigoQR("QR-ABC-123");
        cartilla.setPaciente(paciente);
        cartilla.setActiva(true);
        cartilla.setEstado("ACTIVA");
        return cartilla;
    }

    @Test
    @DisplayName("getAll retorna todas las cartillas de vacunación registradas")
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(crearCartilla()));

        List<CartillaVacunacion> resultado = service.getAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("getById retorna la cartilla si el identificador existe")
    void getById_existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(crearCartilla()));

        Optional<CartillaVacunacion> resultado = service.getById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("QR-ABC-123", resultado.get().getCodigoQR());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById retorna Optional.empty si el identificador no existe")
    void getById_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<CartillaVacunacion> resultado = service.getById(99L);

        assertTrue(resultado.isEmpty());
        verify(repo, times(1)).findById(99L);
    }

    @Test
    @DisplayName("create guarda y retorna la nueva cartilla del paciente")
    void create() {
        CartillaVacunacion cartilla = crearCartilla();
        when(repo.save(cartilla)).thenReturn(cartilla);

        CartillaVacunacion resultado = service.create(cartilla);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repo, times(1)).save(cartilla);
    }

    @Test
    @DisplayName("create guarda correctamente aunque la cartilla no tenga paciente asociado")
    void create_sinPaciente() {
        CartillaVacunacion cartilla = crearCartilla();
        cartilla.setPaciente(null);
        when(repo.save(cartilla)).thenReturn(cartilla);

        CartillaVacunacion resultado = service.create(cartilla);

        assertNotNull(resultado);
        verify(repo, times(1)).save(cartilla);
    }

    @Test
    @DisplayName("create propaga una ServiceException ante fallos de persistencia")
    void create_lanzaServiceException() {
        CartillaVacunacion cartilla = crearCartilla();
        when(repo.save(cartilla)).thenThrow(new DataRetrievalFailureException("Error DB simulado"));

        assertThrows(ServiceException.class, () -> service.create(cartilla));
        verify(repo, times(1)).save(cartilla);
    }

    @Test
    @DisplayName("update modifica la cartilla si su identificador existe")
    void update_existe() {
        CartillaVacunacion cartilla = crearCartilla();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(cartilla)).thenReturn(cartilla);

        CartillaVacunacion resultado = service.update(cartilla);

        assertNotNull(resultado);
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).save(cartilla);
    }

    @Test
    @DisplayName("update lanza ServiceException si la cartilla a modificar no existe")
    void update_noExiste() {
        CartillaVacunacion cartilla = crearCartilla();
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(ServiceException.class, () -> service.update(cartilla));
        verify(repo, never()).save(any(CartillaVacunacion.class));
    }

    @Test
    @DisplayName("update propaga una ServiceException ante fallos de persistencia (cubre bloque catch)")
    void update_fallaPersistencia() {
        CartillaVacunacion cartilla = crearCartilla();
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(cartilla)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.update(cartilla));
        verify(repo, times(1)).save(cartilla);
    }

    @Test
    @DisplayName("deactivate realiza la baja lógica marcando la cartilla como inactiva")
    void deactivate_existe() {
        CartillaVacunacion cartilla = crearCartilla();
        when(repo.findById(1L)).thenReturn(Optional.of(cartilla));
        when(repo.save(cartilla)).thenReturn(cartilla);

        service.deactivate(1L);

        assertFalse(cartilla.isActiva());
        assertEquals("INACTIVA", cartilla.getEstado());
        verify(repo, times(1)).save(cartilla);
    }

    @Test
    @DisplayName("deactivate lanza ServiceException si la cartilla no existe")
    void deactivate_noExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> service.deactivate(99L));
        verify(repo, never()).save(any(CartillaVacunacion.class));
    }

    @Test
    @DisplayName("deactivate propaga una ServiceException ante fallos de persistencia (cubre bloque catch)")
    void deactivate_fallaPersistencia() {
        CartillaVacunacion cartilla = crearCartilla();
        when(repo.findById(1L)).thenReturn(Optional.of(cartilla));
        when(repo.save(cartilla)).thenThrow(new DataRetrievalFailureException("Fallo persistencia"));

        assertThrows(ServiceException.class, () -> service.deactivate(1L));
        verify(repo, times(1)).save(cartilla);
    }

    @Test
    @DisplayName("findByPatient retorna la cartilla asociada a un paciente")
    void findByPatient() {
        when(repo.findByPacienteId(2L)).thenReturn(Optional.of(crearCartilla()));

        Optional<CartillaVacunacion> resultado = service.findByPatient(2L);

        assertTrue(resultado.isPresent());
        verify(repo, times(1)).findByPacienteId(2L);
    }

    @Test
    @DisplayName("findByQrCode retorna la cartilla identificada por su código QR")
    void findByQrCode() {
        when(repo.findByCodigoQR("QR-ABC-123")).thenReturn(Optional.of(crearCartilla()));

        Optional<CartillaVacunacion> resultado = service.findByQrCode("QR-ABC-123");

        assertTrue(resultado.isPresent());
        assertEquals("QR-ABC-123", resultado.get().getCodigoQR());
        verify(repo, times(1)).findByCodigoQR("QR-ABC-123");
    }
}
