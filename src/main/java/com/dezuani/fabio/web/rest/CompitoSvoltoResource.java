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
import javax.validation.Valid;
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
     * {@code POST  /compiti-svolti} : Create a new compitoSvolto.
     *
     * @param compitoSvoltoDTO the compitoSvoltoDTO to create.
     * @param compitoId the id of the compito.
     * @param alunnoId the id of alunno.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compitoSvoltoDTO, or with status {@code 400 (Bad Request)} if the compitoSvolto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compiti-svolti/{compitoId}/{alunnoId}")
    public ResponseEntity<CompitoSvoltoDTO> createCompitoSvolto(
        @Valid @RequestBody CompitoSvoltoDTO compitoSvoltoDTO,
        @PathVariable Long compitoId,
        @PathVariable Long alunnoId
    ) throws URISyntaxException {
        log.debug("REST request to save CompitoSvolto : {}", compitoSvoltoDTO);
        if (compitoSvoltoDTO.getId() != null) {
            throw new BadRequestAlertException("A new compitoSvolto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompitoSvoltoDTO result = compitoSvoltoService.save(compitoSvoltoDTO, compitoId, alunnoId);
        if (result == null) {
            throw new BadRequestAlertException("Selected Compito or Alunno don't exist", ENTITY_NAME, "alunnoCompitoExist");
        }
        return ResponseEntity
            .created(new URI("/api/compiti-svolti/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compiti-svolti/:id} : Updates an existing compitoSvolto.
     *
     * @param id the id of the compitoSvoltoDTO to save.
     * @param compitoSvoltoDTO the compitoSvoltoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compitoSvoltoDTO,
     * or with status {@code 400 (Bad Request)} if the compitoSvoltoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compitoSvoltoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compiti-svolti/{id}")
    public ResponseEntity<CompitoSvoltoDTO> updateCompitoSvolto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompitoSvoltoDTO compitoSvoltoDTO
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
     * {@code PATCH  /compiti-svolti/:id} : Partial updates given fields of an existing compitoSvolto, field will ignore if it is null
     *
     * @param id the id of the compitoSvoltoDTO to save.
     * @param compitoSvoltoDTO the compitoSvoltoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compitoSvoltoDTO,
     * or with status {@code 400 (Bad Request)} if the compitoSvoltoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the compitoSvoltoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the compitoSvoltoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/compiti-svolti/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompitoSvoltoDTO> partialUpdateCompitoSvolto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompitoSvoltoDTO compitoSvoltoDTO
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
     * {@code GET  /compiti-svolti} : get all the compitoSvoltos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compitoSvoltos in body.
     */
    @GetMapping("/compiti-svolti")
    public List<CompitoSvoltoDTO> getAllCompitoSvoltos() {
        log.debug("REST request to get all CompitoSvoltos");
        return compitoSvoltoService.findAll();
    }

    /**
     * {@code GET  /compiti-svolti/:id} : get the "id" compitoSvolto.
     *
     * @param id the id of the compitoSvoltoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compitoSvoltoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compiti-svolti/{id}")
    public ResponseEntity<CompitoSvoltoDTO> getCompitoSvolto(@PathVariable Long id) {
        log.debug("REST request to get CompitoSvolto : {}", id);
        Optional<CompitoSvoltoDTO> compitoSvoltoDTO = compitoSvoltoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compitoSvoltoDTO);
    }

    /**
     * {@code GET  /compiti-svolti/alunno/:id} : get the "id" compitoSvolto.
     *
     * @param id the id of the compitoSvoltoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compitoSvoltoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compiti-svolti/alunno/{id}")
    public List<CompitoSvoltoDTO> getAlunnoCompitiSvolti(@PathVariable Long id) {
        log.debug("REST request to get CompitoSvolto : {}", id);
        return compitoSvoltoService.findAllCompitiByAlunno(id);
    }

    /**
     * {@code GET  /compiti-svolti/alunno/:id} : get the "id" compitoSvolto.
     *
     * @param id the id of the compitoSvoltoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compitoSvoltoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compiti-svolti/compito/{id}")
    public List<CompitoSvoltoDTO> getCompitiSvolti(@PathVariable Long id) {
        log.debug("REST request to get CompitoSvolto : {}", id);
        return compitoSvoltoService.findAllCompitiByCompito(id);
    }

    /**
     * {@code DELETE  /compiti-svolti/:id} : delete the "id" compitoSvolto.
     *
     * @param id the id of the compitoSvoltoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compiti-svolti/{id}")
    public ResponseEntity<Void> deleteCompitoSvolto(@PathVariable Long id) {
        log.debug("REST request to delete CompitoSvolto : {}", id);
        compitoSvoltoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
