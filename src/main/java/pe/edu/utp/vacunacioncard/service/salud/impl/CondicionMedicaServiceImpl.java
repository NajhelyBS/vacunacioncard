package pe.edu.utp.vacunacioncard.service.salud.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import pe.edu.utp.vacunacioncard.repository.salud.CondicionMedicaRepository;
import pe.edu.utp.vacunacioncard.service.salud.ICondicionMedicaService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración de antecedentes y condiciones de salud.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CondicionMedicaServiceImpl implements ICondicionMedicaService {

    private final CondicionMedicaRepository repo;

    /***
     * Recupera todas las condiciones médicas registradas en el sistema con control de lectura.
     * @return Lista de objetos {@link CondicionMedica}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CondicionMedica> listarTodas() {
        log.info("Iniciando consulta del catálogo global de condiciones médicas");
        return repo.findAll();
    }

    /***
     * Recupera el registro de una condición médica específica mediante su identificador único.
     * @param id El identificador único de la condición médica.
     * @return Un {@link Optional} que contiene la condición médica si es hallada.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CondicionMedica> obtenerPorId(Long id) {
        log.info("Buscando condición médica por ID: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra o actualiza una condición médica en el catálogo médico con control de persistencia.
     * @param condicion El objeto condición médica con las especificaciones clínicas.
     * @return La {@link CondicionMedica} guardada con éxito, o null si ocurre un fallo.
     */
    @Override
    @Transactional
    public CondicionMedica registrar(CondicionMedica condicion) {
        log.info("Persistiendo registro de condición médica: {}", condicion.getNombre());
        try {
            CondicionMedica guardada = repo.save(condicion);
                log.info("Condición médica grabada con éxito asignando ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al guardar la condición [{}]: {}", 
                    condicion.getNombre(), e.getMessage());
            return null;
        }
    }

    /***
     * Busca una condición médica por su código estandarizado internacional.
     * @param codigoCIE10 Código CIE-10 a consultar.
     * @return Un {@link Optional} con el resultado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CondicionMedica> obtenerPorCodigoCIE10(String codigoCIE10) {
        log.info("Solicitando búsqueda clínica por código CIE-10: {}", codigoCIE10);
        return repo.findByCodigoCIE10IgnoreCase(codigoCIE10);
    }

    /***
     * Filtra las condiciones patológicas según su estado de vigencia clínica actual.
     * @param activa Estado lógico de la condición.
     * @return Lista de objetos {@link CondicionMedica}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CondicionMedica> listarPorEstado(boolean activa) {
        log.info("Filtrando condiciones médicas según vigencia clínica (activa): {}", activa);
        return repo.findByActiva(activa);
    }
}
