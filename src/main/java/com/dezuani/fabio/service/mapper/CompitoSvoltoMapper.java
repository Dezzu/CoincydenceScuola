package com.dezuani.fabio.service.mapper;

import com.dezuani.fabio.domain.Alunno;
import com.dezuani.fabio.domain.Compito;
import com.dezuani.fabio.domain.CompitoSvolto;
import com.dezuani.fabio.service.dto.AlunnoDTO;
import com.dezuani.fabio.service.dto.CompitoDTO;
import com.dezuani.fabio.service.dto.CompitoSvoltoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompitoSvolto} and its DTO {@link CompitoSvoltoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompitoSvoltoMapper extends EntityMapper<CompitoSvoltoDTO, CompitoSvolto> {
    @Mapping(target = "alunno", source = "alunno", qualifiedByName = "alunnoId")
    @Mapping(target = "compito", source = "compito", qualifiedByName = "compitoId")
    CompitoSvoltoDTO toDto(CompitoSvolto s);

    @Named("alunnoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AlunnoDTO toDtoAlunnoId(Alunno alunno);

    @Named("compitoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompitoDTO toDtoCompitoId(Compito compito);
}
