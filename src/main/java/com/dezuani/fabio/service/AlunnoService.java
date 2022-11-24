package com.dezuani.fabio.service;

import static com.dezuani.fabio.config.Constants.ALUNNO_ENTITY;

import com.dezuani.fabio.domain.Alunno;
import com.dezuani.fabio.domain.Classe;
import com.dezuani.fabio.repository.AlunnoRepository;
import com.dezuani.fabio.repository.ClasseRepository;
import com.dezuani.fabio.repository.CompitoSvoltoRepository;
import com.dezuani.fabio.service.dto.AlunnoDTO;
import com.dezuani.fabio.service.mapper.AlunnoMapper;
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
 * Service Implementation for managing {@link Alunno}.
 */
@Service
@Transactional
public class AlunnoService {

    private final Logger log = LoggerFactory.getLogger(AlunnoService.class);

    private final AlunnoRepository alunnoRepository;
    private final ClasseRepository classeRepository;
    private final CompitoSvoltoRepository compitoSvoltoRepository;

    private final AlunnoMapper alunnoMapper;

    public AlunnoService(
        AlunnoRepository alunnoRepository,
        ClasseRepository classeRepository,
        CompitoSvoltoRepository compitoSvoltoRepository,
        AlunnoMapper alunnoMapper
    ) {
        this.alunnoRepository = alunnoRepository;
        this.classeRepository = classeRepository;
        this.compitoSvoltoRepository = compitoSvoltoRepository;
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
        Classe classe = classeRepository
            .findByAnnoAndSezione(alunnoDTO.getClasse().getAnno(), alunnoDTO.getClasse().getSezione())
            .orElse(null);
        if (classe == null) return null;
        Alunno alunno = alunnoMapper.toEntity(alunnoDTO);
        alunno.setClasse(classe);
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
        Classe classe = classeRepository
            .findByAnnoAndSezione(alunnoDTO.getClasse().getAnno(), alunnoDTO.getClasse().getSezione())
            .orElse(null);
        if (classe == null) return null;
        Alunno alunno = alunnoMapper.toEntity(alunnoDTO);
        alunno.setClasse(classe);
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
                Classe classe = classeRepository
                    .findByAnnoAndSezione(alunnoDTO.getClasse().getAnno(), alunnoDTO.getClasse().getSezione())
                    .orElse(null);
                if (classe == null) return null;
                existingAlunno.setClasse(classe);
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

    @Transactional(readOnly = true)
    public List<AlunnoDTO> findByClasse(Integer anno, String sezione) {
        log.debug("Request to get all Alunnos by classe");
        return alunnoRepository
            .findAllByClasseAnnoAndClasseSezione(anno, sezione)
            .stream()
            .map(alunnoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
