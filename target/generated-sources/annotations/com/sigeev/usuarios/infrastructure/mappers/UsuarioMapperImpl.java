package com.sigeev.usuarios.infrastructure.mappers;

import com.sigeev.usuarios.adapters.inbound.dtos.CadastroUsuarioRequest;
import com.sigeev.usuarios.adapters.inbound.dtos.EnderecoDTO;
import com.sigeev.usuarios.domain.entities.Endereco;
import com.sigeev.usuarios.domain.entities.PerfilUsuario;
import com.sigeev.usuarios.domain.entities.Usuario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-08T17:58:39-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(CadastroUsuarioRequest request) {
        if ( request == null ) {
            return null;
        }

        Usuario.UsuarioBuilder usuario = Usuario.builder();

        usuario.primeiroNome( request.getPrimeiroNome() );
        usuario.ultimoNome( request.getUltimoNome() );
        usuario.email( request.getEmail() );
        usuario.cpf( request.getCpf() );
        usuario.telefone( request.getTelefone() );
        if ( request.getDataNascimento() != null ) {
            usuario.dataNascimento( LocalDate.parse( request.getDataNascimento() ) );
        }
        usuario.endereco( enderecoDTOToEndereco( request.getEndereco() ) );

        usuario.perfil( PerfilUsuario.PARTICIPANTE );
        usuario.status( "ativo" );
        usuario.dataCadastro( java.time.LocalDateTime.now() );

        return usuario.build();
    }

    @Override
    public CadastroUsuarioRequest toDto(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        CadastroUsuarioRequest cadastroUsuarioRequest = new CadastroUsuarioRequest();

        cadastroUsuarioRequest.setPrimeiroNome( usuario.getPrimeiroNome() );
        cadastroUsuarioRequest.setUltimoNome( usuario.getUltimoNome() );
        cadastroUsuarioRequest.setCpf( usuario.getCpf() );
        cadastroUsuarioRequest.setEmail( usuario.getEmail() );
        cadastroUsuarioRequest.setTelefone( usuario.getTelefone() );
        if ( usuario.getDataNascimento() != null ) {
            cadastroUsuarioRequest.setDataNascimento( DateTimeFormatter.ISO_LOCAL_DATE.format( usuario.getDataNascimento() ) );
        }
        cadastroUsuarioRequest.setEndereco( enderecoToEnderecoDTO( usuario.getEndereco() ) );

        return cadastroUsuarioRequest;
    }

    protected Endereco enderecoDTOToEndereco(EnderecoDTO enderecoDTO) {
        if ( enderecoDTO == null ) {
            return null;
        }

        Endereco.EnderecoBuilder endereco = Endereco.builder();

        endereco.logradouro( enderecoDTO.getLogradouro() );
        endereco.numero( enderecoDTO.getNumero() );
        endereco.cidade( enderecoDTO.getCidade() );
        endereco.estado( enderecoDTO.getEstado() );
        endereco.cep( enderecoDTO.getCep() );

        return endereco.build();
    }

    protected EnderecoDTO enderecoToEnderecoDTO(Endereco endereco) {
        if ( endereco == null ) {
            return null;
        }

        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setLogradouro( endereco.getLogradouro() );
        enderecoDTO.setNumero( endereco.getNumero() );
        enderecoDTO.setCidade( endereco.getCidade() );
        enderecoDTO.setEstado( endereco.getEstado() );
        enderecoDTO.setCep( endereco.getCep() );

        return enderecoDTO;
    }
}
