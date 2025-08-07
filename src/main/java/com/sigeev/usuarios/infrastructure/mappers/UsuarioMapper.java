package com.sigeev.usuarios.infrastructure.mappers;

import com.sigeev.usuarios.adapters.outbound.persistence.entities.UsuarioEntity;
import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.adapters.inbound.rest.dtos.CadastroUsuarioRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    
    @Mapping(target = "endereco.logradouro", source = "logradouro")
    @Mapping(target = "endereco.numero", source = "numeroEndereco")
    @Mapping(target = "endereco.cidade", source = "cidade")
    @Mapping(target = "endereco.estado", source = "estado")
    @Mapping(target = "endereco.cep", source = "cep")
    @Mapping(target = "contato.telefone", source = "telefone")
    @Mapping(target = "contato.emailContato", source = "email")
    Usuario toDomain(UsuarioEntity entity);

    @Mapping(target = "logradouro", source = "endereco.logradouro")
    @Mapping(target = "numeroEndereco", source = "endereco.numero")
    @Mapping(target = "cidade", source = "endereco.cidade")
    @Mapping(target = "estado", source = "endereco.estado")
    @Mapping(target = "cep", source = "endereco.cep")
    @Mapping(target = "telefone", source = "contato.telefone")
    UsuarioEntity toEntity(Usuario domain);

    @Mapping(target = "primeiroNome", source = "usuario.primeiroNome")
    @Mapping(target = "ultimoNome", source = "usuario.ultimoNome")
    @Mapping(target = "cpf", source = "usuario.documento.numero")
    @Mapping(target = "email", source = "usuario.credenciais.email")
    @Mapping(target = "dataNascimento", expression = "java(parseDate(request.getUsuario().getDataNascimento()))")
    @Mapping(target = "endereco.logradouro", source = "endereco.logradouro")
    @Mapping(target = "endereco.numero", source = "endereco.numero")
    @Mapping(target = "endereco.cidade", source = "endereco.cidade")
    @Mapping(target = "endereco.estado", source = "endereco.estado")
    @Mapping(target = "endereco.cep", source = "endereco.cep")
    @Mapping(target = "contato.telefone", source = "usuario.contato.telefone")
    @Mapping(target = "contato.emailContato", source = "usuario.contato.emailContato")
    @Mapping(target = "usuarioId", ignore = true)
    @Mapping(target = "perfil", constant = "PARTICIPANTE")
    @Mapping(target = "status", constant = "ATIVO")
    @Mapping(target = "dataCadastro", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "dataUltimoLogin", ignore = true)
    @Mapping(target = "dataAlteracao", ignore = true)
    @Mapping(target = "dataExclusao", ignore = true)
    @Mapping(target = "dataReativacao", ignore = true)
    @Mapping(target = "tokenRecuperacao", ignore = true)
    @Mapping(target = "tokenRecuperacaoExpiraEm", ignore = true)
    @Mapping(target = "telefone", source = "usuario.contato.telefone")
    Usuario toDomain(CadastroUsuarioRequest request);

    default LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE_TIME);
    }
}
