package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("app/v1/medicos")
@RequiredArgsConstructor
public class MedicoController {
    private final MedicoRepository medicoRepository;
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                          UriComponentsBuilder uriComponentsBuilder){
        
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        //retornar 201 created, URL donde se encuentra el medico
       DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
               medico.getTelefono(), medico.getEmail(), medico.getEspecialidad().toString(),
               new DatosDireccion(
                       medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                       medico.getDireccion().getCiudad(), medico.getDireccion().getComplemento(),
                       medico.getDireccion().getNumero()));
        
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
        
    }
//    @GetMapping
//    public List<DatosListadoMedicos> listarMedicos(){
//        return medicoRepository.findAll().stream().map(DatosListadoMedicos::new).toList();
//    }
    
    // a esto  se le llama paginacion y es un recurso que se utiliza desde el frontend o cliente, va a listar dentro de un content
    // LISTA TODOS TANTO ACTIVOS COMO INACTIVOS
//    @GetMapping
//    public Page<DatosListadoMedicos> listarMedicos(@PageableDefault(size = 2, sort = "nombre") Pageable pagination){
//        return medicoRepository.findAll(pagination).map(DatosListadoMedicos::new);
//    }
    // solo lista los activos mediante un metodo creado en  MedicoRepository
    @GetMapping
    public ResponseEntity<Page<DatosListadoMedicos>>  listarMedicos(@PageableDefault(size = 2, sort = "nombre") Pageable pagination){
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(pagination).map(DatosListadoMedicos::new));
    }
    
    @PutMapping("/{id}")
    @Transactional // para que termine la transaccion y haga el commit de los datos nuevos
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody
                                                                @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        
        return  ResponseEntity.ok(new DatosRespuestaMedico(
                medico.getId(), medico.getNombre(), medico.getTelefono(), medico.getEmail(), medico.getEspecialidad().toString(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getComplemento(), medico.getDireccion().getNumero()
                )
        ));
    }
    
    //DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Medico> eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/reactive/{id}")
    @Transactional
    public ResponseEntity<Medico> reactivarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.activarMedico();
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getTelefono(), medico.getEmail(), medico.getEspecialidad().toString(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getComplemento(),
                        medico.getDireccion().getNumero()
                ));
        return ResponseEntity.ok(datosMedico);
    }
    
    
    // metodo delete para  eliminar de la base de datos
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void eliminarMedico(@PathVariable Long id){
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }
}

