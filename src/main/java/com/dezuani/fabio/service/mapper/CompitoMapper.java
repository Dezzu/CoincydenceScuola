package com.dezuani.fabio.service.mapper;

import com.dezuani.fabio.domain.Compito;
import com.dezuani.fabio.service.dto.CompitoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Compito} and its DTO {@link CompitoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompitoMapper extends EntityMapper<CompitoDTO, Compito> {}
