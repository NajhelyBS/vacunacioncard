package pe.edu.utp.vacunacioncard.model.campania;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;

/**
 * Clase CampaniaVacunacion que representa una campaña de vacunación.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class CampaniaVacunacion {
    private String id;
    private String nombre;
    private String descripcion;
    private Vacuna vacuna;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<String> gruposObjetivo;
    private int metaVacunacion;
    private int vacunadosActuales;
    private String estado;

    public CampaniaVacunacion(String nombre, Vacuna vacuna, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.vacuna = vacuna;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.gruposObjetivo = new ArrayList<>();
        this.estado = "PLANEADA";
        this.vacunadosActuales = 0;
    }

}
