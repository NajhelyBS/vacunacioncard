package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase Laboratorio que representa el laboratorio farmacéutico que produce una vacuna.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Builder
@Entity
@Table(name = "mae_laboratorio")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Laboratorio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "pais_origen", nullable = false)
    private String paisOrigen;
}
