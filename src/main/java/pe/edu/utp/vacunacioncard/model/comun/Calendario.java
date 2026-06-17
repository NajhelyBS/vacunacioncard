package pe.edu.utp.vacunacioncard.model.comun;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase Calendario que representa el calendario de días hábiles y feriados.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
public class Calendario {
    private Map<LocalDate, Boolean> diasHabiles = new HashMap<>();
    private Map<LocalDate, String> feriados = new HashMap<>();

}
