package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCadastroRequest {
    @NotBlank(message = "Nome não pode ser vazio")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String nome;
    @NotBlank(message = "Campo obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cpf;
    @NotBlank(message = "Campo obrigatório")
    @Email(message = "E-mail deve ser válido")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String email;
    @NotBlank(message = "Campo obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
    private String telefone;
    @NotBlank(message = "Campo obrigatório")
    private String senha;
    @AssertTrue(message = "O consentimento para tratamento de dados é obrigatório")
    private boolean consentimentoLgpd;
}
