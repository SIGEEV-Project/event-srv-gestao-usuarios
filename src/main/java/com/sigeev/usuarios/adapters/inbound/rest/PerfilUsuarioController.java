package com.sigeev.usuarios.adapters.inbound.rest;

import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.input.AtualizacaoUsuarioUseCase;
import com.sigeev.usuarios.adapters.inbound.rest.dtos.AtualizacaoSenhaRequest;
import com.sigeev.usuarios.adapters.inbound.rest.dtos.CadastroUsuarioResponse;
import com.sigeev.usuarios.infrastructure.mappers.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios/perfil")
@RequiredArgsConstructor
public class PerfilUsuarioController {

    private final AtualizacaoUsuarioUseCase atualizacaoUsuarioUseCase;
    private final UsuarioMapper usuarioMapper;

    @PutMapping
    public ResponseEntity<CadastroUsuarioResponse> atualizarPerfil(
            @Valid @RequestBody Usuario dadosAtualizacao,
            Authentication authentication) {
        UUID usuarioId = UUID.fromString(authentication.getName());
        Usuario usuarioAtualizado = atualizacaoUsuarioUseCase.atualizarDados(usuarioId, dadosAtualizacao);

        CadastroUsuarioResponse response = CadastroUsuarioResponse.builder()
                .sucesso(true)
                .mensagem("Dados atualizados com sucesso!")
                .dados(CadastroUsuarioResponse.DadosUsuario.builder()
                        .usuarioId(usuarioAtualizado.getUsuarioId())
                        .nomeCompleto(usuarioAtualizado.getPrimeiroNome() + " " + usuarioAtualizado.getUltimoNome())
                        .email(usuarioAtualizado.getEmail())
                        .build())
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/senha")
    public ResponseEntity<Void> atualizarSenha(
            @Valid @RequestBody AtualizacaoSenhaRequest request,
            Authentication authentication) {
        UUID usuarioId = UUID.fromString(authentication.getName());
        atualizacaoUsuarioUseCase.atualizarSenha(usuarioId, request.getSenhaAtual(), request.getNovaSenha());
        return ResponseEntity.ok().build();
    }
}
