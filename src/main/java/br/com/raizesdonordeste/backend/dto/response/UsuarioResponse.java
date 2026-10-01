package br.com.raizesdonordeste.backend.dto.response;

import br.com.raizesdonordeste.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponse {

    private Long id;
    private String email;
    private Role role;
}