package com.sigeev.usuarios.infrastructure.mappers;

import com.sigeev.usuarios.adapters.inbound.rest.dtos.CadastroUsuarioRequest;
import com.sigeev.usuarios.adapters.outbound.persistence.entities.UsuarioEntity;
import com.sigeev.usuarios.domain.entities.Contato;
import com.sigeev.usuarios.domain.entities.Endereco;
import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.entities.enums.PerfilUsuario;
import com.sigeev.usuarios.domain.entities.enums.StatusUsuario;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-06T21:02:04-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toDomain(UsuarioEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Usuario.UsuarioBuilder usuario = Usuario.builder();

        usuario.endereco( usuarioEntityToEndereco( entity ) );
        usuario.contato( usuarioEntityToContato( entity ) );
        usuario.usuarioId( entity.getUsuarioId() );
        usuario.primeiroNome( entity.getPrimeiroNome() );
        usuario.ultimoNome( entity.getUltimoNome() );
        usuario.email( entity.getEmail() );
        usuario.cpf( entity.getCpf() );
        usuario.telefone( entity.getTelefone() );
        usuario.dataNascimento( entity.getDataNascimento() );
        if ( entity.getPerfil() != null ) {
            usuario.perfil( Enum.valueOf( PerfilUsuario.class, entity.getPerfil() ) );
        }
        if ( entity.getStatus() != null ) {
            usuario.status( Enum.valueOf( StatusUsuario.class, entity.getStatus() ) );
        }
        usuario.dataCadastro( entity.getDataCadastro() );
        usuario.dataUltimoLogin( entity.getDataUltimoLogin() );
        usuario.dataAlteracao( entity.getDataAlteracao() );
        usuario.dataExclusao( entity.getDataExclusao() );
        usuario.dataReativacao( entity.getDataReativacao() );
        usuario.tokenRecuperacao( entity.getTokenRecuperacao() );
        usuario.tokenRecuperacaoExpiraEm( entity.getTokenRecuperacaoExpiraEm() );

        return usuario.build();
    }

    @Override
    public UsuarioEntity toEntity(Usuario domain) {
        if ( domain == null ) {
            return null;
        }

        UsuarioEntity.UsuarioEntityBuilder usuarioEntity = UsuarioEntity.builder();

        usuarioEntity.logradouro( domainEnderecoLogradouro( domain ) );
        usuarioEntity.numeroEndereco( domainEnderecoNumero( domain ) );
        usuarioEntity.cidade( domainEnderecoCidade( domain ) );
        usuarioEntity.estado( domainEnderecoEstado( domain ) );
        usuarioEntity.cep( domainEnderecoCep( domain ) );
        usuarioEntity.telefone( domainContatoTelefone( domain ) );
        usuarioEntity.usuarioId( domain.getUsuarioId() );
        usuarioEntity.primeiroNome( domain.getPrimeiroNome() );
        usuarioEntity.ultimoNome( domain.getUltimoNome() );
        usuarioEntity.email( domain.getEmail() );
        usuarioEntity.cpf( domain.getCpf() );
        usuarioEntity.dataNascimento( domain.getDataNascimento() );
        if ( domain.getPerfil() != null ) {
            usuarioEntity.perfil( domain.getPerfil().name() );
        }
        if ( domain.getStatus() != null ) {
            usuarioEntity.status( domain.getStatus().name() );
        }
        usuarioEntity.dataCadastro( domain.getDataCadastro() );
        usuarioEntity.dataUltimoLogin( domain.getDataUltimoLogin() );
        usuarioEntity.dataAlteracao( domain.getDataAlteracao() );
        usuarioEntity.dataExclusao( domain.getDataExclusao() );
        usuarioEntity.dataReativacao( domain.getDataReativacao() );
        usuarioEntity.tokenRecuperacao( domain.getTokenRecuperacao() );
        usuarioEntity.tokenRecuperacaoExpiraEm( domain.getTokenRecuperacaoExpiraEm() );

        return usuarioEntity.build();
    }

    @Override
    public Usuario toDomain(CadastroUsuarioRequest request) {
        if ( request == null ) {
            return null;
        }

        Usuario.UsuarioBuilder usuario = Usuario.builder();

        usuario.endereco( enderecoToEndereco( request.getEndereco() ) );
        usuario.contato( dadosUsuarioToContato( request.getUsuario() ) );
        usuario.primeiroNome( requestUsuarioPrimeiroNome( request ) );
        usuario.ultimoNome( requestUsuarioUltimoNome( request ) );
        usuario.cpf( requestUsuarioDocumentoNumero( request ) );
        usuario.email( requestUsuarioCredenciaisEmail( request ) );
        usuario.telefone( requestUsuarioContatoTelefone( request ) );

        usuario.dataNascimento( parseDate(request.getUsuario().getDataNascimento()) );
        usuario.perfil( PerfilUsuario.PARTICIPANTE );
        usuario.status( StatusUsuario.ATIVO );
        usuario.dataCadastro( java.time.LocalDateTime.now() );

        return usuario.build();
    }

    protected Endereco usuarioEntityToEndereco(UsuarioEntity usuarioEntity) {
        if ( usuarioEntity == null ) {
            return null;
        }

        Endereco.EnderecoBuilder endereco = Endereco.builder();

        endereco.logradouro( usuarioEntity.getLogradouro() );
        endereco.numero( usuarioEntity.getNumeroEndereco() );
        endereco.cidade( usuarioEntity.getCidade() );
        endereco.estado( usuarioEntity.getEstado() );
        endereco.cep( usuarioEntity.getCep() );

        return endereco.build();
    }

    protected Contato usuarioEntityToContato(UsuarioEntity usuarioEntity) {
        if ( usuarioEntity == null ) {
            return null;
        }

        Contato.ContatoBuilder contato = Contato.builder();

        contato.telefone( usuarioEntity.getTelefone() );
        contato.emailContato( usuarioEntity.getEmail() );

        return contato.build();
    }

    private String domainEnderecoLogradouro(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }
        Endereco endereco = usuario.getEndereco();
        if ( endereco == null ) {
            return null;
        }
        String logradouro = endereco.getLogradouro();
        if ( logradouro == null ) {
            return null;
        }
        return logradouro;
    }

    private String domainEnderecoNumero(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }
        Endereco endereco = usuario.getEndereco();
        if ( endereco == null ) {
            return null;
        }
        String numero = endereco.getNumero();
        if ( numero == null ) {
            return null;
        }
        return numero;
    }

    private String domainEnderecoCidade(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }
        Endereco endereco = usuario.getEndereco();
        if ( endereco == null ) {
            return null;
        }
        String cidade = endereco.getCidade();
        if ( cidade == null ) {
            return null;
        }
        return cidade;
    }

    private String domainEnderecoEstado(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }
        Endereco endereco = usuario.getEndereco();
        if ( endereco == null ) {
            return null;
        }
        String estado = endereco.getEstado();
        if ( estado == null ) {
            return null;
        }
        return estado;
    }

    private String domainEnderecoCep(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }
        Endereco endereco = usuario.getEndereco();
        if ( endereco == null ) {
            return null;
        }
        String cep = endereco.getCep();
        if ( cep == null ) {
            return null;
        }
        return cep;
    }

    private String domainContatoTelefone(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }
        Contato contato = usuario.getContato();
        if ( contato == null ) {
            return null;
        }
        String telefone = contato.getTelefone();
        if ( telefone == null ) {
            return null;
        }
        return telefone;
    }

    protected Endereco enderecoToEndereco(CadastroUsuarioRequest.Endereco endereco) {
        if ( endereco == null ) {
            return null;
        }

        Endereco.EnderecoBuilder endereco1 = Endereco.builder();

        endereco1.logradouro( endereco.getLogradouro() );
        endereco1.numero( endereco.getNumero() );
        endereco1.cidade( endereco.getCidade() );
        endereco1.estado( endereco.getEstado() );
        endereco1.cep( endereco.getCep() );

        return endereco1.build();
    }

    private String dadosUsuarioContatoTelefone(CadastroUsuarioRequest.DadosUsuario dadosUsuario) {
        if ( dadosUsuario == null ) {
            return null;
        }
        CadastroUsuarioRequest.Contato contato = dadosUsuario.getContato();
        if ( contato == null ) {
            return null;
        }
        String telefone = contato.getTelefone();
        if ( telefone == null ) {
            return null;
        }
        return telefone;
    }

    private String dadosUsuarioContatoEmailContato(CadastroUsuarioRequest.DadosUsuario dadosUsuario) {
        if ( dadosUsuario == null ) {
            return null;
        }
        CadastroUsuarioRequest.Contato contato = dadosUsuario.getContato();
        if ( contato == null ) {
            return null;
        }
        String emailContato = contato.getEmailContato();
        if ( emailContato == null ) {
            return null;
        }
        return emailContato;
    }

    protected Contato dadosUsuarioToContato(CadastroUsuarioRequest.DadosUsuario dadosUsuario) {
        if ( dadosUsuario == null ) {
            return null;
        }

        Contato.ContatoBuilder contato = Contato.builder();

        contato.telefone( dadosUsuarioContatoTelefone( dadosUsuario ) );
        contato.emailContato( dadosUsuarioContatoEmailContato( dadosUsuario ) );

        return contato.build();
    }

    private String requestUsuarioPrimeiroNome(CadastroUsuarioRequest cadastroUsuarioRequest) {
        if ( cadastroUsuarioRequest == null ) {
            return null;
        }
        CadastroUsuarioRequest.DadosUsuario usuario = cadastroUsuarioRequest.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        String primeiroNome = usuario.getPrimeiroNome();
        if ( primeiroNome == null ) {
            return null;
        }
        return primeiroNome;
    }

    private String requestUsuarioUltimoNome(CadastroUsuarioRequest cadastroUsuarioRequest) {
        if ( cadastroUsuarioRequest == null ) {
            return null;
        }
        CadastroUsuarioRequest.DadosUsuario usuario = cadastroUsuarioRequest.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        String ultimoNome = usuario.getUltimoNome();
        if ( ultimoNome == null ) {
            return null;
        }
        return ultimoNome;
    }

    private String requestUsuarioDocumentoNumero(CadastroUsuarioRequest cadastroUsuarioRequest) {
        if ( cadastroUsuarioRequest == null ) {
            return null;
        }
        CadastroUsuarioRequest.DadosUsuario usuario = cadastroUsuarioRequest.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        CadastroUsuarioRequest.Documento documento = usuario.getDocumento();
        if ( documento == null ) {
            return null;
        }
        String numero = documento.getNumero();
        if ( numero == null ) {
            return null;
        }
        return numero;
    }

    private String requestUsuarioCredenciaisEmail(CadastroUsuarioRequest cadastroUsuarioRequest) {
        if ( cadastroUsuarioRequest == null ) {
            return null;
        }
        CadastroUsuarioRequest.DadosUsuario usuario = cadastroUsuarioRequest.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        CadastroUsuarioRequest.Credenciais credenciais = usuario.getCredenciais();
        if ( credenciais == null ) {
            return null;
        }
        String email = credenciais.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String requestUsuarioContatoTelefone(CadastroUsuarioRequest cadastroUsuarioRequest) {
        if ( cadastroUsuarioRequest == null ) {
            return null;
        }
        CadastroUsuarioRequest.DadosUsuario usuario = cadastroUsuarioRequest.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        CadastroUsuarioRequest.Contato contato = usuario.getContato();
        if ( contato == null ) {
            return null;
        }
        String telefone = contato.getTelefone();
        if ( telefone == null ) {
            return null;
        }
        return telefone;
    }
}
