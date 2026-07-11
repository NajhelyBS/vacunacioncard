package pe.edu.utp.vacunacioncard.service.patron.estructural.facade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.service.patron.estructural.facade.impl.CartillaVacunacionFacadeImpl;
import pe.edu.utp.vacunacioncard.service.vacunacion.ICartillaVacunacionService;
import pe.edu.utp.vacunacioncard.service.vacunacion.IEsquemaVacunacionService;
import pe.edu.utp.vacunacioncard.service.vacunacion.IRegistroVacunaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Facade - CartillaVacunacionFacadeImpl")
class CartillaVacunacionFacadeImplTest {

    @Mock
    private IRegistroVacunaService registroVacunaService;
    @Mock
    private ICartillaVacunacionService cartillaService;
    @Mock
    private IEsquemaVacunacionService esquemaService;

    @InjectMocks
    private CartillaVacunacionFacadeImpl facade;

    @Test
    @DisplayName("Registra la dosis y la asocia a la cartilla existente")
    void registrarDosisYActualizarCartilla_exito() {
        RegistroVacuna registro = new RegistroVacuna();
        RegistroVacuna guardado = new RegistroVacuna();
        guardado.setId(1L);

        CartillaVacunacion cartilla = new CartillaVacunacion();
        cartilla.setId(10L);
        cartilla.setRegistrosVacunacion(new ArrayList<>());

        when(registroVacunaService.create(registro)).thenReturn(guardado);
        when(cartillaService.getById(10L)).thenReturn(Optional.of(cartilla));
        when(cartillaService.update(cartilla)).thenReturn(cartilla);

        RegistroVacuna resultado = facade.registrarDosisYActualizarCartilla(registro, 10L);

        assertEquals(1L, resultado.getId());
        assertTrue(cartilla.getRegistrosVacunacion().contains(guardado));
        verify(cartillaService).update(cartilla);
    }

    @Test
    @DisplayName("Lanza ServiceException si la cartilla no existe")
    void registrarDosisYActualizarCartilla_cartillaNoExiste() {
        RegistroVacuna registro = new RegistroVacuna();
        when(registroVacunaService.create(registro)).thenReturn(new RegistroVacuna());
        when(cartillaService.getById(99L)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class,
                () -> facade.registrarDosisYActualizarCartilla(registro, 99L));
    }

    @Test
    @DisplayName("Consulta el estado de vacunación combinando cartilla y esquemas")
    void consultarEstadoVacunacion_exito() {
        CartillaVacunacion cartilla = new CartillaVacunacion();
        cartilla.setId(5L);

        when(cartillaService.findByPatient(2L)).thenReturn(Optional.of(cartilla));
        when(esquemaService.findByPatient(2L)).thenReturn(List.of());

        CartillaVacunacion resultado = facade.consultarEstadoVacunacion(2L);

        assertEquals(5L, resultado.getId());
        verify(esquemaService).findByPatient(2L);
    }

    @Test
    @DisplayName("Lanza ServiceException si el paciente no tiene cartilla")
    void consultarEstadoVacunacion_sinCartilla() {
        when(cartillaService.findByPatient(3L)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> facade.consultarEstadoVacunacion(3L));
    }
}