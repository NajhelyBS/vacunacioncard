package pe.edu.utp.vacunacioncard.model.salud;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase CondicionMedica que representa una condicion medica del paciente.
 *
 */

@Builder
@Entity
@Table(name = "mae_condicion_medica")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CondicionMedica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "codigo_cie10")
    private String codigoCIE10;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tratamiento")
    private String tratamiento;

    @Column(name = "activa")
    @Builder.Default
    private boolean activa = true;
}
