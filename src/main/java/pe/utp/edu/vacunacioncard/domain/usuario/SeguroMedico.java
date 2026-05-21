package pe.utp.edu.vacunacioncard.domain.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String id;
    private String nombre;
    private String numeroPoliza;
    private String cobertura;

    public SeguroMedico(String nombre, String numeroPoliza, String cobertura) {
        this.id = java.util.UUID.randomUUID().toString();
        this.nombre = nombre;
        this.numeroPoliza = numeroPoliza;
        this.cobertura = cobertura;
    }
}