package com.dezuani.fabio.web.rest;

import com.dezuani.fabio.repository.AlunnoRepository;
import com.dezuani.fabio.service.AlunnoService;
import com.dezuani.fabio.service.dto.AlunnoDTO;
import com.dezuani.fabio.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dezuani.fabio.domain.Alunno}.
 */
@RestController
@RequestMapping("/api")
public class AlunnoResource {

    private final Logger log = LoggerFactory.getLogger(AlunnoResource.class);

    private static final String ENTITY_NAME = "alunno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlunnoService alunnoService;

    private final AlunnoRepository alunnoRepository;

    public AlunnoResource(AlunnoService alunnoService, AlunnoRepository alunnoRepository) {
        this.alunnoService = alunnoService;
        this.alunnoRepository = alunnoRepository;
    }

    /**
     * {@code POST  /alunnos} : Create a new alunno.
     *
     * @param alunnoDTO the alunnoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alunnoDTO, or with status {@code 400 (Bad Request)} if the alunno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alunnos")
    public ResponseEntity<AlunnoDTO> createAlunno(@Valid @RequestBody AlunnoDTO alunnoDTO) throws URISyntaxException {
        log.debug("REST request to save Alunno : {}", alunnoDTO);
        if (alunnoDTO.getId() != null) {
            throw new BadRequestAlertException("A new alunno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlunnoDTO result = alunnoService.save(alunnoDTO);
        return ResponseEntity
            .created(new URI("/api/alunnos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alunnos/:id} : Updates an existing alunno.
     *
     * @param id the id of the alunnoDTO to save.
     * @param alunnoDTO the alunnoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alunnoDTO,
     * or with status {@code 400 (Bad Request)} if the alunnoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alunnoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alunnos/{id}")
    public ResponseEntity<AlunnoDTO> updateAlunno(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AlunnoDTO alunnoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Alunno : {}, {}", id, alunnoDTO);
        if (alunnoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alunnoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alunnoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlunnoDTO result = alunnoService.update(alunnoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alunnoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /alunnos/:id} : Partial updates given fields of an existing alunno, field will ignore if it is null
     *
     * @param id the id of the alunnoDTO to save.
     * @param alunnoDTO the alunnoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alunnoDTO,
     * or with status {@code 400 (Bad Request)} if the alunnoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the alunnoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the alunnoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alunnos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AlunnoDTO> partialUpdateAlunno(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AlunnoDTO alunnoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Alunno partially : {}, {}", id, alunnoDTO);
        if (alunnoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alunnoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alunnoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlunnoDTO> result = alunnoService.partialUpdate(alunnoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alunnoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /alunnos} : get all the alunnos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alunnos in body.
     */
    @GetMapping("/alunnos")
    public List<AlunnoDTO> getAllAlunnos() {
        log.debug("REST request to get all Alunnos");
        return alunnoService.findAll();
    }

    /**
     * {@code GET  /alunnos/:id} : get the "id" alunno.
     *
     * @param id the id of the alunnoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alunnoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alunnos/{id}")
    public ResponseEntity<AlunnoDTO> getAlunno(@PathVariable Long id) {
        log.debug("REST request to get Alunno : {}", id);
        Optional<AlunnoDTO> alunnoDTO = alunnoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alunnoDTO);
    }

    /**
     * {@code DELETE  /alunnos/:id} : delete the "id" alunno.
     *
     * @param id the id of the alunnoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alunnos/{id}")
    public ResponseEntity<Void> deleteAlunno(@PathVariable Long id) {
        log.debug("REST request to delete Alunno : {}", id);
        alunnoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
