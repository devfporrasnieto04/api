package med.voll.api.domain.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosListadoPacientes(
		Long id,
		@NotBlank
		String nombre,
		String telefono,
		@NotBlank
		@Pattern(regexp = "\\d{4,10}")
		String documento,
		@NotBlank
		@Email
		String email
) {
	
	public DatosListadoPacientes (Paciente paciente){
		this(paciente.getId(), paciente.getNombre(), paciente.getTelefono(), paciente.getDocumento(), paciente.getEmail());
	}
}
