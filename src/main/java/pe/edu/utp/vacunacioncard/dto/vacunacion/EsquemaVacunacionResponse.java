package pe.edu.utp.vacunacioncard.dto.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion;
import java.time.LocalDate;

public record EsquemaVacunacionResponse(
        Long id,
        String nombre,
        String descripcion,
        Long pacienteId,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String estado) {

    public static EsquemaVacunacionResponse from(EsquemaVacunacion e) {
        return new EsquemaVacunacionResponse(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getPacienteAsignado() != null ? e.getPacienteAsignado().getId() : null,
                e.getFechaInicio(),
                e.getFechaFin(),
                e.getEstado());
    }
}