package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.SeguroMedico;
import pe.edu.utp.vacunacioncard.repository.usuario.SeguroMedicoRepository;
import pe.edu.utp.vacunacioncard.service.usuario.ISeguroMedicoService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeguroMedicoServiceImpl implements ISeguroMedicoService {

    private final SeguroMedicoRepository repo;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<SeguroMedico> getAll() {
        log.info("Listando todos los seguros médicos");
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<SeguroMedico> getById(Long id) {
        log.info("Buscando seguro médico por ID: {}", id);
        return repo.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public SeguroMedico create(SeguroMedico seguro) {
        log.info("Registrando seguro médico póliza: {}", seguro.getNumeroPoliza());
        try {
            SeguroMedico guardado = repo.save(seguro);
            log.info("Seguro médico registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar seguro médico", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public SeguroMedico update(SeguroMedico seguro) {
        log.info("Actualizando seguro médico ID: {}", seguro.getId());
        try {
            if (!repo.existsById(seguro.getId())) {
                throw new ServiceException("No existe el seguro médico con ID: " + seguro.getId(), null);
            }
            return repo.save(seguro);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar seguro médico ID: " + seguro.getId(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando seguro médico con ID: {}", id);
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar seguro médico con ID: " + id, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<SeguroMedico> findByPolicyNumber(String numeroPoliza) {
        log.info("Buscando seguro por póliza: {}", numeroPoliza);
        return repo.findByNumeroPoliza(numeroPoliza);
    }
}
