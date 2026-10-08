package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import br.com.raizesdonordeste.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail deve ser válido")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String email;
    @NotBlank(message = "Campo obrigatório")
    private String senha;
    @NotNull(message = "Campo obrigatório")
    private Role role;

}
