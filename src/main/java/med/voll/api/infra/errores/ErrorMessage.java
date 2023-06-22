package med.voll.api.infra.errores;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
@Getter
@AllArgsConstructor
public class ErrorMessage {
	private int statusCode;
	private Date timesTamp;
	private String message;
	private String description;
}
