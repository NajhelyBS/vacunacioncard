package pe.edu.utp.vacunacioncard.dto.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import java.time.LocalDateTime;

public record RegistroVacunaResponse(
        Long id,
        String vacunaNombre,
        int numeroDosis,
        LocalDateTime fechaAplicacion,
        String enfermeroAplicadorNombre,
        String lote,
        String estado,
        LocalDateTime proximaDosis) {

    public static RegistroVacunaResponse from(RegistroVacuna r) {
        return new RegistroVacunaResponse(
                r.getId(),
                r.getVacuna() != null ? r.getVacuna().getNombre() : null,
                r.getNumeroDosis(),
                r.getFechaAplicacion(),
                r.getEnfermeroAplicador() != null ? r.getEnfermeroAplicador().getNombreCompleto() : null,
                r.getLote(),
                r.getEstado(),
                r.getProximaDosis());
    }
}