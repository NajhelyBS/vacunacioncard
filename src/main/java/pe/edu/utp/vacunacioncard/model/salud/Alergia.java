package pe.edu.utp.vacunacioncard.model.salud;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase alergia que representa una alergia del paciente.
 
 */

@Builder
@Entity
@Table(name = "mae_alergia")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Alergia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "severidad")
    private String severidad;

    @Column(name = "sintomas")
    private String sintomas;

    @Column(name = "tratamiento_recomendado")
    private String tratamientoRecomendado;
}
