package pe.edu.utp.vacunacioncard.service.salud;

import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import java.util.List;
import java.util.Optional;

public interface ICondicionMedicaService {

    /** Obtiene todas las condiciones médicas registradas. */
    List<CondicionMedica> getAll();

    /** Busca una condición médica por su identificador único. */
    Optional<CondicionMedica> getById(Long id);

    /** Registra una nueva condición médica. */
    CondicionMedica create(CondicionMedica condicion);

    /** Actualiza los datos de una condición médica existente. */
    CondicionMedica update(CondicionMedica condicion);

    /** Elimina una condición médica del sistema. */
    void deleteById(Long id);

    /** Busca una condición médica por su código CIE-10. */
    Optional<CondicionMedica> findByIcd10Code(String codigoCIE10);

    /** Lista las condiciones médicas según su estado activo/inactivo. */
    List<CondicionMedica> findByStatus(boolean activa);
}