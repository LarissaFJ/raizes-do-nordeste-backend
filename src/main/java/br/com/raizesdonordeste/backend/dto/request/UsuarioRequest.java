package br.com.raizesdonordeste.backend.dto.request;

import br.com.raizesdonordeste.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {

    private String email;
    private String senha;
    private Role role;

}
