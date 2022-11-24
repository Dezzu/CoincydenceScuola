package com.dezuani.fabio.web.rest;

import static com.dezuani.fabio.config.Constants.CLASSE_ENTITY;

import com.dezuani.fabio.repository.ClasseRepository;
import com.dezuani.fabio.service.ClasseService;
import com.dezuani.fabio.service.dto.ClasseDTO;
import com.dezuani.fabio.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dezuani.fabio.domain.Classe}.
 */
@RestController
@RequestMapping("/api")
public class ClasseResource {

    private final Logger log = LoggerFactory.getLogger(ClasseResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClasseService classeService;

    private final ClasseRepository classeRepository;

    public ClasseResource(ClasseService classeService, ClasseRepository classeRepository) {
        this.classeService = classeService;
        this.classeRepository = classeRepository;
    }

    /**
     * {@code POST  /classes} : Create a new classe.
     *
     * @param classeDTO the classeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classeDTO, or with status {@code 400 (Bad Request)} if the classe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/classi")
    public ResponseEntity<ClasseDTO> createClasse(@Valid @RequestBody ClasseDTO classeDTO) throws URISyntaxException {
        log.debug("REST request to save Classe : {}", classeDTO);
        if (classeDTO.getId() != null) throw new BadRequestAlertException(
            "A new classe cannot already have an ID",
            CLASSE_ENTITY,
            "idexists"
        );

        ClasseDTO result = classeService.save(classeDTO);

        if (result == null) throw new BadRequestAlertException("Classe already present", CLASSE_ENTITY, "classeExists");

        return ResponseEntity
            .created(new URI("/api/classi/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, CLASSE_ENTITY, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /classes/:id} : Updates an existing classe.
     *
     * @param id the id of the classeDTO to save.
     * @param classeDTO the classeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classeDTO,
     * or with status {@code 400 (Bad Request)} if the classeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/classi/{id}")
    public ResponseEntity<ClasseDTO> updateClasse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClasseDTO classeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Classe : {}, {}", id, classeDTO);
        if (classeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", CLASSE_ENTITY, "idnull");
        }
        if (!Objects.equals(id, classeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", CLASSE_ENTITY, "idinvalid");
        }

        if (!classeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", CLASSE_ENTITY, "idnotfound");
        }

        ClasseDTO result = classeService.update(classeDTO);
        if (result == null) throw new BadRequestAlertException("Classe already present", CLASSE_ENTITY, "classeExists");

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, CLASSE_ENTITY, classeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /classes/:id} : Partial updates given fields of an existing classe, field will ignore if it is null
     *
     * @param id the id of the classeDTO to save.
     * @param classeDTO the classeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classeDTO,
     * or with status {@code 400 (Bad Request)} if the classeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the classeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the classeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/classi/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClasseDTO> partialUpdateClasse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClasseDTO classeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Classe partially : {}, {}", id, classeDTO);
        if (classeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", CLASSE_ENTITY, "idnull");
        }
        if (!Objects.equals(id, classeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", CLASSE_ENTITY, "idinvalid");
        }

        if (!classeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", CLASSE_ENTITY, "idnotfound");
        }

        Optional<ClasseDTO> result = classeService.partialUpdate(classeDTO);

        if (result.isEmpty()) throw new BadRequestAlertException("Classe already present", CLASSE_ENTITY, "classeExists");

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, CLASSE_ENTITY, classeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /classes} : get all the classes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classes in body.
     */
    @GetMapping("/public/classi")
    public List<ClasseDTO> getAllClasses() {
        log.debug("REST request to get all Classes");
        return classeService.findAll();
    }

    /**
     * {@code GET  /classes/:id} : get the "id" classe.
     *
     * @param id the id of the classeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public/classi/{id}")
    public ResponseEntity<ClasseDTO> getClasse(@PathVariable Long id) {
        log.debug("REST request to get Classe : {}", id);
        Optional<ClasseDTO> classeDTO = classeService.findOne(id, null, null);
        return ResponseUtil.wrapOrNotFound(classeDTO);
    }

    /**
     * {@code GET  /classes/:anno/:sezione} : get the "anno" and "sezione".
     *
     * @param anno the anno of the classeDTO to retrieve.
     * @param sezione the sezione of the classeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public/classi/{anno}/{sezione}")
    public ResponseEntity<ClasseDTO> getClasse(@PathVariable Integer anno, @PathVariable String sezione) {
        log.debug("REST request to get Classe : {} - {}", anno, sezione);
        Optional<ClasseDTO> classeDTO = classeService.findOne(null, anno, sezione);
        return ResponseUtil.wrapOrNotFound(classeDTO);
    }

    /**
     * {@code DELETE  /classes/:anno/:sezione} : delete the "anno" and "sezione" classe.
     *
     * @param anno the anno of the classeDTO to delete.
     * @param sezione the sezione of the classeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/classi/{anno}/{sezione}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Integer anno, @PathVariable String sezione) {
        log.debug("REST request to delete Classe : {} - {}", anno, sezione);
        try {
            classeService.delete(anno, sezione);
        } catch (Exception e) {
            throw new BadRequestAlertException("Alunni present for anno and sezione", CLASSE_ENTITY, "alunniExists");
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, CLASSE_ENTITY, anno.toString()))
            .build();
    }
}
