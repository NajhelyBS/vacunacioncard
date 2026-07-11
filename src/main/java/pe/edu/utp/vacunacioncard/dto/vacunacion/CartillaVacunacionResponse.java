package pe.edu.utp.vacunacioncard.dto.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;

public record CartillaVacunacionResponse(
        Long id,
        Long pacienteId,
        String pacienteNombre,
        String codigoQR,
        int cantidadRegistros,
        boolean activa,
        String estado) {

    public static CartillaVacunacionResponse from(CartillaVacunacion c) {
        return new CartillaVacunacionResponse(
                c.getId(),
                c.getPaciente() != null ? c.getPaciente().getId() : null,
                c.getPaciente() != null ? c.getPaciente().getNombreCompleto() : null,
                c.getCodigoQR(),
                c.getRegistrosVacunacion() != null ? c.getRegistrosVacunacion().size() : 0,
                c.isActiva(),
                c.getEstado());
    }
}