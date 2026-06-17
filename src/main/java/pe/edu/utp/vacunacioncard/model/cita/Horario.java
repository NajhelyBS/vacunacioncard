package pe.edu.utp.vacunacioncard.model.cita;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase Horario que representa el horario de atención para vacunación.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class Horario {
    private final String id = UUID.randomUUID().toString();
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int capacidadPorIntervalo;
    private List<String> intervalosDisponibles = new ArrayList<>();

    public Horario(String diaSemana, LocalTime horaInicio, LocalTime horaFin, int capacidadPorIntervalo) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.capacidadPorIntervalo = capacidadPorIntervalo;
    }

}
