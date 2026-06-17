package pe.edu.utp.vacunacioncard.model.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
/**
 * Clase SeguroMedico que representa el seguro médico asociado a un paciente.
 * Contiene información relevante sobre cobertura y póliza.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class SeguroMedico {
    private final String id = UUID.randomUUID().toString();
    private String nombre;
    private String numeroPoliza;
    private String cobertura;

    public SeguroMedico(String nombre, String numeroPoliza, String cobertura) {
        if (nombre == null || numeroPoliza == null || cobertura == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }
        this.nombre = nombre;
        this.numeroPoliza = numeroPoliza;
        this.cobertura = cobertura;
    }
}