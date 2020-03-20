package de.wirvsvirus.app.repository;

import de.wirvsvirus.app.domain.Idea;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Idea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
}
