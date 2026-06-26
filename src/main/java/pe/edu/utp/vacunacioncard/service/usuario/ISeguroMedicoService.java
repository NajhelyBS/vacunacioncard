package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.SeguroMedico;
import java.util.List;
import java.util.Optional;

public interface ISeguroMedicoService {

    List<SeguroMedico> listarTodos();

    Optional<SeguroMedico> obtenerPorId(Long id);

    SeguroMedico registrar(SeguroMedico seguro);

    Optional<SeguroMedico> obtenerPorNumeroPoliza(String numeroPoliza);

    void eliminar(Long id);
}
