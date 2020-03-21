package de.wirvsvirus.app.web.rest;

import de.wirvsvirus.app.WirvsvirusApp;
import de.wirvsvirus.app.domain.Challenge;
import de.wirvsvirus.app.repository.ChallengeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ChallengeResource} REST controller.
 */
@SpringBootTest(classes = WirvsvirusApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChallengeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROBLEMS = "AAAAAAAAAA";
    private static final String UPDATED_PROBLEMS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_SOLUTION = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_AUDIENCE = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_AUDIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_STAKE_HOLDER = "AAAAAAAAAA";
    private static final String UPDATED_STAKE_HOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_SLACK_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_SLACK_CHANNEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MINISTRY_PROJECT = false;
    private static final Boolean UPDATED_MINISTRY_PROJECT = true;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Mock
    private ChallengeRepository challengeRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChallengeMockMvc;

    private Challenge challenge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Challenge createEntity(EntityManager em) {
        Challenge challenge = new Challenge()
            .name(DEFAULT_NAME)
            .problems(DEFAULT_PROBLEMS)
            .description(DEFAULT_DESCRIPTION)
            .solution(DEFAULT_SOLUTION)
            .targetAudience(DEFAULT_TARGET_AUDIENCE)
            .stakeHolder(DEFAULT_STAKE_HOLDER)
            .slackChannel(DEFAULT_SLACK_CHANNEL)
            .ministryProject(DEFAULT_MINISTRY_PROJECT);
        return challenge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Challenge createUpdatedEntity(EntityManager em) {
        Challenge challenge = new Challenge()
            .name(UPDATED_NAME)
            .problems(UPDATED_PROBLEMS)
            .description(UPDATED_DESCRIPTION)
            .solution(UPDATED_SOLUTION)
            .targetAudience(UPDATED_TARGET_AUDIENCE)
            .stakeHolder(UPDATED_STAKE_HOLDER)
            .slackChannel(UPDATED_SLACK_CHANNEL)
            .ministryProject(UPDATED_MINISTRY_PROJECT);
        return challenge;
    }

    @BeforeEach
    public void initTest() {
        challenge = createEntity(em);
    }

    @Test
    @Transactional
    public void createChallenge() throws Exception {
        int databaseSizeBeforeCreate = challengeRepository.findAll().size();

        // Create the Challenge
        restChallengeMockMvc.perform(post("/api/challenges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isCreated());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeCreate + 1);
        Challenge testChallenge = challengeList.get(challengeList.size() - 1);
        assertThat(testChallenge.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChallenge.getProblems()).isEqualTo(DEFAULT_PROBLEMS);
        assertThat(testChallenge.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testChallenge.getSolution()).isEqualTo(DEFAULT_SOLUTION);
        assertThat(testChallenge.getTargetAudience()).isEqualTo(DEFAULT_TARGET_AUDIENCE);
        assertThat(testChallenge.getStakeHolder()).isEqualTo(DEFAULT_STAKE_HOLDER);
        assertThat(testChallenge.getSlackChannel()).isEqualTo(DEFAULT_SLACK_CHANNEL);
        assertThat(testChallenge.isMinistryProject()).isEqualTo(DEFAULT_MINISTRY_PROJECT);
    }

    @Test
    @Transactional
    public void createChallengeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = challengeRepository.findAll().size();

        // Create the Challenge with an existing ID
        challenge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChallengeMockMvc.perform(post("/api/challenges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isBadRequest());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = challengeRepository.findAll().size();
        // set the field null
        challenge.setName(null);

        // Create the Challenge, which fails.

        restChallengeMockMvc.perform(post("/api/challenges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isBadRequest());

        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProblemsIsRequired() throws Exception {
        int databaseSizeBeforeTest = challengeRepository.findAll().size();
        // set the field null
        challenge.setProblems(null);

        // Create the Challenge, which fails.

        restChallengeMockMvc.perform(post("/api/challenges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isBadRequest());

        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = challengeRepository.findAll().size();
        // set the field null
        challenge.setDescription(null);

        // Create the Challenge, which fails.

        restChallengeMockMvc.perform(post("/api/challenges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isBadRequest());

        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChallenges() throws Exception {
        // Initialize the database
        challengeRepository.saveAndFlush(challenge);

        // Get all the challengeList
        restChallengeMockMvc.perform(get("/api/challenges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(challenge.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].problems").value(hasItem(DEFAULT_PROBLEMS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].solution").value(hasItem(DEFAULT_SOLUTION)))
            .andExpect(jsonPath("$.[*].targetAudience").value(hasItem(DEFAULT_TARGET_AUDIENCE)))
            .andExpect(jsonPath("$.[*].stakeHolder").value(hasItem(DEFAULT_STAKE_HOLDER)))
            .andExpect(jsonPath("$.[*].slackChannel").value(hasItem(DEFAULT_SLACK_CHANNEL)))
            .andExpect(jsonPath("$.[*].ministryProject").value(hasItem(DEFAULT_MINISTRY_PROJECT.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllChallengesWithEagerRelationshipsIsEnabled() throws Exception {
        ChallengeResource challengeResource = new ChallengeResource(challengeRepositoryMock);
        when(challengeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChallengeMockMvc.perform(get("/api/challenges?eagerload=true"))
            .andExpect(status().isOk());

        verify(challengeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllChallengesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ChallengeResource challengeResource = new ChallengeResource(challengeRepositoryMock);
        when(challengeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChallengeMockMvc.perform(get("/api/challenges?eagerload=true"))
            .andExpect(status().isOk());

        verify(challengeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getChallenge() throws Exception {
        // Initialize the database
        challengeRepository.saveAndFlush(challenge);

        // Get the challenge
        restChallengeMockMvc.perform(get("/api/challenges/{id}", challenge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(challenge.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.problems").value(DEFAULT_PROBLEMS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.solution").value(DEFAULT_SOLUTION))
            .andExpect(jsonPath("$.targetAudience").value(DEFAULT_TARGET_AUDIENCE))
            .andExpect(jsonPath("$.stakeHolder").value(DEFAULT_STAKE_HOLDER))
            .andExpect(jsonPath("$.slackChannel").value(DEFAULT_SLACK_CHANNEL))
            .andExpect(jsonPath("$.ministryProject").value(DEFAULT_MINISTRY_PROJECT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingChallenge() throws Exception {
        // Get the challenge
        restChallengeMockMvc.perform(get("/api/challenges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChallenge() throws Exception {
        // Initialize the database
        challengeRepository.saveAndFlush(challenge);

        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();

        // Update the challenge
        Challenge updatedChallenge = challengeRepository.findById(challenge.getId()).get();
        // Disconnect from session so that the updates on updatedChallenge are not directly saved in db
        em.detach(updatedChallenge);
        updatedChallenge
            .name(UPDATED_NAME)
            .problems(UPDATED_PROBLEMS)
            .description(UPDATED_DESCRIPTION)
            .solution(UPDATED_SOLUTION)
            .targetAudience(UPDATED_TARGET_AUDIENCE)
            .stakeHolder(UPDATED_STAKE_HOLDER)
            .slackChannel(UPDATED_SLACK_CHANNEL)
            .ministryProject(UPDATED_MINISTRY_PROJECT);

        restChallengeMockMvc.perform(put("/api/challenges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedChallenge)))
            .andExpect(status().isOk());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
        Challenge testChallenge = challengeList.get(challengeList.size() - 1);
        assertThat(testChallenge.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChallenge.getProblems()).isEqualTo(UPDATED_PROBLEMS);
        assertThat(testChallenge.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testChallenge.getSolution()).isEqualTo(UPDATED_SOLUTION);
        assertThat(testChallenge.getTargetAudience()).isEqualTo(UPDATED_TARGET_AUDIENCE);
        assertThat(testChallenge.getStakeHolder()).isEqualTo(UPDATED_STAKE_HOLDER);
        assertThat(testChallenge.getSlackChannel()).isEqualTo(UPDATED_SLACK_CHANNEL);
        assertThat(testChallenge.isMinistryProject()).isEqualTo(UPDATED_MINISTRY_PROJECT);
    }

    @Test
    @Transactional
    public void updateNonExistingChallenge() throws Exception {
        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();

        // Create the Challenge

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChallengeMockMvc.perform(put("/api/challenges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isBadRequest());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChallenge() throws Exception {
        // Initialize the database
        challengeRepository.saveAndFlush(challenge);

        int databaseSizeBeforeDelete = challengeRepository.findAll().size();

        // Delete the challenge
        restChallengeMockMvc.perform(delete("/api/challenges/{id}", challenge.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
