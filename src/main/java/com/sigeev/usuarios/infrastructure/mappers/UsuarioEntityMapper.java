package com.sigeev.usuarios.infrastructure.mappers;

import com.sigeev.usuarios.adapters.outbound.persistence.UsuarioEntity;
import com.sigeev.usuarios.domain.entities.Endereco;
import com.sigeev.usuarios.domain.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioEntityMapper {

    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "endereco", expression = "java(mapToEndereco(entity))")
    Usuario toDomain(UsuarioEntity entity);

    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "logradouro", source = "endereco.logradouro")
    @Mapping(target = "numeroEndereco", source = "endereco.numero")
    @Mapping(target = "cidade", source = "endereco.cidade")
    @Mapping(target = "estado", source = "endereco.estado")
    @Mapping(target = "cep", source = "endereco.cep")
    UsuarioEntity toEntity(Usuario domain);

    default Endereco mapToEndereco(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }
        return Endereco.builder()
                .logradouro(entity.getLogradouro())
                .numero(entity.getNumeroEndereco())
                .cidade(entity.getCidade())
                .estado(entity.getEstado())
                .cep(entity.getCep())
                .build();
    }
}
