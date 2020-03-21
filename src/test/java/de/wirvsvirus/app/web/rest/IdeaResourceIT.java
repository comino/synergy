package de.wirvsvirus.app.web.rest;

import de.wirvsvirus.app.WirvsvirusApp;
import de.wirvsvirus.app.domain.Idea;
import de.wirvsvirus.app.repository.IdeaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IdeaResource} REST controller.
 */
@SpringBootTest(classes = WirvsvirusApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class IdeaResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

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
    private IdeaRepository ideaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdeaMockMvc;

    private Idea idea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idea createEntity(EntityManager em) {
        Idea idea = new Idea()
            .title(DEFAULT_TITLE)
            .problems(DEFAULT_PROBLEMS)
            .description(DEFAULT_DESCRIPTION)
            .solution(DEFAULT_SOLUTION)
            .targetAudience(DEFAULT_TARGET_AUDIENCE)
            .stakeHolder(DEFAULT_STAKE_HOLDER)
            .slackChannel(DEFAULT_SLACK_CHANNEL)
            .ministryProject(DEFAULT_MINISTRY_PROJECT);
        return idea;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idea createUpdatedEntity(EntityManager em) {
        Idea idea = new Idea()
            .title(UPDATED_TITLE)
            .problems(UPDATED_PROBLEMS)
            .description(UPDATED_DESCRIPTION)
            .solution(UPDATED_SOLUTION)
            .targetAudience(UPDATED_TARGET_AUDIENCE)
            .stakeHolder(UPDATED_STAKE_HOLDER)
            .slackChannel(UPDATED_SLACK_CHANNEL)
            .ministryProject(UPDATED_MINISTRY_PROJECT);
        return idea;
    }

    @BeforeEach
    public void initTest() {
        idea = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdea() throws Exception {
        int databaseSizeBeforeCreate = ideaRepository.findAll().size();

        // Create the Idea
        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isCreated());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeCreate + 1);
        Idea testIdea = ideaList.get(ideaList.size() - 1);
        assertThat(testIdea.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testIdea.getProblems()).isEqualTo(DEFAULT_PROBLEMS);
        assertThat(testIdea.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIdea.getSolution()).isEqualTo(DEFAULT_SOLUTION);
        assertThat(testIdea.getTargetAudience()).isEqualTo(DEFAULT_TARGET_AUDIENCE);
        assertThat(testIdea.getStakeHolder()).isEqualTo(DEFAULT_STAKE_HOLDER);
        assertThat(testIdea.getSlackChannel()).isEqualTo(DEFAULT_SLACK_CHANNEL);
        assertThat(testIdea.isMinistryProject()).isEqualTo(DEFAULT_MINISTRY_PROJECT);
    }

    @Test
    @Transactional
    public void createIdeaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ideaRepository.findAll().size();

        // Create the Idea with an existing ID
        idea.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setTitle(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProblemsIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setProblems(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setDescription(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIdeas() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        // Get all the ideaList
        restIdeaMockMvc.perform(get("/api/ideas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idea.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].problems").value(hasItem(DEFAULT_PROBLEMS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].solution").value(hasItem(DEFAULT_SOLUTION)))
            .andExpect(jsonPath("$.[*].targetAudience").value(hasItem(DEFAULT_TARGET_AUDIENCE)))
            .andExpect(jsonPath("$.[*].stakeHolder").value(hasItem(DEFAULT_STAKE_HOLDER)))
            .andExpect(jsonPath("$.[*].slackChannel").value(hasItem(DEFAULT_SLACK_CHANNEL)))
            .andExpect(jsonPath("$.[*].ministryProject").value(hasItem(DEFAULT_MINISTRY_PROJECT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getIdea() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        // Get the idea
        restIdeaMockMvc.perform(get("/api/ideas/{id}", idea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(idea.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
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
    public void getNonExistingIdea() throws Exception {
        // Get the idea
        restIdeaMockMvc.perform(get("/api/ideas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdea() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

        // Update the idea
        Idea updatedIdea = ideaRepository.findById(idea.getId()).get();
        // Disconnect from session so that the updates on updatedIdea are not directly saved in db
        em.detach(updatedIdea);
        updatedIdea
            .title(UPDATED_TITLE)
            .problems(UPDATED_PROBLEMS)
            .description(UPDATED_DESCRIPTION)
            .solution(UPDATED_SOLUTION)
            .targetAudience(UPDATED_TARGET_AUDIENCE)
            .stakeHolder(UPDATED_STAKE_HOLDER)
            .slackChannel(UPDATED_SLACK_CHANNEL)
            .ministryProject(UPDATED_MINISTRY_PROJECT);

        restIdeaMockMvc.perform(put("/api/ideas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdea)))
            .andExpect(status().isOk());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
        Idea testIdea = ideaList.get(ideaList.size() - 1);
        assertThat(testIdea.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testIdea.getProblems()).isEqualTo(UPDATED_PROBLEMS);
        assertThat(testIdea.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIdea.getSolution()).isEqualTo(UPDATED_SOLUTION);
        assertThat(testIdea.getTargetAudience()).isEqualTo(UPDATED_TARGET_AUDIENCE);
        assertThat(testIdea.getStakeHolder()).isEqualTo(UPDATED_STAKE_HOLDER);
        assertThat(testIdea.getSlackChannel()).isEqualTo(UPDATED_SLACK_CHANNEL);
        assertThat(testIdea.isMinistryProject()).isEqualTo(UPDATED_MINISTRY_PROJECT);
    }

    @Test
    @Transactional
    public void updateNonExistingIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

        // Create the Idea

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdeaMockMvc.perform(put("/api/ideas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdea() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        int databaseSizeBeforeDelete = ideaRepository.findAll().size();

        // Delete the idea
        restIdeaMockMvc.perform(delete("/api/ideas/{id}", idea.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
