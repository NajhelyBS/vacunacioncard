package pe.edu.utp.vacunacioncard.service.campania.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import pe.edu.utp.vacunacioncard.repository.campania.CampaniaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.campania.ICampaniaVacunacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la gestión de campañas de vacunación.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CampaniaVacunacionServiceImpl implements ICampaniaVacunacionService {

    private final CampaniaVacunacionRepository repo;

    /**
     * Recupera todas las campañas registradas en el sistema.
     * @return Lista de campañas.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CampaniaVacunacion> listarTodas() {
        return repo.findAll();
    }

    /**
     * Busca una campaña específica por su ID.
     * @param id Identificador único.
     * @return Optional con la campaña.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CampaniaVacunacion> obtenerPorId(Long id) {
        return repo.findById(id);
    }

    /**
     * Registra una nueva campaña con control de errores.
     * @param campania Datos de la campaña.
     * @return Campaña guardada o null si falló.
     */
    @Override
    @Transactional
    public CampaniaVacunacion registrar(CampaniaVacunacion campania) {
        try {
            return repo.save(campania);
        } catch (DataAccessException e) {
            log.error("Error al registrar la campaña: {}", e.getMessage());
            return null;
        }
    }
}