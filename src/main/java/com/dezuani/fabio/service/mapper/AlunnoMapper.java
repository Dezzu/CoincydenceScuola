package com.dezuani.fabio.service.mapper;

import com.dezuani.fabio.domain.Alunno;
import com.dezuani.fabio.domain.Classe;
import com.dezuani.fabio.service.dto.AlunnoDTO;
import com.dezuani.fabio.service.dto.ClasseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Alunno} and its DTO {@link AlunnoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlunnoMapper extends EntityMapper<AlunnoDTO, Alunno> {
    @Mapping(target = "classe", source = "classe", qualifiedByName = "classeId")
    AlunnoDTO toDto(Alunno s);

    @Named("classeId")
    ClasseDTO toDtoClasseId(Classe classe);
}
