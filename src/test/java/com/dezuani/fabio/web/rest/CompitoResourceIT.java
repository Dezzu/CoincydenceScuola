package com.dezuani.fabio.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dezuani.fabio.IntegrationTest;
import com.dezuani.fabio.domain.Compito;
import com.dezuani.fabio.domain.enumeration.Materia;
import com.dezuani.fabio.repository.CompitoRepository;
import com.dezuani.fabio.service.dto.CompitoDTO;
import com.dezuani.fabio.service.mapper.CompitoMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CompitoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompitoResourceIT {

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Materia DEFAULT_MATERIA = Materia.STORIA;
    private static final Materia UPDATED_MATERIA = Materia.ITALIANO;

    private static final String ENTITY_API_URL = "/api/compitos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompitoRepository compitoRepository;

    @Autowired
    private CompitoMapper compitoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompitoMockMvc;

    private Compito compito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compito createEntity(EntityManager em) {
        Compito compito = new Compito().data(DEFAULT_DATA).materia(DEFAULT_MATERIA);
        return compito;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compito createUpdatedEntity(EntityManager em) {
        Compito compito = new Compito().data(UPDATED_DATA).materia(UPDATED_MATERIA);
        return compito;
    }

    @BeforeEach
    public void initTest() {
        compito = createEntity(em);
    }

    @Test
    @Transactional
    void createCompito() throws Exception {
        int databaseSizeBeforeCreate = compitoRepository.findAll().size();
        // Create the Compito
        CompitoDTO compitoDTO = compitoMapper.toDto(compito);
        restCompitoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compitoDTO)))
            .andExpect(status().isCreated());

        // Validate the Compito in the database
        List<Compito> compitoList = compitoRepository.findAll();
        assertThat(compitoList).hasSize(databaseSizeBeforeCreate + 1);
        Compito testCompito = compitoList.get(compitoList.size() - 1);
        assertThat(testCompito.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testCompito.getMateria()).isEqualTo(DEFAULT_MATERIA);
    }

    @Test
    @Transactional
    void createCompitoWithExistingId() throws Exception {
        // Create the Compito with an existing ID
        compito.setId(1L);
        CompitoDTO compitoDTO = compitoMapper.toDto(compito);

        int databaseSizeBeforeCreate = compitoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompitoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compito in the database
        List<Compito> compitoList = compitoRepository.findAll();
        assertThat(compitoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompitos() throws Exception {
        // Initialize the database
        compitoRepository.saveAndFlush(compito);

        // Get all the compitoList
        restCompitoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compito.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].materia").value(hasItem(DEFAULT_MATERIA.toString())));
    }

    @Test
    @Transactional
    void getCompito() throws Exception {
        // Initialize the database
        compitoRepository.saveAndFlush(compito);

        // Get the compito
        restCompitoMockMvc
            .perform(get(ENTITY_API_URL_ID, compito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(compito.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.materia").value(DEFAULT_MATERIA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCompito() throws Exception {
        // Get the compito
        restCompitoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompito() throws Exception {
        // Initialize the database
        compitoRepository.saveAndFlush(compito);

        int databaseSizeBeforeUpdate = compitoRepository.findAll().size();

        // Update the compito
        Compito updatedCompito = compitoRepository.findById(compito.getId()).get();
        // Disconnect from session so that the updates on updatedCompito are not directly saved in db
        em.detach(updatedCompito);
        updatedCompito.data(UPDATED_DATA).materia(UPDATED_MATERIA);
        CompitoDTO compitoDTO = compitoMapper.toDto(updatedCompito);

        restCompitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, compitoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compitoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Compito in the database
        List<Compito> compitoList = compitoRepository.findAll();
        assertThat(compitoList).hasSize(databaseSizeBeforeUpdate);
        Compito testCompito = compitoList.get(compitoList.size() - 1);
        assertThat(testCompito.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testCompito.getMateria()).isEqualTo(UPDATED_MATERIA);
    }

    @Test
    @Transactional
    void putNonExistingCompito() throws Exception {
        int databaseSizeBeforeUpdate = compitoRepository.findAll().size();
        compito.setId(count.incrementAndGet());

        // Create the Compito
        CompitoDTO compitoDTO = compitoMapper.toDto(compito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, compitoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compitoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Compito in the database
        List<Compito> compitoList = compitoRepository.findAll();
        assertThat(compitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompito() throws Exception {
        int databaseSizeBeforeUpdate = compitoRepository.findAll().size();
        compito.setId(count.incrementAndGet());

        // Create the Compito
        CompitoDTO compitoDTO = compitoMapper.toDto(compito);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compitoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Compito in the database
        List<Compito> compitoList = compitoRepository.findAll();
        assertThat(compitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompito() throws Exception {
        int databaseSizeBeforeUpdate = compitoRepository.findAll().size();
        compito.setId(count.incrementAndGet());

        // Create the Compito
        CompitoDTO compitoDTO = compitoMapper.toDto(compito);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompitoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compitoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Compito in the database
        List<Compito> compitoList = compitoRepository.findAll();
        assertThat(compitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompitoWithPatch() throws Exception {
        // Initialize the database
        compitoRepository.saveAndFlush(compito);

        int databaseSizeBeforeUpdate = compitoRepository.findAll().size();

        // Update the compito using partial update
        Compito partialUpdatedCompito = new Compito();
        partialUpdatedCompito.setId(compito.getId());

        partialUpdatedCompito.data(UPDATED_DATA);

        restCompitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompito))
            )
            .andExpect(status().isOk());

        // Validate the Compito in the database
        List<Compito> compitoList = compitoRepository.findAll();
        assertThat(compitoList).hasSize(databaseSizeBeforeUpdate);
        Compito testCompito = compitoList.get(compitoList.size() - 1);
        assertThat(testCompito.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testCompito.getMateria()).isEqualTo(DEFAULT_MATERIA);
    }

    @Test
    @Transactional
    void fullUpdateCompitoWithPatch() throws Exception {
        // Initialize the database
        compitoRepository.saveAndFlush(compito);

        int databaseSizeBeforeUpdate = compitoRepository.findAll().size();

        // Update the compito using partial update
        Compito partialUpdatedCompito = new Compito();
        partialUpdatedCompito.setId(compito.getId());

        partialUpdatedCompito.data(UPDATED_DATA).materia(UPDATED_MATERIA);

        restCompitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompito))
            )
            .andExpect(status().isOk());

        // Validate the Compito in the database
        List<Compito> compitoList = compitoRepository.findAll();
        assertThat(compitoList).hasSize(databaseSizeBeforeUpdate);
        Compito testCompito = compitoList.get(compitoList.size() - 1);
        assertThat(testCompito.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testCompito.getMateria()).isEqualTo(UPDATED_MATERIA);
    }

    @Test
    @Transactional
    void patchNonExistingCompito() throws Exception {
        int databaseSizeBeforeUpdate = compitoRepository.findAll().size();
        compito.setId(count.incrementAndGet());

        // Create the Compito
        CompitoDTO compitoDTO = compitoMapper.toDto(compito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, compitoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compitoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Compito in the database
        List<Compito> compitoList = compitoRepository.findAll();
        assertThat(compitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompito() throws Exception {
        int databaseSizeBeforeUpdate = compitoRepository.findAll().size();
        compito.setId(count.incrementAndGet());

        // Create the Compito
        CompitoDTO compitoDTO = compitoMapper.toDto(compito);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compitoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Compito in the database
        List<Compito> compitoList = compitoRepository.findAll();
        assertThat(compitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompito() throws Exception {
        int databaseSizeBeforeUpdate = compitoRepository.findAll().size();
        compito.setId(count.incrementAndGet());

        // Create the Compito
        CompitoDTO compitoDTO = compitoMapper.toDto(compito);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompitoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(compitoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Compito in the database
        List<Compito> compitoList = compitoRepository.findAll();
        assertThat(compitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompito() throws Exception {
        // Initialize the database
        compitoRepository.saveAndFlush(compito);

        int databaseSizeBeforeDelete = compitoRepository.findAll().size();

        // Delete the compito
        restCompitoMockMvc
            .perform(delete(ENTITY_API_URL_ID, compito.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Compito> compitoList = compitoRepository.findAll();
        assertThat(compitoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
