package med.voll.Api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.Api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;

@Component
public class HorarioDeFuncionamientoClinica implements ValidadorDeConsultas {
    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        var domingo = DayOfWeek.SUNDAY.equals(datosAgendarConsulta.fecha());
        var antesDeApertura = datosAgendarConsulta.fecha().getHour()<7;
        var despuesDeApertura = datosAgendarConsulta.fecha().getHour()>19;
        if(domingo || antesDeApertura || despuesDeApertura){
            throw new ValidationException("El horario de atención de la clinica es de lunes a sábado de 07:00 a 19:00 horas");
        }
    }
}
