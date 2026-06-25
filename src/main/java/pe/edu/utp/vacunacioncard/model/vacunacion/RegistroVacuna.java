package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;

/**
 * Clase RegistroVacuna que representa el registro de aplicacion de una vacuna.
 * Contiene informacion de la dosis aplicada.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Builder
@Entity
@Table(name = "mae_registro_vacuna")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistroVacuna implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vacuna_id")
    private Vacuna vacuna;

    @Column(name = "numero_dosis")
    private int numeroDosis;

    @Column(name = "fecha_aplicacion")
    private LocalDateTime fechaAplicacion;

    @ManyToOne
    @JoinColumn(name = "enfermero_aplicador_id")
    private Enfermero enfermeroAplicador;

    @Column(name = "lote")
    private String lote;

    @Embedded
    private UbicacionAplicacion lugarAplicacion;

    @Builder.Default
    @Column(name = "estado")
    private String estado = "APLICADA";

    @Column(name = "reacciones_adversas")
    private String reaccionesAdversas;

    @Column(name = "proxima_dosis")
    private LocalDateTime proximaDosis;
}
