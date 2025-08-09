package com.sigeev.usuarios.adapters.inbound.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EnderecoDTO {
    @NotBlank(message = "O logradouro é obrigatório")
    @Size(min = 3, max = 100, message = "O logradouro deve ter entre 3 e 100 caracteres")
    private String logradouro;

    @NotBlank(message = "O número é obrigatório")
    @Pattern(regexp = "^[0-9]+$", message = "O número deve conter apenas dígitos")
    private String numero;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(min = 2, max = 50, message = "A cidade deve ter entre 2 e 50 caracteres")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$", message = "A cidade deve conter apenas letras")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    @Pattern(regexp = "^[A-Z]{2}$", message = "O estado deve ser uma sigla de dois caracteres maiúsculos")
    private String estado;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP inválido")
    private String cep;
}
