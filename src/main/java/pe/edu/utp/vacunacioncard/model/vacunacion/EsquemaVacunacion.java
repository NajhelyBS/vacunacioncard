package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private final String id = UUID.randomUUID().toString();
    private String nombre;
    private String descripcion;
    private List<Vacuna> vacunasRequeridas = new ArrayList<>();
    private List<Integer> edadesRecomendadas = new ArrayList<>();
    private Paciente pacienteAsignado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado = "PENDIENTE";

    public EsquemaVacunacion(String nombre, String descripcion) {
        if (nombre == null || nombre.isBlank() || descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("Datos obligatorios (Nombre y Descripción) no pueden ser nulos o vacíos");
        }
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
