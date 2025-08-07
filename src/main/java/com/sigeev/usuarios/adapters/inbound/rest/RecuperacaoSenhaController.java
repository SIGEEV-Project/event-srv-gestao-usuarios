package com.sigeev.usuarios.adapters.inbound.rest;

import com.sigeev.usuarios.domain.ports.input.RecuperacaoSenhaUseCase;
import com.sigeev.usuarios.adapters.inbound.rest.dtos.RecuperacaoSenhaRequest;
import com.sigeev.usuarios.adapters.inbound.rest.dtos.RedefinicaoSenhaRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios/senha")
@RequiredArgsConstructor
public class RecuperacaoSenhaController {

    private final RecuperacaoSenhaUseCase recuperacaoSenhaUseCase;

    @PostMapping("/recuperar")
    public ResponseEntity<Void> solicitarRecuperacao(
            @Valid @RequestBody RecuperacaoSenhaRequest request) {
        recuperacaoSenhaUseCase.solicitarRecuperacaoSenha(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validar/{token}")
    public ResponseEntity<Void> validarToken(@PathVariable String token) {
        recuperacaoSenhaUseCase.validarTokenRecuperacao(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/redefinir")
    public ResponseEntity<Void> redefinirSenha(
            @Valid @RequestBody RedefinicaoSenhaRequest request) {
        recuperacaoSenhaUseCase.redefinirSenha(request.getToken(), request.getNovaSenha());
        return ResponseEntity.ok().build();
    }
}
