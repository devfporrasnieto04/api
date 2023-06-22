package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Paciente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String email;
	private String telefono;
	private String documento;
	private Boolean activo;
	@Embedded
	private Direccion direccion;
	
	public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
		this.activo =true;
		this.nombre = datosRegistroPaciente.nombre();
		this.email = datosRegistroPaciente.email();
		this.telefono = datosRegistroPaciente.telefono();
		this.documento = datosRegistroPaciente.documento();
		this.direccion = new Direccion(datosRegistroPaciente.direccion());
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Paciente paciente = (Paciente) o;
		return getId() != null && Objects.equals(getId(), paciente.getId());
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	public void desactivarPaciente() {
		this.activo=false;
	}
	
	public void actualizarDatos(DatosActualizarPaciente datosActualizarPaciente) {
		
		if(datosActualizarPaciente.nombre() != null){
			this.nombre = datosActualizarPaciente.nombre();
		}
		if(datosActualizarPaciente.documento() != null){
			this.documento = datosActualizarPaciente.documento();
		}
		if(datosActualizarPaciente.direccion() != null){
			this.direccion = direccion.actualizarDatos(datosActualizarPaciente.direccion());
			
		}
	}
	
}
