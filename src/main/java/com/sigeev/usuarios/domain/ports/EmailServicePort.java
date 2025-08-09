package com.sigeev.usuarios.domain.ports;

public interface EmailServicePort {
    void enviarEmailRecuperacaoSenha(String email, String token);
    void enviarEmailConfirmacaoCadastro(String email, String nome);
    void enviarEmailAlteracaoPerfil(String email, String nome, String novoPerfil);
    void enviarEmailExclusaoConta(String email, String nome);
    void enviarEmailAlteracaoDados(String email, String nome);
}
