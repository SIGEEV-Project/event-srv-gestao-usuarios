package com.sigeev.usuarios.adapters.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sigeev.usuarios.adapters.inbound.dtos.*;
import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.UsuarioServicePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioServicePort usuarioService;

    @Test
    void cadastrarUsuario_QuandoDadosValidos_DeveRetornarSucesso() throws Exception {
        // Arrange
        CadastroUsuarioRequest request = new CadastroUsuarioRequest();
        request.setPrimeiroNome("João");
        request.setUltimoNome("Silva");
        request.setEmail("joao@email.com");
        request.setCpf("12345678900");
        request.setSenha("Senha@123");

        Usuario usuarioCriado = Usuario.builder()
                .usuarioId(UUID.randomUUID())
                .primeiroNome(request.getPrimeiroNome())
                .ultimoNome(request.getUltimoNome())
                .email(request.getEmail())
                .build();

        when(usuarioService.cadastrarUsuario(any(), any())).thenReturn(usuarioCriado);

        // Act & Assert
        mockMvc.perform(post("/api/v1/usuarios/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sucesso").value(true))
                .andExpect(jsonPath("$.dados.email").value(request.getEmail()));
    }

    @Test
    void login_QuandoCredenciaisValidas_DeveRetornarToken() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("joao@email.com");
        request.setSenha("Senha@123");

        when(usuarioService.autenticarUsuario(any(), any())).thenReturn("token-jwt");

        // Act & Assert
        mockMvc.perform(post("/api/v1/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sucesso").value(true))
                .andExpect(jsonPath("$.dados.tokenAcesso").value("token-jwt"));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRADOR")
    void promoverUsuario_QuandoAdministrador_DeveRetornarSucesso() throws Exception {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        PromoverUsuarioRequest request = new PromoverUsuarioRequest();
        request.setAdminId(UUID.randomUUID());
        request.setNovoPerfil("PROMOTOR");

        // Act & Assert
        mockMvc.perform(put("/api/v1/usuarios/" + usuarioId + "/promover")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sucesso").value(true));
    }

    @Test
    @WithMockUser
    void excluirConta_QuandoUsuarioAutenticado_DeveRetornarSucesso() throws Exception {
        // Arrange
        UUID usuarioId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(delete("/api/v1/usuarios/" + usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sucesso").value(true))
                .andExpect(jsonPath("$.mensagem").value("Conta excluída com sucesso!"));
    }

    @Test
    void recuperarSenha_QuandoEmailValido_DeveRetornarSucesso() throws Exception {
        // Arrange
        RecuperarSenhaRequest request = new RecuperarSenhaRequest();
        request.setEmail("joao@email.com");

        // Act & Assert
        mockMvc.perform(post("/api/v1/usuarios/senha/recuperar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sucesso").value(true))
                .andExpect(jsonPath("$.mensagem").value("Email de recuperação enviado com sucesso!"));
    }
}
