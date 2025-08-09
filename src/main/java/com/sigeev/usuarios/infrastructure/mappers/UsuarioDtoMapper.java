package com.sigeev.usuarios.infrastructure.mappers;

import com.sigeev.usuarios.adapters.inbound.dtos.CadastroUsuarioRequest;
import com.sigeev.usuarios.adapters.inbound.dtos.EnderecoDTO;
import com.sigeev.usuarios.domain.entities.Endereco;
import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.entities.PerfilUsuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {LocalDateTime.class, PerfilUsuario.class}
)
public interface UsuarioDtoMapper {

    @Mapping(target = "usuarioId", ignore = true)
    @Mapping(target = "perfil", expression = "java(PerfilUsuario.PARTICIPANTE)")
    @Mapping(target = "status", constant = "ativo")
    @Mapping(target = "dataCadastro", expression = "java(LocalDateTime.now())")
    @Mapping(target = "dataNascimento", source = "dataNascimento", qualifiedByName = "stringToLocalDate")
    Usuario toUsuario(CadastroUsuarioRequest request);

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String date) {
        if (date == null) {
            return null;
        }
        return LocalDate.parse(date);
    }

    Endereco toEndereco(EnderecoDTO enderecoDTO);
    EnderecoDTO toEnderecoDTO(Endereco endereco);
}
