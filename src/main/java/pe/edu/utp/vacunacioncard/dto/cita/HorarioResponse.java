
package pe.edu.utp.vacunacioncard.dto.cita;


import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import java.time.LocalTime;


public record HorarioResponse(
        Long id,
        DiaSemana diaSemana,
        LocalTime horaInicio,
        LocalTime horaFin,
        int capacidadPorIntervalo) {


    public static HorarioResponse from(Horario h) {
        return new HorarioResponse(
                h.getId(),
                h.getDiaSemana(),
                h.getHoraInicio(),
                h.getHoraFin(),
                h.getCapacidadPorIntervalo());
    }
}

