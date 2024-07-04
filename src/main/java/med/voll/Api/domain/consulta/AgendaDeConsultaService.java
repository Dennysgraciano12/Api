package med.voll.Api.domain.consulta;

import med.voll.Api.domain.medico.Medico;
import med.voll.Api.domain.medico.MedicoRepository;
import med.voll.Api.domain.paciente.PacienteRepository;
import med.voll.Api.infra.errores.ValidacionIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public void agendar(DatosAgendarConsulta datosAgendarConsulta){
        if(pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent()){
            throw new ValidacionIntegridad("El id del paciente no fue encontrado");
        }

        if(datosAgendarConsulta.idMedico() !=null && medicoRepository.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidacionIntegridad("El id del medico no fue encontrado");
        }
        var paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();
        var medico = seleccionarMedico(datosAgendarConsulta);
        var consulta = new Consulta(null, medico,  paciente, datosAgendarConsulta.fecha());
        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if(datosAgendarConsulta.idMedico() !=null){
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if(datosAgendarConsulta.especialidad() !=null){
            throw new ValidacionIntegridad("Debe seleccionarse uan especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendarConsulta.especialidad(),datosAgendarConsulta.fecha());
    }
}
