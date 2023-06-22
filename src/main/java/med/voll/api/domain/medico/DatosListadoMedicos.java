package med.voll.api.domain.medico;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DatosListadoMedicos(
		Long id,
		@NotBlank
		String nombre,
		@NotNull
		Especialidad especialidad,
		@NotBlank
		@Pattern(regexp = "\\d{4,10}")
		String documento,
		@NotBlank
		@Email
		String email
) {
	
	public DatosListadoMedicos (Medico medico){
		this(medico.getId(), medico.getNombre(), medico.getEspecialidad(), medico.getDocumento(), medico.getEmail());
		
	}
}
