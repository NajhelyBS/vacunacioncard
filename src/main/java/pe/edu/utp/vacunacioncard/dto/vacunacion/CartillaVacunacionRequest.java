package pe.edu.utp.vacunacioncard.dto.vacunacion;

import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;

/**
 * Datos de entrada para crear o actualizar una cartilla de vacunación.
 * El paciente se referencia por su identificador; los registros y esquemas se gestionan aparte.
 */
public record CartillaVacunacionRequest(
        Long pacienteId,
        String codigoQR,
        String estado,
        Boolean activa) {

    /**
     * Construye una entidad {@link CartillaVacunacion} a partir de los datos recibidos.
     *
     * @return la entidad lista para persistir.
     */
    public CartillaVacunacion toEntity() {
        CartillaVacunacion cartilla = new CartillaVacunacion();
        if (pacienteId != null) {
            Paciente paciente = new Paciente();
            paciente.setId(pacienteId);
            cartilla.setPaciente(paciente);
        }
        cartilla.setCodigoQR(codigoQR);
        if (estado != null) {
            cartilla.setEstado(estado);
        }
        if (activa != null) {
            cartilla.setActiva(activa);
        }
        return cartilla;
    }
}
