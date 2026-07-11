package pe.edu.utp.vacunacioncard.dto.cita;


import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import java.time.LocalDateTime;


public record CitaVacunacionResponse(
        Long id,
        Long pacienteId,
        String pacienteNombre,
        Long vacunaId,
        String vacunaNombre,
        LocalDateTime fechaHora,
        Long centroVacunacionId,
        String centroVacunacionNombre,
        String estado,
        String observaciones) {


    public static CitaVacunacionResponse from(CitaVacunacion c) {
        return new CitaVacunacionResponse(
                c.getId(),
                c.getPaciente() != null ? c.getPaciente().getId() : null,
                c.getPaciente() != null ? c.getPaciente().getNombreCompleto() : null,
                c.getVacuna() != null ? c.getVacuna().getId() : null,
                c.getVacuna() != null ? c.getVacuna().getNombre() : null,
                c.getFechaHora(),
                c.getCentroVacunacion() != null ? c.getCentroVacunacion().getId() : null,
                c.getCentroVacunacion() != null ? c.getCentroVacunacion().getNombre() : null,
                c.getEstado(),
                c.getObservaciones());
    }
}

