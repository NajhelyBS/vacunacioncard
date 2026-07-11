package pe.edu.utp.vacunacioncard.service.cita;


import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import java.util.List;
import java.util.Optional;


public interface ICitaVacunacionService {


    List<CitaVacunacion> getAll();


    Optional<CitaVacunacion> findById(Long id);


    CitaVacunacion create(CitaVacunacion cita);


    CitaVacunacion update(CitaVacunacion cita);


    List<CitaVacunacion> findByPatient(Long patientId);


    List<CitaVacunacion> findByStatus(String status);


    void deleteById(Long id);
}
