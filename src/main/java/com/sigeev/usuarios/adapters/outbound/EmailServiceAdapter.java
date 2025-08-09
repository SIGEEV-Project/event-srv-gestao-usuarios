package com.sigeev.usuarios.adapters.outbound;

import com.sigeev.usuarios.domain.ports.EmailServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceAdapter implements EmailServicePort {
    
    private final JavaMailSender emailSender;
    private static final String FROM_EMAIL = "noreply@sigeev.com";

    @Override
    public void enviarEmailRecuperacaoSenha(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(email);
        message.setSubject("Recuperação de Senha - SIGEEV");
        message.setText("Para redefinir sua senha, acesse o link: " +
                "http://localhost:8080/usuarios/recuperar-senha?token=" + token + "\n" +
                "Este link expira em 1 hora.");
        
        emailSender.send(message);
    }

    @Override
    public void enviarEmailConfirmacaoCadastro(String email, String nome) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(email);
        message.setSubject("Bem-vindo ao SIGEEV!");
        message.setText("Olá " + nome + ",\n\n" +
                "Seja bem-vindo ao SIGEEV! Seu cadastro foi realizado com sucesso.\n" +
                "Você já pode acessar nossa plataforma e começar a participar dos eventos.");
        
        emailSender.send(message);
    }

    @Override
    public void enviarEmailAlteracaoPerfil(String email, String nome, String novoPerfil) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(email);
        message.setSubject("Alteração de Perfil - SIGEEV");
        message.setText("Olá " + nome + ",\n\n" +
                "Seu perfil foi alterado para " + novoPerfil + ".\n" +
                "Se você não reconhece esta alteração, entre em contato com nossa equipe de suporte.");
        
        emailSender.send(message);
    }

    @Override
    public void enviarEmailExclusaoConta(String email, String nome) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(email);
        message.setSubject("Exclusão de Conta - SIGEEV");
        message.setText("Olá " + nome + ",\n\n" +
                "Sua conta foi marcada para exclusão conforme solicitado.\n" +
                "Você tem 30 dias para reativar sua conta, caso deseje.\n" +
                "Após este período, seus dados serão permanentemente removidos.");
        
        emailSender.send(message);
    }

    @Override
    public void enviarEmailAlteracaoDados(String email, String nome) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(email);
        message.setSubject("Alteração de Dados - SIGEEV");
        message.setText("Olá " + nome + ",\n\n" +
                "Seus dados foram alterados conforme solicitado.\n" +
                "Se você não reconhece esta alteração, entre em contato com nossa equipe de suporte imediatamente.");
        
        emailSender.send(message);
    }
}
