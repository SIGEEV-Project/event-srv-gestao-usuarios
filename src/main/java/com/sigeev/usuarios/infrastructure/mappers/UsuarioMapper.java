package com.sigeev.usuarios.infrastructure.mappers;

import com.sigeev.usuarios.adapters.inbound.dtos.CadastroUsuarioRequest;
import com.sigeev.usuarios.domain.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    @Mapping(target = "usuarioId", ignore = true)
    @Mapping(target = "perfil", constant = "PARTICIPANTE")
    @Mapping(target = "status", constant = "ativo")
    @Mapping(target = "dataCadastro", expression = "java(java.time.LocalDateTime.now())")
    Usuario toEntity(CadastroUsuarioRequest request);

    @Mapping(target = "senha", ignore = true)
    CadastroUsuarioRequest toDto(Usuario usuario);
}
