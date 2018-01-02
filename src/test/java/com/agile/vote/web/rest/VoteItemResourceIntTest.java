package com.agile.vote.web.rest;

import com.agile.vote.VoteApp;

import com.agile.vote.domain.VoteItem;
import com.agile.vote.repository.VoteItemRepository;
import com.agile.vote.service.VoteItemService;
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
import java.util.List;

import static com.agile.vote.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VoteItemResource REST controller.
 *
 * @see VoteItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoteApp.class)
public class VoteItemResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VOTE_COUNT = 1;
    private static final Integer UPDATED_VOTE_COUNT = 2;

    @Autowired
    private VoteItemRepository voteItemRepository;

    @Autowired
    private VoteItemService voteItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVoteItemMockMvc;

    private VoteItem voteItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoteItemResource voteItemResource = new VoteItemResource(voteItemService);
        this.restVoteItemMockMvc = MockMvcBuilders.standaloneSetup(voteItemResource)
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
    public static VoteItem createEntity(EntityManager em) {
        VoteItem voteItem = new VoteItem()
            .title(DEFAULT_TITLE)
            .voteCount(DEFAULT_VOTE_COUNT);
        return voteItem;
    }

    @Before
    public void initTest() {
        voteItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoteItem() throws Exception {
        int databaseSizeBeforeCreate = voteItemRepository.findAll().size();

        // Create the VoteItem
        restVoteItemMockMvc.perform(post("/api/vote-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voteItem)))
            .andExpect(status().isCreated());

        // Validate the VoteItem in the database
        List<VoteItem> voteItemList = voteItemRepository.findAll();
        assertThat(voteItemList).hasSize(databaseSizeBeforeCreate + 1);
        VoteItem testVoteItem = voteItemList.get(voteItemList.size() - 1);
        assertThat(testVoteItem.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testVoteItem.getVoteCount()).isEqualTo(DEFAULT_VOTE_COUNT);
    }

    @Test
    @Transactional
    public void createVoteItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voteItemRepository.findAll().size();

        // Create the VoteItem with an existing ID
        voteItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoteItemMockMvc.perform(post("/api/vote-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voteItem)))
            .andExpect(status().isBadRequest());

        // Validate the VoteItem in the database
        List<VoteItem> voteItemList = voteItemRepository.findAll();
        assertThat(voteItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteItemRepository.findAll().size();
        // set the field null
        voteItem.setTitle(null);

        // Create the VoteItem, which fails.

        restVoteItemMockMvc.perform(post("/api/vote-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voteItem)))
            .andExpect(status().isBadRequest());

        List<VoteItem> voteItemList = voteItemRepository.findAll();
        assertThat(voteItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoteItems() throws Exception {
        // Initialize the database
        voteItemRepository.saveAndFlush(voteItem);

        // Get all the voteItemList
        restVoteItemMockMvc.perform(get("/api/vote-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voteItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].voteCount").value(hasItem(DEFAULT_VOTE_COUNT)));
    }

    @Test
    @Transactional
    public void getVoteItem() throws Exception {
        // Initialize the database
        voteItemRepository.saveAndFlush(voteItem);

        // Get the voteItem
        restVoteItemMockMvc.perform(get("/api/vote-items/{id}", voteItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voteItem.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.voteCount").value(DEFAULT_VOTE_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingVoteItem() throws Exception {
        // Get the voteItem
        restVoteItemMockMvc.perform(get("/api/vote-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoteItem() throws Exception {
        // Initialize the database
        voteItemService.save(voteItem);

        int databaseSizeBeforeUpdate = voteItemRepository.findAll().size();

        // Update the voteItem
        VoteItem updatedVoteItem = voteItemRepository.findOne(voteItem.getId());
        // Disconnect from session so that the updates on updatedVoteItem are not directly saved in db
        em.detach(updatedVoteItem);
        updatedVoteItem
            .title(UPDATED_TITLE)
            .voteCount(UPDATED_VOTE_COUNT);

        restVoteItemMockMvc.perform(put("/api/vote-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVoteItem)))
            .andExpect(status().isOk());

        // Validate the VoteItem in the database
        List<VoteItem> voteItemList = voteItemRepository.findAll();
        assertThat(voteItemList).hasSize(databaseSizeBeforeUpdate);
        VoteItem testVoteItem = voteItemList.get(voteItemList.size() - 1);
        assertThat(testVoteItem.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testVoteItem.getVoteCount()).isEqualTo(UPDATED_VOTE_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingVoteItem() throws Exception {
        int databaseSizeBeforeUpdate = voteItemRepository.findAll().size();

        // Create the VoteItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVoteItemMockMvc.perform(put("/api/vote-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voteItem)))
            .andExpect(status().isCreated());

        // Validate the VoteItem in the database
        List<VoteItem> voteItemList = voteItemRepository.findAll();
        assertThat(voteItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVoteItem() throws Exception {
        // Initialize the database
        voteItemService.save(voteItem);

        int databaseSizeBeforeDelete = voteItemRepository.findAll().size();

        // Get the voteItem
        restVoteItemMockMvc.perform(delete("/api/vote-items/{id}", voteItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VoteItem> voteItemList = voteItemRepository.findAll();
        assertThat(voteItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoteItem.class);
        VoteItem voteItem1 = new VoteItem();
        voteItem1.setId(1L);
        VoteItem voteItem2 = new VoteItem();
        voteItem2.setId(voteItem1.getId());
        assertThat(voteItem1).isEqualTo(voteItem2);
        voteItem2.setId(2L);
        assertThat(voteItem1).isNotEqualTo(voteItem2);
        voteItem1.setId(null);
        assertThat(voteItem1).isNotEqualTo(voteItem2);
    }
}
