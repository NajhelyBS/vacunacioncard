package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.Laboratorio;
import java.util.List;
import java.util.Optional;

public interface ILaboratorioService {

    /** Obtiene todos los laboratorios registrados. */
    List<Laboratorio> getAll();

    /** Busca un laboratorio por su identificador único. */
    Optional<Laboratorio> getById(Long id);

    /** Registra un nuevo laboratorio. */
    Laboratorio create(Laboratorio laboratorio);

    /** Actualiza los datos de un laboratorio existente. */
    Laboratorio update(Laboratorio laboratorio);

    /** Elimina un laboratorio del sistema. */
    void deleteById(Long id);

    /** Lista los laboratorios según su país de origen. */
    List<Laboratorio> findByCountry(String pais);
}
