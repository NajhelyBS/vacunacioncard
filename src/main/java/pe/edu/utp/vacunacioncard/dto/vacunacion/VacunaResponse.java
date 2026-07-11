package pe.edu.utp.vacunacioncard.dto.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;

public record VacunaResponse(
        Long id,
        String nombre,
        String laboratorioNombre,
        int dosisRequeridas,
        String viaAdministracion,
        boolean disponible) {

    public static VacunaResponse from(Vacuna v) {
        return new VacunaResponse(
                v.getId(),
                v.getNombre(),
                v.getLaboratorio() != null ? v.getLaboratorio().getNombre() : null,
                v.getDosisRequeridas(),
                v.getViaAdministracion(),
                v.isDisponible());
    }
}
