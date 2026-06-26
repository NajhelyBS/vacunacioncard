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

    @Override
    @Transactional(readOnly = true)
    public List<SeguroMedico> listarTodos() {
        log.info("Listando todos los seguros médicos");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SeguroMedico> obtenerPorId(Long id) {
        log.info("Buscando seguro médico por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public SeguroMedico registrar(SeguroMedico seguro) {
        log.info("Registrando seguro médico póliza: {}", seguro.getNumeroPoliza());
        try {
            SeguroMedico guardado = repo.save(seguro);
            log.info("Seguro médico registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar seguro médico", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SeguroMedico> obtenerPorNumeroPoliza(String numeroPoliza) {
        log.info("Buscando seguro por póliza: {}", numeroPoliza);
        return repo.findByNumeroPoliza(numeroPoliza);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando seguro médico con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Seguro médico eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar seguro médico con ID: " + id, e);
        }
    }
}
