package com.agile.vote.web.rest;

import com.agile.vote.VoteApp;

import com.agile.vote.domain.VoteResult;
import com.agile.vote.repository.VoteResultRepository;
import com.agile.vote.service.VoteResultService;
import com.agile.vote.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.agile.vote.web.rest.TestUtil.sameInstant;
import static com.agile.vote.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VoteResultResource REST controller.
 *
 * @see VoteResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoteApp.class)
public class VoteResultResourceIntTest {

    private static final ZonedDateTime DEFAULT_VOTE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VOTE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_OPENID = "AAAAAAAAAA";
    private static final String UPDATED_OPENID = "BBBBBBBBBB";

    @Autowired
    private VoteResultRepository voteResultRepository;

    @Autowired
    private VoteResultService voteResultService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVoteResultMockMvc;

    private VoteResult voteResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoteResultResource voteResultResource = new VoteResultResource(voteResultService);
        this.restVoteResultMockMvc = MockMvcBuilders.standaloneSetup(voteResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoteResult createEntity(EntityManager em) {
        VoteResult voteResult = new VoteResult()
            .voteTime(DEFAULT_VOTE_TIME)
            .openid(DEFAULT_OPENID);
        return voteResult;
    }

    @Before
    public void initTest() {
        voteResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoteResult() throws Exception {
        int databaseSizeBeforeCreate = voteResultRepository.findAll().size();

        // Create the VoteResult
        restVoteResultMockMvc.perform(post("/api/vote-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voteResult)))
            .andExpect(status().isCreated());

        // Validate the VoteResult in the database
        List<VoteResult> voteResultList = voteResultRepository.findAll();
        assertThat(voteResultList).hasSize(databaseSizeBeforeCreate + 1);
        VoteResult testVoteResult = voteResultList.get(voteResultList.size() - 1);
        assertThat(testVoteResult.getVoteTime()).isEqualTo(DEFAULT_VOTE_TIME);
        assertThat(testVoteResult.getOpenid()).isEqualTo(DEFAULT_OPENID);
    }

    @Test
    @Transactional
    public void createVoteResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voteResultRepository.findAll().size();

        // Create the VoteResult with an existing ID
        voteResult.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoteResultMockMvc.perform(post("/api/vote-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voteResult)))
            .andExpect(status().isBadRequest());

        // Validate the VoteResult in the database
        List<VoteResult> voteResultList = voteResultRepository.findAll();
        assertThat(voteResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkVoteTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteResultRepository.findAll().size();
        // set the field null
        voteResult.setVoteTime(null);

        // Create the VoteResult, which fails.

        restVoteResultMockMvc.perform(post("/api/vote-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voteResult)))
            .andExpect(status().isBadRequest());

        List<VoteResult> voteResultList = voteResultRepository.findAll();
        assertThat(voteResultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpenidIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteResultRepository.findAll().size();
        // set the field null
        voteResult.setOpenid(null);

        // Create the VoteResult, which fails.

        restVoteResultMockMvc.perform(post("/api/vote-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voteResult)))
            .andExpect(status().isBadRequest());

        List<VoteResult> voteResultList = voteResultRepository.findAll();
        assertThat(voteResultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoteResults() throws Exception {
        // Initialize the database
        voteResultRepository.saveAndFlush(voteResult);

        // Get all the voteResultList
        restVoteResultMockMvc.perform(get("/api/vote-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voteResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].voteTime").value(hasItem(sameInstant(DEFAULT_VOTE_TIME))))
            .andExpect(jsonPath("$.[*].openid").value(hasItem(DEFAULT_OPENID.toString())));
    }

    @Test
    @Transactional
    public void getVoteResult() throws Exception {
        // Initialize the database
        voteResultRepository.saveAndFlush(voteResult);

        // Get the voteResult
        restVoteResultMockMvc.perform(get("/api/vote-results/{id}", voteResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voteResult.getId().intValue()))
            .andExpect(jsonPath("$.voteTime").value(sameInstant(DEFAULT_VOTE_TIME)))
            .andExpect(jsonPath("$.openid").value(DEFAULT_OPENID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoteResult() throws Exception {
        // Get the voteResult
        restVoteResultMockMvc.perform(get("/api/vote-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoteResult() throws Exception {
        // Initialize the database
        voteResultService.save(voteResult);

        int databaseSizeBeforeUpdate = voteResultRepository.findAll().size();

        // Update the voteResult
        VoteResult updatedVoteResult = voteResultRepository.findOne(voteResult.getId());
        // Disconnect from session so that the updates on updatedVoteResult are not directly saved in db
        em.detach(updatedVoteResult);
        updatedVoteResult
            .voteTime(UPDATED_VOTE_TIME)
            .openid(UPDATED_OPENID);

        restVoteResultMockMvc.perform(put("/api/vote-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVoteResult)))
            .andExpect(status().isOk());

        // Validate the VoteResult in the database
        List<VoteResult> voteResultList = voteResultRepository.findAll();
        assertThat(voteResultList).hasSize(databaseSizeBeforeUpdate);
        VoteResult testVoteResult = voteResultList.get(voteResultList.size() - 1);
        assertThat(testVoteResult.getVoteTime()).isEqualTo(UPDATED_VOTE_TIME);
        assertThat(testVoteResult.getOpenid()).isEqualTo(UPDATED_OPENID);
    }

    @Test
    @Transactional
    public void updateNonExistingVoteResult() throws Exception {
        int databaseSizeBeforeUpdate = voteResultRepository.findAll().size();

        // Create the VoteResult

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVoteResultMockMvc.perform(put("/api/vote-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voteResult)))
            .andExpect(status().isCreated());

        // Validate the VoteResult in the database
        List<VoteResult> voteResultList = voteResultRepository.findAll();
        assertThat(voteResultList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVoteResult() throws Exception {
        // Initialize the database
        voteResultService.save(voteResult);

        int databaseSizeBeforeDelete = voteResultRepository.findAll().size();

        // Get the voteResult
        restVoteResultMockMvc.perform(delete("/api/vote-results/{id}", voteResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VoteResult> voteResultList = voteResultRepository.findAll();
        assertThat(voteResultList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoteResult.class);
        VoteResult voteResult1 = new VoteResult();
        voteResult1.setId(1L);
        VoteResult voteResult2 = new VoteResult();
        voteResult2.setId(voteResult1.getId());
        assertThat(voteResult1).isEqualTo(voteResult2);
        voteResult2.setId(2L);
        assertThat(voteResult1).isNotEqualTo(voteResult2);
        voteResult1.setId(null);
        assertThat(voteResult1).isNotEqualTo(voteResult2);
    }
}
