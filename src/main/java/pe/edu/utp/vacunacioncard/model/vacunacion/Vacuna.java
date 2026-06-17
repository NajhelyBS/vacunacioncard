package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase Vacuna que representa una vacuna disponible en el sistema.
 * Contiene información médica y de administración.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class Vacuna {
    private final String id = UUID.randomUUID().toString();
    private String nombre;
    private Laboratorio laboratorio;
    private int dosisRequeridas;
    private int intervaloDias;
    private String viaAdministracion;
    private String temperaturaAlmacenamiento;
    private boolean disponible =  true;
    private List<String> efectosSecundarios = new ArrayList<>();
    private String contraindicaciones;
    private LocalDate fechaVencimiento;

    public Vacuna(String nombre, Laboratorio laboratorio, int dosisRequeridas) {
        if (nombre == null || nombre.isBlank() || laboratorio == null || dosisRequeridas <= 0) {
            throw new IllegalArgumentException("Datos obligatorios (Nombre, Laboratorio, Dosis Requeridas) no pueden ser nulos, vacíos o inválidos");
        }
        this.nombre = nombre;
        this.laboratorio = laboratorio;
        this.dosisRequeridas = dosisRequeridas;
    }
}
