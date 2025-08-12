package com.sigeev.usuarios.adapters.inbound.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "DTO para cadastro de novo usuário")
public class CadastroUsuarioDTO {
    @Valid
    @Schema(description = "Dados pessoais do usuário", required = true)
    private UsuarioDTO usuario;
    
    @Valid
    @Schema(description = "Dados de endereço do usuário", required = true)
    private EnderecoDTO endereco;

    @Data
    public static class UsuarioDTO {
        @NotBlank(message = "O primeiro nome é obrigatório")
        @Size(min = 2, max = 50, message = "O primeiro nome deve ter entre 2 e 50 caracteres")
        private String primeiroNome;

        @NotBlank(message = "O último nome é obrigatório")
        @Size(min = 2, max = 100, message = "O último nome deve ter entre 2 e 100 caracteres")
        private String ultimoNome;

        @Valid
        private DocumentoDTO documento;

        @Valid
        private CredenciaisDTO credenciais;

        @Valid
        private ContatoDTO contato;

        @Past(message = "A data de nascimento deve ser no passado")
        private LocalDate dataNascimento;
    }

    @Data
    public static class DocumentoDTO {
        @NotBlank(message = "O tipo de documento é obrigatório")
        private String tipo;

        @NotBlank(message = "O número do documento é obrigatório")
        private String numero;
    }

    @Data
    public static class CredenciaisDTO {
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Email inválido")
        @Size(min = 5, max = 100, message = "O email deve ter entre 5 e 100 caracteres")
        private String email;

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        private String senha;
    }

    @Data
    public static class ContatoDTO {
        private String telefone;
        private String emailContato;
    }

    @Data
    public static class EnderecoDTO {
        private String logradouro;
        private String numero;
        private String cidade;
        private String estado;
        private String cep;
    }
}
