package med.voll.Api.domain.medico;

import med.voll.Api.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        DatosDireccion direccion) {
}
