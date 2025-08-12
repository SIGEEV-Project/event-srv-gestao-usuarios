package com.sigeev.usuarios.adapters.inbound.rest.api;

import com.sigeev.usuarios.adapters.inbound.rest.dto.CadastroUsuarioDTO;
import com.sigeev.usuarios.adapters.inbound.rest.dto.RespostaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "Usuários", description = "API para gerenciamento de usuários do sistema")
public interface UsuarioApi {

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
            description = "CPF ou e-mail já cadastrados",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RespostaDTO.class)
            )
        )
    })
    ResponseEntity<RespostaDTO> cadastrarUsuario(
        @RequestBody CadastroUsuarioDTO cadastroUsuarioDTO
    );

    @Operation(
        summary = "Buscar usuário por ID",
        description = "Endpoint para buscar os dados de um usuário específico pelo seu ID"
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
    ResponseEntity<RespostaDTO> buscarUsuarioPorId(
        @Parameter(description = "ID do usuário a ser buscado", required = true)
        @PathVariable UUID id
    );

    @Operation(
        summary = "Excluir usuário",
        description = "Endpoint para excluir um usuário do sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuário excluído com sucesso",
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
    ResponseEntity<RespostaDTO> excluirUsuario(
        @Parameter(description = "ID do usuário a ser excluído", required = true)
        @PathVariable UUID id
    );
}
