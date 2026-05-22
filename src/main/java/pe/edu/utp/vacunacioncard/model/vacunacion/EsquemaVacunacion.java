package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;

/**
 * Clase EsquemaVacunacion que representa el esquema de vacunación que debe seguir un paciente.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class EsquemaVacunacion {
    private String id;
    private String nombre;
    private String descripcion;
    private List<Vacuna> vacunasRequeridas;
    private List<Integer> edadesRecomendadas;
    private Paciente pacienteAsignado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;

    public EsquemaVacunacion(String nombre, String descripcion) {
        this.id = java.util.UUID.randomUUID().toString();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.vacunasRequeridas = new ArrayList<>();
        this.edadesRecomendadas = new ArrayList<>();
        this.estado = "PENDIENTE";
    }
}
