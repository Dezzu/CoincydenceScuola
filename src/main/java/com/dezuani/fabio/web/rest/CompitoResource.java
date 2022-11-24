package com.dezuani.fabio.web.rest;

import com.dezuani.fabio.repository.CompitoRepository;
import com.dezuani.fabio.service.CompitoService;
import com.dezuani.fabio.service.dto.CompitoDTO;
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
 * REST controller for managing {@link com.dezuani.fabio.domain.Compito}.
 */
@RestController
@RequestMapping("/api")
public class CompitoResource {

    private final Logger log = LoggerFactory.getLogger(CompitoResource.class);

    private static final String ENTITY_NAME = "compito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompitoService compitoService;

    private final CompitoRepository compitoRepository;

    public CompitoResource(CompitoService compitoService, CompitoRepository compitoRepository) {
        this.compitoService = compitoService;
        this.compitoRepository = compitoRepository;
    }

    /**
     * {@code POST  /compitos} : Create a new compito.
     *
     * @param compitoDTO the compitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compitoDTO, or with status {@code 400 (Bad Request)} if the compito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compitos")
    public ResponseEntity<CompitoDTO> createCompito(@RequestBody CompitoDTO compitoDTO) throws URISyntaxException {
        log.debug("REST request to save Compito : {}", compitoDTO);
        if (compitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new compito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompitoDTO result = compitoService.save(compitoDTO);
        return ResponseEntity
            .created(new URI("/api/compitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compitos/:id} : Updates an existing compito.
     *
     * @param id the id of the compitoDTO to save.
     * @param compitoDTO the compitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compitoDTO,
     * or with status {@code 400 (Bad Request)} if the compitoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compitoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compitos/{id}")
    public ResponseEntity<CompitoDTO> updateCompito(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompitoDTO compitoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Compito : {}, {}", id, compitoDTO);
        if (compitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compitoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compitoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompitoDTO result = compitoService.update(compitoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /compitos/:id} : Partial updates given fields of an existing compito, field will ignore if it is null
     *
     * @param id the id of the compitoDTO to save.
     * @param compitoDTO the compitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compitoDTO,
     * or with status {@code 400 (Bad Request)} if the compitoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the compitoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the compitoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/compitos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompitoDTO> partialUpdateCompito(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompitoDTO compitoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Compito partially : {}, {}", id, compitoDTO);
        if (compitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compitoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compitoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompitoDTO> result = compitoService.partialUpdate(compitoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compitoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /compitos} : get all the compitos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compitos in body.
     */
    @GetMapping("/compitos")
    public List<CompitoDTO> getAllCompitos() {
        log.debug("REST request to get all Compitos");
        return compitoService.findAll();
    }

    /**
     * {@code GET  /compitos/:id} : get the "id" compito.
     *
     * @param id the id of the compitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compitos/{id}")
    public ResponseEntity<CompitoDTO> getCompito(@PathVariable Long id) {
        log.debug("REST request to get Compito : {}", id);
        Optional<CompitoDTO> compitoDTO = compitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compitoDTO);
    }

    /**
     * {@code DELETE  /compitos/:id} : delete the "id" compito.
     *
     * @param id the id of the compitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compitos/{id}")
    public ResponseEntity<Void> deleteCompito(@PathVariable Long id) {
        log.debug("REST request to delete Compito : {}", id);
        compitoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
