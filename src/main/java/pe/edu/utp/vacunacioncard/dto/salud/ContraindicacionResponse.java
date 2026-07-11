package pe.edu.utp.vacunacioncard.dto.salud;

import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;

public record ContraindicacionResponse(Long id, String descripcion, String severidad, String vacunaAfectadaNombre) {
    public static ContraindicacionResponse from(Contraindicacion c) {
        return new ContraindicacionResponse(
                c.getId(),
                c.getDescripcion(),
                c.getSeveridad(),
                c.getVacunaAfectada() != null ? c.getVacunaAfectada().getNombre() : null);
    }
}