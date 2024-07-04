package med.voll.Api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.Api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(
        Long id,
        @NotNull
        Long idPaciente,
        Long idMedico,
        @NotNull
        @Future
        LocalDateTime fecha,
        Especialidad especialidad
) {
}
