package br.com.raizesdonordeste.backend.dto.response;

import br.com.raizesdonordeste.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private User user;

    @Getter
    @Setter
    public static class User {

        private Long id;
        private String nome;
        private Role perfil;
    }
}
