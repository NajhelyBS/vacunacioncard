package pe.edu.utp.vacunacioncard.model.campania;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.comun.Contacto;
import pe.edu.utp.vacunacioncard.model.comun.Direccion;

/**
 * Entidad CentroVacunacion que representa un centro de vacunación.
 *
 */

@Builder
@Entity
@Table(name = "mae_centro_vacunacion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CentroVacunacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Embedded
    private Direccion direccion;

    @Embedded
    private Contacto contacto;

    @Column(name = "capacidad_diaria")
    private int capacidadDiaria;

    @Column(name = "personal_disponible")
    private int personalDisponible;

    @Column(name = "horario_atencion")
    private String horarioAtencion;

    
    @Column(name = "activo")
    @Builder.Default
    private boolean activo = true;
}
