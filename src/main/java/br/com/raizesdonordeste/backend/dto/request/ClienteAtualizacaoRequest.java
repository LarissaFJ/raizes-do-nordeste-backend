package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteAtualizacaoRequest {

    @Pattern(regexp = "(?s).*\\S.*", message = "O formato do campo é inválido")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String nome;
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos")
    private String cpf;
    @Pattern(regexp = "(?s).*\\S.*", message = "O formato do campo é inválido")
    @Email(message = "E-mail deve ser válido")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String email;
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
    private String telefone;
}
