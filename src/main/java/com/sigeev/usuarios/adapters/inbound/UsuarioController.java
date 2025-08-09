package com.sigeev.usuarios.adapters.inbound;

import com.sigeev.usuarios.adapters.inbound.dtos.*;
import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.UsuarioServicePort;
import com.sigeev.usuarios.infrastructure.mappers.UsuarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "API para gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioServicePort usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping("/cadastro")
    @Operation(summary = "Cadastrar novo usuário")
    public ResponseEntity<ApiResponse> cadastrarUsuario(@RequestBody @Valid CadastroUsuarioRequest request) {
        Usuario usuario = usuarioMapper.toEntity(request);
        Usuario usuarioCriado = usuarioService.cadastrarUsuario(usuario, request.getSenha());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .sucesso(true)
                        .mensagem("Usuário " + usuarioCriado.getPrimeiroNome() + " cadastrado com sucesso!")
                        .dados(usuarioCriado)
                        .build());
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest request) {
        String token = usuarioService.autenticarUsuario(request.getEmail(), request.getSenha());

        return ResponseEntity.ok(ApiResponse.builder()
                .sucesso(true)
                .mensagem("Login realizado com sucesso!")
                .dados(new TokenResponse(token))
                .build());
    }

    @PutMapping("/{usuarioId}")
    @Operation(summary = "Atualizar perfil do usuário")
    @PreAuthorize("#usuarioId == authentication.principal.usuarioId")
    public ResponseEntity<ApiResponse> atualizarPerfil(
            @PathVariable UUID usuarioId,
            @RequestBody @Valid AtualizarPerfilRequest request) {

        Usuario usuario = usuarioMapper.toEntity(request);
        Usuario usuarioAtualizado = usuarioService.atualizarPerfil(usuarioId, usuario);

        return ResponseEntity.ok(ApiResponse.builder()
                .sucesso(true)
                .mensagem("Perfil atualizado com sucesso!")
                .dados(usuarioAtualizado)
                .build());
    }

    @PutMapping("/{usuarioId}/promover")
    @Operation(summary = "Promover usuário")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse> promoverUsuario(
            @PathVariable UUID usuarioId,
            @RequestBody @Valid PromoverUsuarioRequest request) {

        usuarioService.promoverUsuario(usuarioId, request.getAdminId(), request.getNovoPerfil());

        return ResponseEntity.ok(ApiResponse.builder()
                .sucesso(true)
                .mensagem("Usuário promovido com sucesso!")
                .build());
    }

    @PutMapping("/{usuarioId}/rebaixar")
    @Operation(summary = "Rebaixar usuário")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse> rebaixarUsuario(
            @PathVariable UUID usuarioId,
            @RequestBody @Valid RebaixarUsuarioRequest request) {

        usuarioService.rebaixarUsuario(usuarioId, request.getAdminId());

        return ResponseEntity.ok(ApiResponse.builder()
                .sucesso(true)
                .mensagem("Usuário rebaixado com sucesso!")
                .build());
    }

    @DeleteMapping("/{usuarioId}")
    @Operation(summary = "Excluir conta")
    @PreAuthorize("#usuarioId == authentication.principal.usuarioId")
    public ResponseEntity<ApiResponse> excluirConta(@PathVariable UUID usuarioId) {
        usuarioService.excluirConta(usuarioId);

        return ResponseEntity.ok(ApiResponse.builder()
                .sucesso(true)
                .mensagem("Conta excluída com sucesso!")
                .build());
    }

    @PostMapping("/senha/trocar")
    @Operation(summary = "Trocar senha")
    public ResponseEntity<ApiResponse> trocarSenha(
            @RequestBody @Valid TrocarSenhaRequest request) {

        usuarioService.trocarSenha(request.getUsuarioId(), request.getSenhaAtual(), request.getNovaSenha());

        return ResponseEntity.ok(ApiResponse.builder()
                .sucesso(true)
                .mensagem("Senha alterada com sucesso!")
                .build());
    }

    @PostMapping("/senha/recuperar")
    @Operation(summary = "Solicitar recuperação de senha")
    public ResponseEntity<ApiResponse> solicitarRecuperacaoSenha(
            @RequestBody @Valid RecuperarSenhaRequest request) {

        usuarioService.solicitarRecuperacaoSenha(request.getEmail());

        return ResponseEntity.ok(ApiResponse.builder()
                .sucesso(true)
                .mensagem("Email de recuperação enviado com sucesso!")
                .build());
    }

    @PostMapping("/token/renovar")
    @Operation(summary = "Renovar token")
    public ResponseEntity<ApiResponse> renovarToken(
            @RequestBody @Valid RenovarTokenRequest request) {

        String novoToken = usuarioService.renovarToken(request.getRefreshToken());

        return ResponseEntity.ok(ApiResponse.builder()
                .sucesso(true)
                .mensagem("Token renovado com sucesso!")
                .dados(new TokenResponse(novoToken))
                .build());
    }
}
