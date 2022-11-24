package com.dezuani.fabio.service;

import com.dezuani.fabio.domain.CompitoSvolto;
import com.dezuani.fabio.repository.CompitoSvoltoRepository;
import com.dezuani.fabio.service.dto.CompitoSvoltoDTO;
import com.dezuani.fabio.service.mapper.CompitoSvoltoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompitoSvolto}.
 */
@Service
@Transactional
public class CompitoSvoltoService {

    private final Logger log = LoggerFactory.getLogger(CompitoSvoltoService.class);

    private final CompitoSvoltoRepository compitoSvoltoRepository;

    private final CompitoSvoltoMapper compitoSvoltoMapper;

    public CompitoSvoltoService(CompitoSvoltoRepository compitoSvoltoRepository, CompitoSvoltoMapper compitoSvoltoMapper) {
        this.compitoSvoltoRepository = compitoSvoltoRepository;
        this.compitoSvoltoMapper = compitoSvoltoMapper;
    }

    /**
     * Save a compitoSvolto.
     *
     * @param compitoSvoltoDTO the entity to save.
     * @return the persisted entity.
     */
    public CompitoSvoltoDTO save(CompitoSvoltoDTO compitoSvoltoDTO) {
        log.debug("Request to save CompitoSvolto : {}", compitoSvoltoDTO);
        CompitoSvolto compitoSvolto = compitoSvoltoMapper.toEntity(compitoSvoltoDTO);
        compitoSvolto = compitoSvoltoRepository.save(compitoSvolto);
        return compitoSvoltoMapper.toDto(compitoSvolto);
    }

    /**
     * Update a compitoSvolto.
     *
     * @param compitoSvoltoDTO the entity to save.
     * @return the persisted entity.
     */
    public CompitoSvoltoDTO update(CompitoSvoltoDTO compitoSvoltoDTO) {
        log.debug("Request to update CompitoSvolto : {}", compitoSvoltoDTO);
        CompitoSvolto compitoSvolto = compitoSvoltoMapper.toEntity(compitoSvoltoDTO);
        compitoSvolto = compitoSvoltoRepository.save(compitoSvolto);
        return compitoSvoltoMapper.toDto(compitoSvolto);
    }

    /**
     * Partially update a compitoSvolto.
     *
     * @param compitoSvoltoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CompitoSvoltoDTO> partialUpdate(CompitoSvoltoDTO compitoSvoltoDTO) {
        log.debug("Request to partially update CompitoSvolto : {}", compitoSvoltoDTO);

        return compitoSvoltoRepository
            .findById(compitoSvoltoDTO.getId())
            .map(existingCompitoSvolto -> {
                compitoSvoltoMapper.partialUpdate(existingCompitoSvolto, compitoSvoltoDTO);

                return existingCompitoSvolto;
            })
            .map(compitoSvoltoRepository::save)
            .map(compitoSvoltoMapper::toDto);
    }

    /**
     * Get all the compitoSvoltos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CompitoSvoltoDTO> findAll() {
        log.debug("Request to get all CompitoSvoltos");
        return compitoSvoltoRepository.findAll().stream().map(compitoSvoltoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one compitoSvolto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompitoSvoltoDTO> findOne(Long id) {
        log.debug("Request to get CompitoSvolto : {}", id);
        return compitoSvoltoRepository.findById(id).map(compitoSvoltoMapper::toDto);
    }

    /**
     * Delete the compitoSvolto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompitoSvolto : {}", id);
        compitoSvoltoRepository.deleteById(id);
    }
}
