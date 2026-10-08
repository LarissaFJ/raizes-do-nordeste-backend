package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCadastroRequest {
    @NotBlank(message = "Nome não pode ser vazio")
    @Size(max = 255)
    private String nome;
    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cpf;
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
    @NotBlank
    @Pattern(regexp = "\\d{10,11}")
    private String telefone;
    @NotBlank
    private String senha;
    @AssertTrue(message = "O consentimento para tratamento de dados é obrigatório")
    private boolean consentimentoLgpd;
}
