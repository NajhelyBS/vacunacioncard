package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private String id;
    private String nombre;
    private Laboratorio laboratorio;
    private int dosisRequeridas;
    private int intervaloDias;
    private String viaAdministracion;
    private String temperaturaAlmacenamiento;
    private boolean disponible;
    private List<String> efectosSecundarios;
    private String contraindicaciones;
    private LocalDate fechaVencimiento;

    public Vacuna(String nombre, Laboratorio laboratorio, int dosisRequeridas) {
        this.id = java.util.UUID.randomUUID().toString();
        this.nombre = nombre;
        this.laboratorio = laboratorio;
        this.dosisRequeridas = dosisRequeridas;
        this.disponible = true;
        this.efectosSecundarios = new ArrayList<>();
    }
}
