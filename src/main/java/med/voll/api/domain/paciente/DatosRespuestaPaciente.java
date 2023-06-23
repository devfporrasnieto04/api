package med.voll.api.domain.paciente;

public record DatosRespuestaPaciente(
		String nombre,
        String telefono,
		String documento,
		Boolean activo
		) {
}
