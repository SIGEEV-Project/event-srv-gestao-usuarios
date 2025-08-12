package com.sigeev.usuarios.infrastructure.mappers;

import com.sigeev.usuarios.adapters.outbound.persistence.entity.UsuarioEntity;
import com.sigeev.usuarios.domain.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {
    
    @Mapping(target = "endereco.logradouro", source = "logradouro")
    @Mapping(target = "endereco.numero", source = "numeroEndereco")
    @Mapping(target = "endereco.cidade", source = "cidade")
    @Mapping(target = "endereco.estado", source = "estado")
    @Mapping(target = "endereco.cep", source = "cep")
    Usuario toDomain(UsuarioEntity entity);

    @Mapping(target = "logradouro", source = "endereco.logradouro")
    @Mapping(target = "numeroEndereco", source = "endereco.numero")
    @Mapping(target = "cidade", source = "endereco.cidade")
    @Mapping(target = "estado", source = "endereco.estado")
    @Mapping(target = "cep", source = "endereco.cep")
    UsuarioEntity toEntity(Usuario domain);
}
