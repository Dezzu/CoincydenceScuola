package com.dezuani.fabio.service;

import com.dezuani.fabio.domain.Alunno;
import com.dezuani.fabio.repository.AlunnoRepository;
import com.dezuani.fabio.service.dto.AlunnoDTO;
import com.dezuani.fabio.service.mapper.AlunnoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Alunno}.
 */
@Service
@Transactional
public class AlunnoService {

    private final Logger log = LoggerFactory.getLogger(AlunnoService.class);

    private final AlunnoRepository alunnoRepository;

    private final AlunnoMapper alunnoMapper;

    public AlunnoService(AlunnoRepository alunnoRepository, AlunnoMapper alunnoMapper) {
        this.alunnoRepository = alunnoRepository;
        this.alunnoMapper = alunnoMapper;
    }

    /**
     * Save a alunno.
     *
     * @param alunnoDTO the entity to save.
     * @return the persisted entity.
     */
    public AlunnoDTO save(AlunnoDTO alunnoDTO) {
        log.debug("Request to save Alunno : {}", alunnoDTO);
        Alunno alunno = alunnoMapper.toEntity(alunnoDTO);
        alunno = alunnoRepository.save(alunno);
        return alunnoMapper.toDto(alunno);
    }

    /**
     * Update a alunno.
     *
     * @param alunnoDTO the entity to save.
     * @return the persisted entity.
     */
    public AlunnoDTO update(AlunnoDTO alunnoDTO) {
        log.debug("Request to update Alunno : {}", alunnoDTO);
        Alunno alunno = alunnoMapper.toEntity(alunnoDTO);
        alunno = alunnoRepository.save(alunno);
        return alunnoMapper.toDto(alunno);
    }

    /**
     * Partially update a alunno.
     *
     * @param alunnoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AlunnoDTO> partialUpdate(AlunnoDTO alunnoDTO) {
        log.debug("Request to partially update Alunno : {}", alunnoDTO);

        return alunnoRepository
            .findById(alunnoDTO.getId())
            .map(existingAlunno -> {
                alunnoMapper.partialUpdate(existingAlunno, alunnoDTO);

                return existingAlunno;
            })
            .map(alunnoRepository::save)
            .map(alunnoMapper::toDto);
    }

    /**
     * Get all the alunnos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AlunnoDTO> findAll() {
        log.debug("Request to get all Alunnos");
        return alunnoRepository.findAll().stream().map(alunnoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one alunno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AlunnoDTO> findOne(Long id) {
        log.debug("Request to get Alunno : {}", id);
        return alunnoRepository.findById(id).map(alunnoMapper::toDto);
    }

    /**
     * Delete the alunno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Alunno : {}", id);
        alunnoRepository.deleteById(id);
    }
}
