package med.voll.Api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.Api.domain.direccion.DatosDireccion;

public record DatosActualizarPaciente(
        @NotNull
        Long id,
        String nombre,
        String documentoIdentidad,
        DatosDireccion direccion) {
}
