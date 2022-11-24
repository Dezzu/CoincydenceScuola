package com.dezuani.fabio.service;

import static com.dezuani.fabio.config.Constants.CLASSE_ENTITY;
import static com.dezuani.fabio.config.Constants.COMPITO_ENTITY;

import com.dezuani.fabio.domain.Compito;
import com.dezuani.fabio.repository.CompitoRepository;
import com.dezuani.fabio.repository.CompitoSvoltoRepository;
import com.dezuani.fabio.service.dto.CompitoDTO;
import com.dezuani.fabio.service.mapper.CompitoMapper;
import com.dezuani.fabio.web.rest.errors.BadRequestAlertException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Compito}.
 */
@Service
@Transactional
public class CompitoService {

    private final Logger log = LoggerFactory.getLogger(CompitoService.class);

    private final CompitoRepository compitoRepository;
    private final CompitoSvoltoRepository compitoSvoltoRepository;

    private final CompitoMapper compitoMapper;

    public CompitoService(
        CompitoRepository compitoRepository,
        CompitoSvoltoRepository compitoSvoltoRepository,
        CompitoMapper compitoMapper
    ) {
        this.compitoRepository = compitoRepository;
        this.compitoSvoltoRepository = compitoSvoltoRepository;
        this.compitoMapper = compitoMapper;
    }

    /**
     * Save a compito.
     *
     * @param compitoDTO the entity to save.
     * @return the persisted entity.
     */
    public CompitoDTO save(CompitoDTO compitoDTO) {
        log.debug("Request to save Compito : {}", compitoDTO);
        Compito compito = compitoMapper.toEntity(compitoDTO);
        compito = compitoRepository.save(compito);
        return compitoMapper.toDto(compito);
    }

    /**
     * Update a compito.
     *
     * @param compitoDTO the entity to save.
     * @return the persisted entity.
     */
    public CompitoDTO update(CompitoDTO compitoDTO) {
        log.debug("Request to update Compito : {}", compitoDTO);
        Compito compito = compitoMapper.toEntity(compitoDTO);
        compito = compitoRepository.save(compito);
        return compitoMapper.toDto(compito);
    }

    /**
     * Partially update a compito.
     *
     * @param compitoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CompitoDTO> partialUpdate(CompitoDTO compitoDTO) {
        log.debug("Request to partially update Compito : {}", compitoDTO);

        return compitoRepository
            .findById(compitoDTO.getId())
            .map(existingCompito -> {
                compitoMapper.partialUpdate(existingCompito, compitoDTO);

                return existingCompito;
            })
            .map(compitoRepository::save)
            .map(compitoMapper::toDto);
    }

    /**
     * Get all the compitos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CompitoDTO> findAll() {
        log.debug("Request to get all Compitos");
        return compitoRepository.findAll().stream().map(compitoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one compito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompitoDTO> findOne(Long id) {
        log.debug("Request to get Compito : {}", id);
        return compitoRepository.findById(id).map(compitoMapper::toDto);
    }

    /**
     * Delete the compito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Compito : {}", id);
        compitoRepository.deleteById(id);
    }
}
