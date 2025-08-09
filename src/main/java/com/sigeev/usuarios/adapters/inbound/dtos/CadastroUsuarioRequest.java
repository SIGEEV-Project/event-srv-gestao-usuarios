package com.sigeev.usuarios.adapters.inbound.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CadastroUsuarioRequest {
    @NotBlank(message = "O primeiro nome é obrigatório")
    @Size(min = 2, max = 50, message = "O primeiro nome deve ter entre 2 e 50 caracteres")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$", message = "O primeiro nome deve conter apenas letras")
    private String primeiroNome;

    @NotBlank(message = "O último nome é obrigatório")
    @Size(min = 2, max = 100, message = "O último nome deve ter entre 2 e 100 caracteres")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$", message = "O último nome deve conter apenas letras")
    private String ultimoNome;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
            message = "A senha deve ter pelo menos 8 caracteres, incluindo maiúsculas, minúsculas, números e caracteres especiais")
    private String senha;

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone inválido")
    private String telefone;

    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve ser no passado")
    private String dataNascimento;

    @Valid
    private EnderecoDTO endereco;
}
