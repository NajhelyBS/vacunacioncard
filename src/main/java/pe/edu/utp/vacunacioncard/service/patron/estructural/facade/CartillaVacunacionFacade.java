package pe.edu.utp.vacunacioncard.service.patron.estructural.facade;

import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

/**
 * Fachada que simplifica el proceso completo de registrar la aplicación
 * de una dosis de vacuna, ocultando la coordinación entre los subsistemas
 * de registro de dosis, cartilla de vacunación y esquema de vacunación.
 */
public interface CartillaVacunacionFacade {

    /**
     * Orquesta el registro completo de una dosis aplicada: guarda el registro
     * de vacunación, lo asocia a la cartilla del paciente y, si corresponde,
     * actualiza el estado del esquema de vacunación asignado.
     *
     * @param registro   datos de la dosis aplicada (vacuna, enfermero, lote, etc.).
     * @param cartillaId identificador de la cartilla del paciente a actualizar.
     * @return el registro de vacuna persistido, con su ID generado.
     */
    RegistroVacuna registrarDosisYActualizarCartilla(RegistroVacuna registro, Long cartillaId);

    /**
     * Orquesta la consulta del estado completo de vacunación de un paciente,
     * combinando su cartilla y los esquemas de vacunación que tiene asignados.
     *
     * @param pacienteId identificador del paciente.
     * @return la cartilla del paciente, con sus esquemas asignados actualizados.
     */
    CartillaVacunacion consultarEstadoVacunacion(Long pacienteId);
}