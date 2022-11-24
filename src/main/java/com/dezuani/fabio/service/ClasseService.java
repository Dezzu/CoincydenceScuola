package com.dezuani.fabio.service;

import static com.dezuani.fabio.config.Constants.CLASSE_ENTITY;
import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

import com.dezuani.fabio.domain.Classe;
import com.dezuani.fabio.repository.AlunnoRepository;
import com.dezuani.fabio.repository.ClasseRepository;
import com.dezuani.fabio.service.dto.ClasseDTO;
import com.dezuani.fabio.service.mapper.ClasseMapper;
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
 * Service Implementation for managing {@link Classe}.
 */
@Service
@Transactional
public class ClasseService {

    private final Logger log = LoggerFactory.getLogger(ClasseService.class);

    private final ClasseRepository classeRepository;
    private final AlunnoRepository alunnoRepository;

    private final ClasseMapper classeMapper;

    public ClasseService(ClasseRepository classeRepository, AlunnoRepository alunnoRepository, ClasseMapper classeMapper) {
        this.classeRepository = classeRepository;
        this.alunnoRepository = alunnoRepository;
        this.classeMapper = classeMapper;
    }

    /**
     * Save a classe.
     *
     * @param classeDTO the entity to save.
     * @return the persisted entity.
     */
    public ClasseDTO save(ClasseDTO classeDTO) {
        log.debug("Request to save Classe : {}", classeDTO);
        Optional<Classe> classePresent = classeRepository.findByAnnoAndSezione(classeDTO.getAnno(), classeDTO.getSezione());
        if (classePresent.isPresent()) return null;

        Classe classe = classeMapper.toEntity(classeDTO);
        classe = classeRepository.save(classe);
        return classeMapper.toDto(classe);
    }

    /**
     * Update a classe.
     *
     * @param classeDTO the entity to save.
     * @return the persisted entity.
     */
    public ClasseDTO update(ClasseDTO classeDTO) {
        log.debug("Request to update Classe : {}", classeDTO);
        Optional<Classe> classePresent = classeRepository.findByAnnoAndSezioneAndIdNot(
            classeDTO.getAnno(),
            classeDTO.getSezione(),
            classeDTO.getId()
        );
        if (classePresent.isPresent()) return null;
        Classe classe = classeMapper.toEntity(classeDTO);
        classe = classeRepository.save(classe);
        return classeMapper.toDto(classe);
    }

    /**
     * Partially update a classe.
     *
     * @param classeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClasseDTO> partialUpdate(ClasseDTO classeDTO) {
        log.debug("Request to partially update Classe : {}", classeDTO);

        return classeRepository
            .findById(classeDTO.getId())
            .map(existingClasse -> {
                Optional<Classe> classePresent = classeRepository.findByAnnoAndSezioneAndIdNot(
                    classeDTO.getAnno() == null ? existingClasse.getAnno() : classeDTO.getAnno(),
                    classeDTO.getSezione() == null ? existingClasse.getSezione() : classeDTO.getSezione(),
                    classeDTO.getId()
                );
                if (classePresent.isPresent()) return null;
                classeMapper.partialUpdate(existingClasse, classeDTO);

                return existingClasse;
            })
            .map(classeRepository::save)
            .map(classeMapper::toDto);
    }

    /**
     * Get all the classes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClasseDTO> findAll() {
        log.debug("Request to get all Classes");
        return classeRepository.findAll().stream().map(classeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one classe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClasseDTO> findOne(Long id, Integer anno, String sezione) {
        log.debug("Request to get Classe : {}", id);
        if (anno != null && sezione != null) {
            return classeRepository.findByAnnoAndSezione(anno, sezione).map(classeMapper::toDto);
        }
        return classeRepository.findById(id).map(classeMapper::toDto);
    }

    /**
     * Delete the classe by id.
     *
     * @param anno the anno of the entity.
     * @param sezione the sezione of the entity.
     */
    public void delete(Integer anno, String sezione) {
        log.debug("Request to delete Classe : {} - {}", anno, sezione);
        classeRepository.deleteByAnnoAndSezione(anno, sezione);
    }
}
