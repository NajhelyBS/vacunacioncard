package pe.edu.utp.vacunacioncard.model.salud;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;

/**
 * Clase Contraindicacion que representa una contraindicacion para la aplicacion de vacunas.
 *
 */

@Builder
@Entity
@Table(name = "mae_contraindicacion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contraindicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "severidad")
    private String severidad;

    @Column(name = "condicion_asociada")
    private String condicionAsociada;

    @ManyToOne
    @JoinColumn(name = "vacuna_afectada_id")
    private Vacuna vacunaAfectada;
}
