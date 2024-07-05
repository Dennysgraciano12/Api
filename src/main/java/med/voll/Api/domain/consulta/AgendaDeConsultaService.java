package med.voll.Api.domain.consulta;

import med.voll.Api.domain.consulta.validaciones.*;
import med.voll.Api.domain.medico.Medico;
import med.voll.Api.domain.medico.MedicoRepository;
import med.voll.Api.domain.paciente.PacienteRepository;
import med.voll.Api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    public DatosDetalladoConsulta agendar(DatosAgendarConsulta datosAgendarConsulta){
        if(!pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("El id del paciente no fue encontrado");
        }

        if(datosAgendarConsulta.idMedico() !=null && !medicoRepository.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidacionDeIntegridad("El id del medico no fue encontrado");
        }

        validadores.forEach(v ->v.validar(datosAgendarConsulta));

        var paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();

        var medico = seleccionarMedico(datosAgendarConsulta);
        if(medico == null){
            throw new ValidacionDeIntegridad("No existen medicos disponibles para este horario y especialidad");
        }

        var consulta = new Consulta(null, medico,  paciente, datosAgendarConsulta.fecha());
        consultaRepository.save(consulta);

        return new DatosDetalladoConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if(datosAgendarConsulta.idMedico() !=null){
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if(datosAgendarConsulta.especialidad() !=null){
            throw new ValidacionDeIntegridad("Debe seleccionarse uan especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendarConsulta.especialidad(),datosAgendarConsulta.fecha());
    }
}
