package pe.edu.utp.vacunacioncard.dto.campania;

import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import java.time.LocalDate;

public record CampaniaVacunacionResponse(
        Long id,
        String nombre,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        int metaVacunacion,
        int vacunadosActuales,
        String estado) {

    public static CampaniaVacunacionResponse from(CampaniaVacunacion c) {
        return new CampaniaVacunacionResponse(
                c.getId(), c.getNombre(), c.getFechaInicio(), c.getFechaFin(),
                c.getMetaVacunacion(), c.getVacunadosActuales(), c.getEstado());
    }
}