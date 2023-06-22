package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;
import org.hibernate.Hibernate;
import java.util.Objects;

@Entity(name = "Medico")
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.activo = true;
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.telefono = datosRegistroMedico.telefono();
        this.documento = datosRegistroMedico.documento();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Medico medico = (Medico) o;
        return getId() != null && Objects.equals(getId(), medico.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {
       
       if(datosActualizarMedico.nombre() != null){
        this.nombre = datosActualizarMedico.nombre();
       }
       if(datosActualizarMedico.documento() != null){
        this.documento = datosActualizarMedico.documento();
       }
       if(datosActualizarMedico.direccion() != null){
        this.direccion = direccion.actualizarDatos(datosActualizarMedico.direccion());
        
       }
    }
    
    public void desactivarMedico() {
       this.activo=false;
    }
    
    public void activarMedico() {
        this.activo=true;
    }
}
