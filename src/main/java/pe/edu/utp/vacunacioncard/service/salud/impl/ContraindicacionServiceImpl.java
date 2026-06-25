package pe.edu.utp.vacunacioncard.service.salud.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;
import pe.edu.utp.vacunacioncard.repository.salud.ContraindicacionRepository;
import pe.edu.utp.vacunacioncard.service.salud.IContraindicacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración de restricciones y contraindicaciones de vacunas.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContraindicacionServiceImpl implements IContraindicacionService {

    private final ContraindicacionRepository repo;

    /***
     * Recupera todas las contraindicaciones registradas en el sistema con control de lectura.
     * @return Lista de objetos {@link Contraindicacion}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contraindicacion> listarTodas() {
        log.info("Ejecutando consulta global del catálogo de contraindicaciones médicas");
        return repo.findAll();
    }

    /***
     * Recupera el registro de una contraindicación específica mediante su identificador único.
     * @param id El identificador único de la contraindicación.
     * @return Un {@link Optional} que contiene el registro si es hallado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Contraindicacion> obtenerPorId(Long id) {
        log.info("Buscando registro de contraindicación con ID: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra o actualiza una contraindicación médica con control de persistencia y errores.
     * @param contraindicacion El objeto contraindicación a guardar.
     * @return La {@link Contraindicacion} guardada con éxito, o null si ocurre un fallo.
     */
    @Override
    @Transactional
    public Contraindicacion registrar(Contraindicacion contraindicacion) {
        log.info("Iniciando almacenamiento de contraindicación médica preventiva");
        try {
            Contraindicacion guardada = repo.save(contraindicacion);
            log.info("Contraindicación registrada exitosamente con el ID generado: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar la contraindicación: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Obtiene el catálogo de restricciones médicas vinculadas a una vacuna en particular.
     * @param vacunaId Identificador único de la vacuna.
     * @return Lista de objetos {@link Contraindicacion}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contraindicacion> listarPorVacunaAfectada(Long vacunaId) {
        log.info("Consultando restricciones médicas específicas aplicadas a la vacuna ID: {}", vacunaId);
        return repo.findByVacunaAfectadaId(vacunaId);
    }
}
