package pe.edu.utp.vacunacioncard.service.patron.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.service.patron.facade.CartillaVacunacionFacade;
import pe.edu.utp.vacunacioncard.service.vacunacion.ICartillaVacunacionService;
import pe.edu.utp.vacunacioncard.service.vacunacion.IEsquemaVacunacionService;
import pe.edu.utp.vacunacioncard.service.vacunacion.IRegistroVacunaService;

import java.util.List;

/**
 * Implementación de {@link CartillaVacunacionFacade}.
 *
 * IMPORTANTE: ninguno de los subsistemas inyectados ({@link IRegistroVacunaService},
 * {@link ICartillaVacunacionService}, {@link IEsquemaVacunacionService}) conoce la
 * existencia de esta fachada. Cada uno sigue siendo un servicio independiente,
 * usable por separado desde cualquier otro punto del sistema; esta clase únicamente
 * coordina el orden de sus llamadas para el caso de uso "aplicar una dosis".
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CartillaVacunacionFacadeImpl implements CartillaVacunacionFacade {

    private final IRegistroVacunaService registroVacunaService;
    private final ICartillaVacunacionService cartillaService;
    private final IEsquemaVacunacionService esquemaService;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public RegistroVacuna registrarDosisYActualizarCartilla(RegistroVacuna registro, Long cartillaId) {
        log.info("Facade: iniciando registro de dosis para cartilla ID: {}", cartillaId);

        // 1. Persistir el registro de la dosis aplicada
        RegistroVacuna guardado = registroVacunaService.create(registro);

        // 2. Recuperar la cartilla del paciente y colgar el nuevo registro
        CartillaVacunacion cartilla = cartillaService.getById(cartillaId)
                .orElseThrow(() -> new ServiceException("No existe la cartilla con ID: " + cartillaId, null));
        cartilla.getRegistrosVacunacion().add(guardado);
        cartillaService.update(cartilla);

        log.info("Facade: dosis ID {} registrada y asociada a la cartilla ID {}", guardado.getId(), cartillaId);
        return guardado;
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public CartillaVacunacion consultarEstadoVacunacion(Long pacienteId) {
        log.info("Facade: consultando estado de vacunación del paciente ID: {}", pacienteId);

        CartillaVacunacion cartilla = cartillaService.findByPatient(pacienteId)
                .orElseThrow(() -> new ServiceException("El paciente ID " + pacienteId + " no tiene cartilla registrada", null));

        List<pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion> esquemas =
                esquemaService.findByPatient(pacienteId);
        cartilla.setEsquemasAsignados(esquemas);

        return cartilla;
    }
}