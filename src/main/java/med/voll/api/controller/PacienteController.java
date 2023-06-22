package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import med.voll.api.domain.paciente.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteRepository pacienteRepository;
    
    @PostMapping
    public void registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente){
        pacienteRepository.save(new Paciente(datosRegistroPaciente));
    }
    @GetMapping
    public Page<DatosListadoPacientes> listarPacientes(@PageableDefault(size = 2, sort = "nombre")Pageable pagination){
        return pacienteRepository.findByActivoTrue(pagination).map(DatosListadoPacientes::new);
    }
    @PutMapping
    @Transactional
    public void actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
    }
}
