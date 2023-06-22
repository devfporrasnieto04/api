package med.voll.api.infra.errores;

public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundException(String message) {
	super(message);
	}
}
