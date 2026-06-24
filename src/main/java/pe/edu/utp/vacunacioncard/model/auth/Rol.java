package pe.edu.utp.vacunacioncard.model.auth;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Rol que representa los perfiles de acceso en el sistema.
 */
@Builder
@Entity
@Table(name = "mae_rol")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id")
    @Builder.Default
    private List<Permiso> permisos = new ArrayList<>();
}