package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.repository.vacunacion.RegistroVacunaRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.IRegistroVacunaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistroVacunaServiceImpl implements IRegistroVacunaService {

    private final RegistroVacunaRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<RegistroVacuna> listarTodos() {
        log.info("Listando todos los registros de vacunas");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroVacuna> obtenerPorId(Long id) {
        log.info("Buscando registro de vacuna por ID: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public RegistroVacuna guardar(RegistroVacuna registro) {
        log.info("Guardando registro de dosis N° {} para vacuna ID: {}",
                registro.getNumeroDosis(),
                registro.getVacuna() != null ? registro.getVacuna().getId() : "N/A");
        try {
            RegistroVacuna guardado = repo.save(registro);
            log.info("Registro de vacuna guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar registro de vacuna", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroVacuna> listarPorLote(String lote) {
        log.info("Listando registros por lote: {}", lote);
        return repo.findByLoteIgnoreCase(lote);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroVacuna> listarPorEnfermero(Long enfermeroId) {
        log.info("Listando registros del enfermero ID: {}", enfermeroId);
        return repo.findByEnfermeroAplicadorId(enfermeroId);
    }
}
