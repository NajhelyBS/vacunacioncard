package pe.edu.utp.vacunacioncard.dto.salud;

import pe.edu.utp.vacunacioncard.model.salud.Alergia;

public record AlergiaResponse(Long id, String nombre, String tipo, String severidad) {
    public static AlergiaResponse from(Alergia a) {
        return new AlergiaResponse(a.getId(), a.getNombre(), a.getTipo(), a.getSeveridad());
    }
}