package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.repository.vacunacion.RegistroVacunaRepository;
import pe.edu.utp.vacunacioncard.service.patron.observer.NotificadorVacunacionConcreto;
import pe.edu.utp.vacunacioncard.service.vacunacion.IRegistroVacunaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistroVacunaServiceImpl implements IRegistroVacunaService {

    private final RegistroVacunaRepository repo;
    private final NotificadorVacunacionConcreto notificador;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroVacuna> getAll() {
        log.info("Listando todos los registros de vacunas");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroVacuna> getById(Long id) {
        log.info("Buscando registro de vacuna por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public RegistroVacuna create(RegistroVacuna registro) {
        log.info("Guardando registro de dosis N° {}", registro.getNumeroDosis());
        try {
            RegistroVacuna guardado = repo.save(registro);
            log.info("Registro de vacuna guardado con ID: {}", guardado.getId());

            // Patrón Observer: al aplicar la dosis se notifica a cartilla y auditoría,
            // y si hay una próxima dosis programada, al recordatorio.
            notificador.dosisAplicada(guardado);
            if (guardado.getProximaDosis() != null) {
                notificador.programarProximaDosis(guardado);
            }

            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar registro de vacuna", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public RegistroVacuna update(RegistroVacuna registro) {
        log.info("Actualizando registro de vacuna ID: {}", registro.getId());
        try {
            if (!repo.existsById(registro.getId())) {
                throw new ServiceException("No existe el registro de vacuna con ID: " + registro.getId(), null);
            }
            return repo.save(registro);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar registro de vacuna ID: " + registro.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando registro de vacuna con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar registro de vacuna con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroVacuna> findByBatch(String lote) {
        log.info("Listando registros por lote: {}", lote);
        return repo.findByLoteIgnoreCase(lote);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroVacuna> findByNurse(Long enfermeroId) {
        log.info("Listando registros del enfermero ID: {}", enfermeroId);
        return repo.findByEnfermeroAplicadorId(enfermeroId);
    }
}