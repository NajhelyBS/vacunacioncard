package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import java.util.List;
import java.util.Optional;

public interface ICartillaVacunacionService {

    List<CartillaVacunacion> listarTodas();

    Optional<CartillaVacunacion> obtenerPorId(Long id);

    CartillaVacunacion guardar(CartillaVacunacion cartilla);

    Optional<CartillaVacunacion> obtenerPorPaciente(Long pacienteId);

    Optional<CartillaVacunacion> obtenerPorCodigoQR(String codigoQR);
}
