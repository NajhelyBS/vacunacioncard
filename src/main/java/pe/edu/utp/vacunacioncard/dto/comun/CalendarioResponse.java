package pe.edu.utp.vacunacioncard.dto.comun;


import pe.edu.utp.vacunacioncard.model.comun.Calendario;
import java.time.LocalDate;


public record CalendarioResponse(
        Long id,
        LocalDate fecha,
        boolean esHabil,
        String descripcionFeriado) {


    public static CalendarioResponse from(Calendario c) {
        return new CalendarioResponse(
                c.getId(),
                c.getFecha(),
                c.isEsHabil(),
                c.getDescripcionFeriado());
    }
}

