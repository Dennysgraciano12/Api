package med.voll.Api.domain.paciente;

import med.voll.Api.domain.direccion.DatosDireccion;

public record DatosRespuestaPaciente(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documentoIdentidad,
        DatosDireccion direccion) {
}
