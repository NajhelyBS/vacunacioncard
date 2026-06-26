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

    @Override
    @Transactional(readOnly = true)
    public List<CartillaVacunacion> listarTodas() {
        log.info("Listando todas las cartillas de vacunación");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartillaVacunacion> obtenerPorId(Long id) {
        log.info("Buscando cartilla por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public CartillaVacunacion guardar(CartillaVacunacion cartilla) {
        log.info("Guardando cartilla del paciente ID: {}",
                cartilla.getPaciente() != null ? cartilla.getPaciente().getId() : "N/A");
        try {
            CartillaVacunacion guardada = repo.save(cartilla);
            log.info("Cartilla guardada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar cartilla de vacunación", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartillaVacunacion> obtenerPorPaciente(Long pacienteId) {
        log.info("Buscando cartilla del paciente ID: {}", pacienteId);
        return repo.findByPacienteId(pacienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartillaVacunacion> obtenerPorCodigoQR(String codigoQR) {
        log.info("Buscando cartilla por código QR");
        return repo.findByCodigoQR(codigoQR);
    }
}
