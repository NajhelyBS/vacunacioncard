package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import pe.edu.utp.vacunacioncard.repository.vacunacion.CartillaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.ICartillaVacunacionService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartillaVacunacionServiceImpl implements ICartillaVacunacionService {

    private final CartillaVacunacionRepository repo;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<CartillaVacunacion> getAll() {
        log.info("Listando todas las cartillas de vacunación");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartillaVacunacion> getById(Long id) {
        log.info("Buscando cartilla por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public CartillaVacunacion create(CartillaVacunacion cartilla) {
        log.info("Creando cartilla del paciente ID: {}",
                cartilla.getPaciente() != null ? cartilla.getPaciente().getId() : "N/A");
        try {
            CartillaVacunacion guardada = repo.save(cartilla);
            log.info("Cartilla guardada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar cartilla de vacunación", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public CartillaVacunacion update(CartillaVacunacion cartilla) {
        log.info("Actualizando cartilla ID: {}", cartilla.getId());
        try {
            if (!repo.existsById(cartilla.getId())) {
                throw new ServiceException("No existe la cartilla con ID: " + cartilla.getId(), null);
            }
            return repo.save(cartilla);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar cartilla ID: " + cartilla.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deactivate(Long id) {
        log.info("Desactivando cartilla con ID: {}", id);
        try {
            repo.findById(id).ifPresentOrElse(cartilla -> {
                cartilla.setActiva(false);
                cartilla.setEstado("INACTIVA");
                repo.save(cartilla);
                log.info("Cartilla ID: {} desactivada", id);
            }, () -> {
                throw new ServiceException("No existe la cartilla con ID: " + id, null);
            });
        } catch (DataAccessException e) {
            throw new ServiceException("Error al desactivar cartilla con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartillaVacunacion> findByPatient(Long pacienteId) {
        log.info("Buscando cartilla del paciente ID: {}", pacienteId);
        return repo.findByPacienteId(pacienteId);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartillaVacunacion> findByQrCode(String codigoQR) {
        log.info("Buscando cartilla por código QR");
        return repo.findByCodigoQR(codigoQR);
    }
}