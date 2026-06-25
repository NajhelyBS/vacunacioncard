package pe.edu.utp.vacunacioncard.service.salud.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import pe.edu.utp.vacunacioncard.repository.salud.AlergiaRepository;
import pe.edu.utp.vacunacioncard.service.salud.IAlergiaService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración del catálogo de alergias del paciente.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiaServiceImpl implements IAlergiaService {

    private final AlergiaRepository repo;

    /***
     * Recupera todas las alergias registradas en el sistema con control de lectura.
     * @return Lista de objetos {@link Alergia}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Alergia> listarTodas() {
        log.info("Ejecutando consulta global del catálogo maestro de alergias");
        return repo.findAll();
    }

    /***
     * Recupera el registro de una alergia específica mediante su identificador único.
     * @param id El identificador único de la alergia.
     * @return Un {@link Optional} que contiene la alergia si es hallada.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Alergia> obtenerPorId(Long id) {
        log.info("Buscando registro de alergia con ID: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra o actualiza una alergia en el catálogo médico con control de persistencia.
     * @param alergia El objeto alergia con las especificaciones clínicas.
     * @return La {@link Alergia} guardada con éxito, o null si ocurre un fallo.
     */
    @Override
    @Transactional
    public Alergia registrar(Alergia alergia) {
        log.info("Iniciando persistencia de nueva alergia en el catálogo: {}", alergia.getNombre());
        try {
            Alergia alergiaGuardada = repo.save(alergia);
            log.info("Alergia registrada exitosamente bajo el ID generado: {}", alergiaGuardada.getId());
            return alergiaGuardada;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar la alergia [{}]: {}", 
                    alergia.getNombre(), e.getMessage());
            return null;
        }
    }

    /***
     * Filtra las alergias del sistema según su nivel de peligrosidad o severidad.
     * @param severidad Criterio de severidad clínica.
     * @return Lista de objetos {@link Alergia}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Alergia> listarPorSeveridad(String severidad) {
        log.info("Filtrando catálogo de alergias por nivel de severidad: {}", severidad);
        return repo.findBySeveridadIgnoreCase(severidad);
    }
}
