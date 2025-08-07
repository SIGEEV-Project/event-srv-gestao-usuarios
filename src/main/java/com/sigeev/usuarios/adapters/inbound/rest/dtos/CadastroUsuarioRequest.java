package com.sigeev.usuarios.adapters.inbound.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CadastroUsuarioRequest {
    @Valid
    @JsonProperty("usuario")
    private DadosUsuario usuario;

    @Valid
    @JsonProperty("endereco")
    private Endereco endereco;

    @Data
    public static class DadosUsuario {
        @NotBlank
        @Size(min = 2, max = 50)
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]*$")
        private String primeiroNome;

        @NotBlank
        @Size(min = 2, max = 100)
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]*$")
        private String ultimoNome;

        @Valid
        private Documento documento;

        @Valid
        private Credenciais credenciais;

        @Valid
        private Contato contato;

        @NotBlank
        private String dataNascimento;
    }

    @Data
    public static class Documento {
        @NotBlank
        private String tipo;

        @NotBlank
        @Pattern(regexp = "^\\d{11}$")
        private String numero;
    }

    @Data
    public static class Credenciais {
        @NotBlank
        @Email
        @Size(min = 5, max = 100)
        private String email;

        @NotBlank
        @Size(min = 8)
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
        private String senha;
    }

    @Data
    public static class Contato {
        @Pattern(regexp = "^\\d{10,11}$")
        private String telefone;

        @Email
        private String emailContato;
    }

    @Data
    public static class Endereco {
        @NotBlank
        @Size(max = 100)
        private String logradouro;

        @NotBlank
        @Size(max = 10)
        private String numero;

        @NotBlank
        @Size(max = 50)
        private String cidade;

        @NotBlank
        @Size(max = 2)
        @Pattern(regexp = "^[A-Z]{2}$")
        private String estado;

        @NotBlank
        @Pattern(regexp = "^\\d{5}-\\d{3}$")
        private String cep;
    }
}
