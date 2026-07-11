package pe.edu.utp.vacunacioncard.dto.salud;

import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;

public record CondicionMedicaResponse(Long id, String nombre, String codigoCIE10, boolean activa) {
    public static CondicionMedicaResponse from(CondicionMedica c) {
        return new CondicionMedicaResponse(c.getId(), c.getNombre(), c.getCodigoCIE10(), c.isActiva());
    }
}