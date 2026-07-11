package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.SeguroMedico;
import java.util.List;
import java.util.Optional;

public interface ISeguroMedicoService {

    /** Obtiene todos los seguros médicos registrados. */
    List<SeguroMedico> getAll();

    /** Busca un seguro médico por su identificador único. */
    Optional<SeguroMedico> getById(Long id);

    /** Registra un nuevo seguro médico. */
    SeguroMedico create(SeguroMedico seguro);

    /** Actualiza los datos de un seguro médico existente. */
    SeguroMedico update(SeguroMedico seguro);

    /** Elimina un seguro médico del sistema. */
    void deleteById(Long id);

    /** Busca un seguro médico por su número de póliza. */
    Optional<SeguroMedico> findByPolicyNumber(String numeroPoliza);
}