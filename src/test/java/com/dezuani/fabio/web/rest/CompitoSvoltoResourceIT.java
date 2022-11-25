package com.dezuani.fabio.web.rest;

import static com.dezuani.fabio.web.rest.AlunnoResourceIT.*;
import static com.dezuani.fabio.web.rest.CompitoResourceIT.DEFAULT_DATA;
import static com.dezuani.fabio.web.rest.CompitoResourceIT.DEFAULT_MATERIA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dezuani.fabio.IntegrationTest;
import com.dezuani.fabio.domain.Alunno;
import com.dezuani.fabio.domain.Compito;
import com.dezuani.fabio.domain.CompitoSvolto;
import com.dezuani.fabio.repository.AlunnoRepository;
import com.dezuani.fabio.repository.CompitoRepository;
import com.dezuani.fabio.repository.CompitoSvoltoRepository;
import com.dezuani.fabio.service.dto.CompitoSvoltoDTO;
import com.dezuani.fabio.service.mapper.CompitoSvoltoMapper;
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
 * Integration tests for the {@link CompitoSvoltoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompitoSvoltoResourceIT {

    private static final Double DEFAULT_VOTO = 1D;
    private static final Double UPDATED_VOTO = 2D;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/compiti-svolti";
    private static final String ENTITY_API_URL_ADD = "/api/compiti-svolti/{compitoId}/{alunnoId}";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompitoSvoltoRepository compitoSvoltoRepository;
    @Autowired
    private CompitoRepository compitoRepository;
    @Autowired
    private AlunnoRepository alunnoRepository;

    @Autowired
    private CompitoSvoltoMapper compitoSvoltoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompitoSvoltoMockMvc;

    private CompitoSvolto compitoSvolto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompitoSvolto createEntity(EntityManager em) {
        CompitoSvolto compitoSvolto = new CompitoSvolto().voto(DEFAULT_VOTO).note(DEFAULT_NOTE);
        return compitoSvolto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompitoSvolto createUpdatedEntity(EntityManager em) {
        CompitoSvolto compitoSvolto = new CompitoSvolto().voto(UPDATED_VOTO).note(UPDATED_NOTE);
        return compitoSvolto;
    }

    @BeforeEach
    public void initTest() {
        compitoSvolto = createEntity(em);
    }

    @Test
    @Transactional
    void createCompitoSvolto() throws Exception {
        int databaseSizeBeforeCreate = compitoSvoltoRepository.findAll().size();
        Compito compito = new Compito().data(DEFAULT_DATA).materia(DEFAULT_MATERIA);
        compito = compitoRepository.saveAndFlush(compito);

        Alunno alunno = new Alunno().nome(DEFAULT_NOME).cognome(DEFAULT_COGNOME).dataNascita(DEFAULT_DATA_NASCITA);
        alunno = alunnoRepository.saveAndFlush(alunno);

        // Create the CompitoSvolto
        CompitoSvoltoDTO compitoSvoltoDTO = compitoSvoltoMapper.toDto(compitoSvolto);
        restCompitoSvoltoMockMvc
            .perform(
                post(ENTITY_API_URL_ADD, compito.getId(),alunno.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compitoSvoltoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompitoSvolto in the database
        List<CompitoSvolto> compitoSvoltoList = compitoSvoltoRepository.findAll();
        assertThat(compitoSvoltoList).hasSize(databaseSizeBeforeCreate + 1);
        CompitoSvolto testCompitoSvolto = compitoSvoltoList.get(compitoSvoltoList.size() - 1);
        assertThat(testCompitoSvolto.getVoto()).isEqualTo(DEFAULT_VOTO);
        assertThat(testCompitoSvolto.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void getAllCompitoSvoltos() throws Exception {
        // Initialize the database
        compitoSvoltoRepository.saveAndFlush(compitoSvolto);

        // Get all the compitoSvoltoList
        restCompitoSvoltoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compitoSvolto.getId().intValue())))
            .andExpect(jsonPath("$.[*].voto").value(hasItem(DEFAULT_VOTO.doubleValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    @Transactional
    void getCompitoSvolto() throws Exception {
        // Initialize the database
        compitoSvoltoRepository.saveAndFlush(compitoSvolto);

        // Get the compitoSvolto
        restCompitoSvoltoMockMvc
            .perform(get(ENTITY_API_URL_ID, compitoSvolto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(compitoSvolto.getId().intValue()))
            .andExpect(jsonPath("$.voto").value(DEFAULT_VOTO.doubleValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    void getNonExistingCompitoSvolto() throws Exception {
        // Get the compitoSvolto
        restCompitoSvoltoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompitoSvolto() throws Exception {
        // Initialize the database
        compitoSvoltoRepository.saveAndFlush(compitoSvolto);

        int databaseSizeBeforeUpdate = compitoSvoltoRepository.findAll().size();

        // Update the compitoSvolto
        CompitoSvolto updatedCompitoSvolto = compitoSvoltoRepository.findById(compitoSvolto.getId()).get();
        // Disconnect from session so that the updates on updatedCompitoSvolto are not directly saved in db
        em.detach(updatedCompitoSvolto);
        updatedCompitoSvolto.voto(UPDATED_VOTO).note(UPDATED_NOTE);
        CompitoSvoltoDTO compitoSvoltoDTO = compitoSvoltoMapper.toDto(updatedCompitoSvolto);

        restCompitoSvoltoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, compitoSvoltoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compitoSvoltoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompitoSvolto in the database
        List<CompitoSvolto> compitoSvoltoList = compitoSvoltoRepository.findAll();
        assertThat(compitoSvoltoList).hasSize(databaseSizeBeforeUpdate);
        CompitoSvolto testCompitoSvolto = compitoSvoltoList.get(compitoSvoltoList.size() - 1);
        assertThat(testCompitoSvolto.getVoto()).isEqualTo(UPDATED_VOTO);
        assertThat(testCompitoSvolto.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingCompitoSvolto() throws Exception {
        int databaseSizeBeforeUpdate = compitoSvoltoRepository.findAll().size();
        compitoSvolto.setId(count.incrementAndGet());

        // Create the CompitoSvolto
        CompitoSvoltoDTO compitoSvoltoDTO = compitoSvoltoMapper.toDto(compitoSvolto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompitoSvoltoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, compitoSvoltoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compitoSvoltoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompitoSvolto in the database
        List<CompitoSvolto> compitoSvoltoList = compitoSvoltoRepository.findAll();
        assertThat(compitoSvoltoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompitoSvolto() throws Exception {
        int databaseSizeBeforeUpdate = compitoSvoltoRepository.findAll().size();
        compitoSvolto.setId(count.incrementAndGet());

        // Create the CompitoSvolto
        CompitoSvoltoDTO compitoSvoltoDTO = compitoSvoltoMapper.toDto(compitoSvolto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompitoSvoltoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compitoSvoltoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompitoSvolto in the database
        List<CompitoSvolto> compitoSvoltoList = compitoSvoltoRepository.findAll();
        assertThat(compitoSvoltoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompitoSvolto() throws Exception {
        int databaseSizeBeforeUpdate = compitoSvoltoRepository.findAll().size();
        compitoSvolto.setId(count.incrementAndGet());

        // Create the CompitoSvolto
        CompitoSvoltoDTO compitoSvoltoDTO = compitoSvoltoMapper.toDto(compitoSvolto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompitoSvoltoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compitoSvoltoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompitoSvolto in the database
        List<CompitoSvolto> compitoSvoltoList = compitoSvoltoRepository.findAll();
        assertThat(compitoSvoltoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompitoSvoltoWithPatch() throws Exception {
        // Initialize the database
        compitoSvoltoRepository.saveAndFlush(compitoSvolto);

        int databaseSizeBeforeUpdate = compitoSvoltoRepository.findAll().size();

        // Update the compitoSvolto using partial update
        CompitoSvolto partialUpdatedCompitoSvolto = new CompitoSvolto();
        partialUpdatedCompitoSvolto.setId(compitoSvolto.getId());

        partialUpdatedCompitoSvolto.note(UPDATED_NOTE);

        restCompitoSvoltoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompitoSvolto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompitoSvolto))
            )
            .andExpect(status().isOk());

        // Validate the CompitoSvolto in the database
        List<CompitoSvolto> compitoSvoltoList = compitoSvoltoRepository.findAll();
        assertThat(compitoSvoltoList).hasSize(databaseSizeBeforeUpdate);
        CompitoSvolto testCompitoSvolto = compitoSvoltoList.get(compitoSvoltoList.size() - 1);
        assertThat(testCompitoSvolto.getVoto()).isEqualTo(DEFAULT_VOTO);
        assertThat(testCompitoSvolto.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateCompitoSvoltoWithPatch() throws Exception {
        // Initialize the database
        compitoSvoltoRepository.saveAndFlush(compitoSvolto);

        int databaseSizeBeforeUpdate = compitoSvoltoRepository.findAll().size();

        // Update the compitoSvolto using partial update
        CompitoSvolto partialUpdatedCompitoSvolto = new CompitoSvolto();
        partialUpdatedCompitoSvolto.setId(compitoSvolto.getId());

        partialUpdatedCompitoSvolto.voto(UPDATED_VOTO).note(UPDATED_NOTE);

        restCompitoSvoltoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompitoSvolto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompitoSvolto))
            )
            .andExpect(status().isOk());

        // Validate the CompitoSvolto in the database
        List<CompitoSvolto> compitoSvoltoList = compitoSvoltoRepository.findAll();
        assertThat(compitoSvoltoList).hasSize(databaseSizeBeforeUpdate);
        CompitoSvolto testCompitoSvolto = compitoSvoltoList.get(compitoSvoltoList.size() - 1);
        assertThat(testCompitoSvolto.getVoto()).isEqualTo(UPDATED_VOTO);
        assertThat(testCompitoSvolto.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingCompitoSvolto() throws Exception {
        int databaseSizeBeforeUpdate = compitoSvoltoRepository.findAll().size();
        compitoSvolto.setId(count.incrementAndGet());

        // Create the CompitoSvolto
        CompitoSvoltoDTO compitoSvoltoDTO = compitoSvoltoMapper.toDto(compitoSvolto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompitoSvoltoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, compitoSvoltoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compitoSvoltoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompitoSvolto in the database
        List<CompitoSvolto> compitoSvoltoList = compitoSvoltoRepository.findAll();
        assertThat(compitoSvoltoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompitoSvolto() throws Exception {
        int databaseSizeBeforeUpdate = compitoSvoltoRepository.findAll().size();
        compitoSvolto.setId(count.incrementAndGet());

        // Create the CompitoSvolto
        CompitoSvoltoDTO compitoSvoltoDTO = compitoSvoltoMapper.toDto(compitoSvolto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompitoSvoltoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compitoSvoltoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompitoSvolto in the database
        List<CompitoSvolto> compitoSvoltoList = compitoSvoltoRepository.findAll();
        assertThat(compitoSvoltoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompitoSvolto() throws Exception {
        int databaseSizeBeforeUpdate = compitoSvoltoRepository.findAll().size();
        compitoSvolto.setId(count.incrementAndGet());

        // Create the CompitoSvolto
        CompitoSvoltoDTO compitoSvoltoDTO = compitoSvoltoMapper.toDto(compitoSvolto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompitoSvoltoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compitoSvoltoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompitoSvolto in the database
        List<CompitoSvolto> compitoSvoltoList = compitoSvoltoRepository.findAll();
        assertThat(compitoSvoltoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompitoSvolto() throws Exception {
        // Initialize the database
        compitoSvoltoRepository.saveAndFlush(compitoSvolto);

        int databaseSizeBeforeDelete = compitoSvoltoRepository.findAll().size();

        // Delete the compitoSvolto
        restCompitoSvoltoMockMvc
            .perform(delete(ENTITY_API_URL_ID, compitoSvolto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompitoSvolto> compitoSvoltoList = compitoSvoltoRepository.findAll();
        assertThat(compitoSvoltoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
