package com.sigeev.usuarios.adapters.inbound.rest;

import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.input.ConsultaUsuarioUseCase;
import com.sigeev.usuarios.adapters.inbound.rest.dtos.CadastroUsuarioResponse;
import com.sigeev.usuarios.adapters.inbound.rest.dtos.ListagemUsuariosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class ConsultaUsuarioController {

    private final ConsultaUsuarioUseCase consultaUsuarioUseCase;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<CadastroUsuarioResponse> buscarPorId(@PathVariable UUID id) {
        Usuario usuario = consultaUsuarioUseCase.buscarPorId(id);
        
        return ResponseEntity.ok(CadastroUsuarioResponse.builder()
                .sucesso(true)
                .mensagem("Usuário encontrado com sucesso")
                .dados(CadastroUsuarioResponse.DadosUsuario.builder()
                        .usuarioId(usuario.getUsuarioId())
                        .nomeCompleto(usuario.getPrimeiroNome() + " " + usuario.getUltimoNome())
                        .email(usuario.getEmail())
                        .build())
                .build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListagemUsuariosResponse> listarUsuarios(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho,
            @RequestParam(required = false) String filtro) {
        
        PageRequest paginacao = PageRequest.of(pagina, tamanho, Sort.by("dataCadastro").descending());
        Page<Usuario> resultado = consultaUsuarioUseCase.listarUsuarios(paginacao, filtro != null ? filtro : "");
        
        return ResponseEntity.ok(ListagemUsuariosResponse.fromPage(resultado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirUsuario(@PathVariable UUID id) {
        consultaUsuarioUseCase.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> reativarUsuario(@PathVariable UUID id) {
        consultaUsuarioUseCase.reativarUsuario(id);
        return ResponseEntity.ok().build();
    }
}
