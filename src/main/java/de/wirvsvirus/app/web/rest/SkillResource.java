package de.wirvsvirus.app.web.rest;

import de.wirvsvirus.app.domain.Skill;
import de.wirvsvirus.app.repository.SkillRepository;
import de.wirvsvirus.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.wirvsvirus.app.domain.Skill}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SkillResource {

    private final Logger log = LoggerFactory.getLogger(SkillResource.class);

    private static final String ENTITY_NAME = "skill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillRepository skillRepository;

    public SkillResource(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    /**
     * {@code POST  /skills} : Create a new skill.
     *
     * @param skill the skill to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skill, or with status {@code 400 (Bad Request)} if the skill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skills")
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody Skill skill) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skill);
        if (skill.getId() != null) {
            throw new BadRequestAlertException("A new skill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Skill result = skillRepository.save(skill);
        return ResponseEntity.created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skills} : Updates an existing skill.
     *
     * @param skill the skill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skill,
     * or with status {@code 400 (Bad Request)} if the skill is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skills")
    public ResponseEntity<Skill> updateSkill(@Valid @RequestBody Skill skill) throws URISyntaxException {
        log.debug("REST request to update Skill : {}", skill);
        if (skill.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Skill result = skillRepository.save(skill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skill.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skills} : get all the skills.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skills in body.
     */
    @GetMapping("/skills")
    public List<Skill> getAllSkills() {
        log.debug("REST request to get all Skills");
        return skillRepository.findAll();
    }

    /**
     * {@code GET  /skills/:id} : get the "id" skill.
     *
     * @param id the id of the skill to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skill, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skills/{id}")
    public ResponseEntity<Skill> getSkill(@PathVariable Long id) {
        log.debug("REST request to get Skill : {}", id);
        Optional<Skill> skill = skillRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(skill);
    }

    /**
     * {@code DELETE  /skills/:id} : delete the "id" skill.
     *
     * @param id the id of the skill to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.debug("REST request to delete Skill : {}", id);
        skillRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
