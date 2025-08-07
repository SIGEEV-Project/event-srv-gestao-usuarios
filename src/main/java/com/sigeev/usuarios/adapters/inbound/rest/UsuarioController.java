package com.sigeev.usuarios.adapters.inbound.rest;

import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.input.AuthenticationUseCase;
import com.sigeev.usuarios.domain.ports.input.CadastroUsuarioUseCase;
import com.sigeev.usuarios.adapters.inbound.rest.dtos.CadastroUsuarioRequest;
import com.sigeev.usuarios.adapters.inbound.rest.dtos.CadastroUsuarioResponse;
import com.sigeev.usuarios.adapters.inbound.rest.dtos.LoginRequest;
import com.sigeev.usuarios.adapters.inbound.rest.dtos.LoginResponse;
import com.sigeev.usuarios.infrastructure.mappers.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final CadastroUsuarioUseCase cadastroUsuarioUseCase;
    private final AuthenticationUseCase authenticationUseCase;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    public ResponseEntity<CadastroUsuarioResponse> cadastrarUsuario(
            @Valid @RequestBody CadastroUsuarioRequest request) {
        
        Usuario usuario = usuarioMapper.toDomain(request);
        Usuario usuarioCadastrado = cadastroUsuarioUseCase.cadastrarNovoUsuario(usuario);

        CadastroUsuarioResponse response = CadastroUsuarioResponse.builder()
                .sucesso(true)
                .mensagem("Usuário " + usuarioCadastrado.getPrimeiroNome() + " cadastrado com sucesso!")
                .dados(CadastroUsuarioResponse.DadosUsuario.builder()
                        .usuarioId(usuarioCadastrado.getUsuarioId())
                        .nomeCompleto(usuarioCadastrado.getPrimeiroNome() + " " + usuarioCadastrado.getUltimoNome())
                        .email(usuarioCadastrado.getEmail())
                        .tokenAcesso(authenticationUseCase.authenticate(usuarioCadastrado.getEmail(), request.getUsuario().getCredenciais().getSenha()))
                        .build())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            String token = authenticationUseCase.authenticate(request.getEmail(), request.getSenha());
            return ResponseEntity.ok(LoginResponse.builder()
                    .sucesso(true)
                    .mensagem("Login realizado com sucesso!")
                    .tokenAcesso(token)
                    .build());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.builder()
                            .sucesso(false)
                            .mensagem("Email ou senha inválidos")
                            .build());
        }
    }
}
