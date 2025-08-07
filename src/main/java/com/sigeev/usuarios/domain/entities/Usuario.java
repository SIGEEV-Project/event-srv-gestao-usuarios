package com.sigeev.usuarios.domain.entities;

import com.sigeev.usuarios.domain.entities.enums.PerfilUsuario;
import com.sigeev.usuarios.domain.entities.enums.StatusUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private UUID usuarioId;
    private String primeiroNome;
    private String ultimoNome;
    private String email;
    private String cpf;
    private String telefone;
    private LocalDate dataNascimento;
    private Endereco endereco;
    private Contato contato;
    private PerfilUsuario perfil;
    private StatusUsuario status;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataUltimoLogin;
    private LocalDateTime dataAlteracao;
    private LocalDateTime dataExclusao;
    private LocalDateTime dataReativacao;
    private String tokenRecuperacao;
    private LocalDateTime tokenRecuperacaoExpiraEm;
}
