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

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
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
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
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
        assertThat(testIdea.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIdea.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
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
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setName(null);

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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
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
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restIdeaMockMvc.perform(put("/api/ideas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdea)))
            .andExpect(status().isOk());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
        Idea testIdea = ideaList.get(ideaList.size() - 1);
        assertThat(testIdea.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIdea.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
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
