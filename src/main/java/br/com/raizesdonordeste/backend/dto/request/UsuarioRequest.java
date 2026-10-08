package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import br.com.raizesdonordeste.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {

    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
    @NotBlank
    private String senha;
    @NotNull
    private Role role;

}
