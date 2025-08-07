package com.sigeev.usuarios.adapters.inbound.rest.dtos;

import com.sigeev.usuarios.domain.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListagemUsuariosResponse {
    private boolean sucesso;
    private String mensagem;
    private DadosPaginacao paginacao;
    private List<DadosListagemUsuario> usuarios;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DadosPaginacao {
        private int pagina;
        private int tamanhoPagina;
        private long totalElementos;
        private int totalPaginas;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DadosListagemUsuario {
        private UUID usuarioId;
        private String nomeCompleto;
        private String email;
        private String perfil;
        private String status;
        private LocalDateTime dataCadastro;
    }

    public static ListagemUsuariosResponse fromPage(Page<Usuario> page) {
        List<DadosListagemUsuario> usuarios = page.getContent().stream()
                .map(usuario -> DadosListagemUsuario.builder()
                        .usuarioId(usuario.getUsuarioId())
                        .nomeCompleto(usuario.getPrimeiroNome() + " " + usuario.getUltimoNome())
                        .email(usuario.getEmail())
                        .perfil(usuario.getPerfil().toString())
                        .status(usuario.getStatus().toString())
                        .dataCadastro(usuario.getDataCadastro())
                        .build())
                .collect(Collectors.toList());

        return ListagemUsuariosResponse.builder()
                .sucesso(true)
                .mensagem("Usuários listados com sucesso")
                .paginacao(DadosPaginacao.builder()
                        .pagina(page.getNumber())
                        .tamanhoPagina(page.getSize())
                        .totalElementos(page.getTotalElements())
                        .totalPaginas(page.getTotalPages())
                        .build())
                .usuarios(usuarios)
                .build();
    }
}
