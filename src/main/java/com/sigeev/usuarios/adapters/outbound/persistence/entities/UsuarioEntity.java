package com.sigeev.usuarios.adapters.outbound.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Usuarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID usuarioId;

    @Column(nullable = false, length = 50)
    private String primeiroNome;

    @Column(nullable = false, length = 100)
    private String ultimoNome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(length = 15)
    private String telefone;

    @Column
    private LocalDate dataNascimento;

    @Column(length = 100)
    private String logradouro;

    @Column(length = 10)
    private String numeroEndereco;

    @Column(length = 50)
    private String cidade;

    @Column(length = 2)
    private String estado;

    @Column(length = 9)
    private String cep;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private String perfil;

    @Column(nullable = false, length = 20)
    private String status;

    @Column
    private LocalDateTime dataCadastro;

    @Column
    private LocalDateTime dataUltimoLogin;

    @Column
    private LocalDateTime dataAlteracao;

    @Column
    private LocalDateTime dataExclusao;

    @Column
    private LocalDateTime dataReativacao;

    @Column
    private String tokenRecuperacao;

    @Column
    private LocalDateTime tokenRecuperacaoExpiraEm;
}
