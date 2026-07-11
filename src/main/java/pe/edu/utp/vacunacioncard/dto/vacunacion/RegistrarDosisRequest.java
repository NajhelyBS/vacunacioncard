package pe.edu.utp.vacunacioncard.dto.vacunacion;

import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import java.time.LocalDateTime;

public record RegistrarDosisRequest(
        Long cartillaId,
        Long vacunaId,
        int numeroDosis,
        LocalDateTime fechaAplicacion,
        Long enfermeroAplicadorId,
        String lote,
        LocalDateTime proximaDosis) {

    public RegistroVacuna toEntity() {
        RegistroVacuna r = new RegistroVacuna();
        if (vacunaId != null) {
            Vacuna v = new Vacuna();
            v.setId(vacunaId);
            r.setVacuna(v);
        }
        r.setNumeroDosis(numeroDosis);
        r.setFechaAplicacion(fechaAplicacion);
        if (enfermeroAplicadorId != null) {
            Enfermero e = new Enfermero();
            e.setId(enfermeroAplicadorId);
            r.setEnfermeroAplicador(e);
        }
        r.setLote(lote);
        r.setProximaDosis(proximaDosis);
        return r;
    }
}