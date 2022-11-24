package com.dezuani.fabio.web.rest;

import com.dezuani.fabio.repository.CompitoSvoltoRepository;
import com.dezuani.fabio.service.CompitoSvoltoService;
import com.dezuani.fabio.service.dto.CompitoSvoltoDTO;
import com.dezuani.fabio.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dezuani.fabio.domain.CompitoSvolto}.
 */
@RestController
@RequestMapping("/api")
public class CompitoSvoltoResource {

    private final Logger log = LoggerFactory.getLogger(CompitoSvoltoResource.class);

    private static final String ENTITY_NAME = "compitoSvolto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompitoSvoltoService compitoSvoltoService;

    private final CompitoSvoltoRepository compitoSvoltoRepository;

    public CompitoSvoltoResource(CompitoSvoltoService compitoSvoltoService, CompitoSvoltoRepository compitoSvoltoRepository) {
        this.compitoSvoltoService = compitoSvoltoService;
        this.compitoSvoltoRepository = compitoSvoltoRepository;
    }

    /**
     * {@code POST  /compito-svoltos} : Create a new compitoSvolto.
     *
     * @param compitoSvoltoDTO the compitoSvoltoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compitoSvoltoDTO, or with status {@code 400 (Bad Request)} if the compitoSvolto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compito-svoltos")
    public ResponseEntity<CompitoSvoltoDTO> createCompitoSvolto(@RequestBody CompitoSvoltoDTO compitoSvoltoDTO) throws URISyntaxException {
        log.debug("REST request to save CompitoSvolto : {}", compitoSvoltoDTO);
        if (compitoSvoltoDTO.getId() != null) {
            throw new BadRequestAlertException("A new compitoSvolto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompitoSvoltoDTO result = compitoSvoltoService.save(compitoSvoltoDTO);
        return ResponseEntity
            .created(new URI("/api/compito-svoltos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compito-svoltos/:id} : Updates an existing compitoSvolto.
     *
     * @param id the id of the compitoSvoltoDTO to save.
     * @param compitoSvoltoDTO the compitoSvoltoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compitoSvoltoDTO,
     * or with status {@code 400 (Bad Request)} if the compitoSvoltoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compitoSvoltoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compito-svoltos/{id}")
    public ResponseEntity<CompitoSvoltoDTO> updateCompitoSvolto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompitoSvoltoDTO compitoSvoltoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompitoSvolto : {}, {}", id, compitoSvoltoDTO);
        if (compitoSvoltoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compitoSvoltoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compitoSvoltoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompitoSvoltoDTO result = compitoSvoltoService.update(compitoSvoltoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compitoSvoltoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /compito-svoltos/:id} : Partial updates given fields of an existing compitoSvolto, field will ignore if it is null
     *
     * @param id the id of the compitoSvoltoDTO to save.
     * @param compitoSvoltoDTO the compitoSvoltoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compitoSvoltoDTO,
     * or with status {@code 400 (Bad Request)} if the compitoSvoltoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the compitoSvoltoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the compitoSvoltoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/compito-svoltos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompitoSvoltoDTO> partialUpdateCompitoSvolto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompitoSvoltoDTO compitoSvoltoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompitoSvolto partially : {}, {}", id, compitoSvoltoDTO);
        if (compitoSvoltoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compitoSvoltoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compitoSvoltoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompitoSvoltoDTO> result = compitoSvoltoService.partialUpdate(compitoSvoltoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compitoSvoltoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /compito-svoltos} : get all the compitoSvoltos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compitoSvoltos in body.
     */
    @GetMapping("/compito-svoltos")
    public List<CompitoSvoltoDTO> getAllCompitoSvoltos() {
        log.debug("REST request to get all CompitoSvoltos");
        return compitoSvoltoService.findAll();
    }

    /**
     * {@code GET  /compito-svoltos/:id} : get the "id" compitoSvolto.
     *
     * @param id the id of the compitoSvoltoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compitoSvoltoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compito-svoltos/{id}")
    public ResponseEntity<CompitoSvoltoDTO> getCompitoSvolto(@PathVariable Long id) {
        log.debug("REST request to get CompitoSvolto : {}", id);
        Optional<CompitoSvoltoDTO> compitoSvoltoDTO = compitoSvoltoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compitoSvoltoDTO);
    }

    /**
     * {@code DELETE  /compito-svoltos/:id} : delete the "id" compitoSvolto.
     *
     * @param id the id of the compitoSvoltoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compito-svoltos/{id}")
    public ResponseEntity<Void> deleteCompitoSvolto(@PathVariable Long id) {
        log.debug("REST request to delete CompitoSvolto : {}", id);
        compitoSvoltoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
