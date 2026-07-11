package pe.edu.utp.vacunacioncard.dto.cita;


import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import java.time.LocalTime;


/**
 * Datos de entrada para registrar el horario de atención de un día.
 */
public record HorarioRequest(
        DiaSemana diaSemana,
        LocalTime horaInicio,
        LocalTime horaFin,
        int capacidadPorIntervalo) {




    public Horario toEntity() {
        Horario horario = new Horario();
        horario.setDiaSemana(diaSemana);
        horario.setHoraInicio(horaInicio);
        horario.setHoraFin(horaFin);
        horario.setCapacidadPorIntervalo(capacidadPorIntervalo);
        return horario;
    }
}
