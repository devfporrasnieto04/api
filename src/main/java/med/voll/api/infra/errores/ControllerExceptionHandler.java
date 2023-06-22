package med.voll.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorMessage> tratamientoError404(EntityNotFoundException exception, WebRequest request){
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				exception.getMessage().substring(0, 15).concat(exception.getMessage().substring(42,60)),
				request.getDescription(false)
		);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<List<DatosErrorValidacion>> tratamientoError400(MethodArgumentNotValidException exception){
		var errores = exception.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
		return ResponseEntity.badRequest().body(errores);
	}
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorMessage> tratamientoError500(Exception exception, WebRequest request){
		ErrorMessage message = new ErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				new Date(),
				exception.getMessage(),
				request.getDescription(false)
		);
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorMessage> notFoundExceptionHandler( Exception exception, WebRequest request){
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				exception.getMessage(),
				request.getDescription(false)
		);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
}
