package pe.edu.utp.vacunacioncard.model.auth;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

/**
 * Entidad Permiso que define las acciones autorizadas en el sistema.
 */
@Builder
@Entity
@Table(name = "mae_permiso")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Permiso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;
}