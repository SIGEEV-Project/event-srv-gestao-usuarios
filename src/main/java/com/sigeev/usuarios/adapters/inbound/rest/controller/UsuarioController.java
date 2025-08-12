package com.sigeev.usuarios.adapters.inbound.rest.controller;

import com.sigeev.usuarios.adapters.inbound.rest.dto.CadastroUsuarioDTO;
import com.sigeev.usuarios.adapters.inbound.rest.dto.RespostaDTO;
import com.sigeev.usuarios.domain.entities.Endereco;
import com.sigeev.usuarios.domain.entities.PerfilUsuario;
import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.inbound.UsuarioServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "API para gerenciamento de usuários do sistema")
public class UsuarioController {

    private final UsuarioServicePort usuarioService;

    @Operation(
        summary = "Cadastrar novo usuário",
        description = "Endpoint para cadastrar um novo usuário no sistema com seus dados pessoais e endereço"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Usuário cadastrado com sucesso",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RespostaDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RespostaDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "CPF ou email já cadastrados",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RespostaDTO.class)
            )
        )
    })
    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RespostaDTO> cadastrarUsuario(
        @Parameter(description = "Dados do usuário a ser cadastrado", required = true)
        @Valid @RequestBody CadastroUsuarioDTO cadastroDTO
    ) {
        try {
            var usuario = mapearParaUsuario(cadastroDTO);
            var usuarioCriado = usuarioService.cadastrarUsuario(usuario);

            var dadosResposta = RespostaDTO.UsuarioCriadoDTO.builder()
                    .usuarioId(usuarioCriado.getUsuarioId())
                    .nomeCompleto(usuarioCriado.getPrimeiroNome() + " " + usuarioCriado.getUltimoNome())
                    .email(usuarioCriado.getEmail())
                    .tokenAcesso("token-temporario") // TODO: Implementar geração de token JWT
                    .build();

            var resposta = RespostaDTO.builder()
                    .sucesso(true)
                    .mensagem("Usuário " + usuarioCriado.getPrimeiroNome() + " cadastrado com sucesso!")
                    .dados(dadosResposta)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(resposta);

        } catch (IllegalArgumentException e) {
            var erro = RespostaDTO.ErroDTO.builder()
                    .campo(e.getMessage().contains("CPF") ? "cpf" : "email")
                    .mensagem(e.getMessage())
                    .build();

            var resposta = RespostaDTO.builder()
                    .sucesso(false)
                    .mensagem("Erro ao cadastrar usuário.")
                    .erros(Collections.singletonList(erro))
                    .build();

            return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
        }
    }

    @Operation(
        summary = "Buscar usuário por ID",
        description = "Endpoint para buscar um usuário específico pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuário encontrado com sucesso",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RespostaDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RespostaDTO.class)
            )
        )
    })
    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RespostaDTO> buscarUsuario(
        @Parameter(description = "ID do usuário a ser buscado", required = true)
        @PathVariable UUID id
    ) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> {
                    var resposta = RespostaDTO.builder()
                            .sucesso(true)
                            .mensagem("Usuário encontrado com sucesso!")
                            .dados(usuario)
                            .build();
                    return ResponseEntity.ok(resposta);
                })
                .orElseGet(() -> {
                    var resposta = RespostaDTO.builder()
                            .sucesso(false)
                            .mensagem("Usuário não encontrado.")
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
                });
    }

    private Usuario mapearParaUsuario(CadastroUsuarioDTO dto) {
        return Usuario.builder()
                .primeiroNome(dto.getUsuario().getPrimeiroNome())
                .ultimoNome(dto.getUsuario().getUltimoNome())
                .email(dto.getUsuario().getCredenciais().getEmail())
                .cpf(dto.getUsuario().getDocumento().getNumero())
                .telefone(dto.getUsuario().getContato().getTelefone())
                .dataNascimento(dto.getUsuario().getDataNascimento())
                .endereco(Endereco.builder()
                        .logradouro(dto.getEndereco().getLogradouro())
                        .numero(dto.getEndereco().getNumero())
                        .cidade(dto.getEndereco().getCidade())
                        .estado(dto.getEndereco().getEstado())
                        .cep(dto.getEndereco().getCep())
                        .build())
                .perfil(PerfilUsuario.PARTICIPANTE)
                .build();
    }
}
