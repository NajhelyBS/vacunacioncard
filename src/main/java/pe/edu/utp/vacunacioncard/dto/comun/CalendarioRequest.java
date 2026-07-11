package pe.edu.utp.vacunacioncard.dto.comun;


import pe.edu.utp.vacunacioncard.model.comun.Calendario;
import java.time.LocalDate;


/**
 * Datos de entrada para registrar un día en el calendario del sistema.
 * Expone únicamente los campos que el cliente puede definir
 */
public record CalendarioRequest(
        LocalDate fecha,
        boolean esHabil,
        String descripcionFeriado) {


    public Calendario toEntity() {
        Calendario calendario = new Calendario();
        calendario.setFecha(fecha);
        calendario.setEsHabil(esHabil);
        calendario.setDescripcionFeriado(descripcionFeriado);
        return calendario;
    }
}
