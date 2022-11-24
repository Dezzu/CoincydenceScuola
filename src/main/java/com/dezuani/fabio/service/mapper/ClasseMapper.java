package com.dezuani.fabio.service.mapper;

import com.dezuani.fabio.domain.Classe;
import com.dezuani.fabio.service.dto.ClasseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Classe} and its DTO {@link ClasseDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClasseMapper extends EntityMapper<ClasseDTO, Classe> {}
