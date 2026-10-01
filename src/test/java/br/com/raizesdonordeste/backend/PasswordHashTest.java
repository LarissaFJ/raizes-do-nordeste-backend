package br.com.raizesdonordeste.backend;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordHashTest {

    @Test
    void imprimirHashesBcrypt() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senhaAdmin = "AdminTeste@2026";
        String senhaAtendente = "AtendenteTeste@2026";

        System.out.println("ADMIN: " + encoder.encode(senhaAdmin));
        System.out.println("ATENDENTE: " + encoder.encode(senhaAtendente));
    }
}