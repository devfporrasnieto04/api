package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(
      //  @NotNull  internamente @NotBlank realiza la misma tarea de notnull y a la vez verifica que no este vacio
      //  o "blank", por eso se deja solo notblank
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefono,
        @NotBlank
        @Pattern(regexp = "\\d{4,10}")
        String documento,
        @NotNull
        Especialidad especialidad,
        @NotNull  // porque es un objeto, entonces no va a llegar vacio porque se esta instanciando de la clase ppal. entonces ya existe.
        @Valid
        DatosDireccion direccion) {

}
