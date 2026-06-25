package pe.edu.utp.vacunacioncard.model.comun;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidad Calendario que representa un calendario de días hábiles y feriados.
 */

@Getter
@Setter
public class Calendario {
    private Map<LocalDate, Boolean> diasHabiles = new HashMap<>();
    private Map<LocalDate, String> feriados = new HashMap<>();

}
