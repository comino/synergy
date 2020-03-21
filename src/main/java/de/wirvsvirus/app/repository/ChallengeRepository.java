package de.wirvsvirus.app.repository;

import de.wirvsvirus.app.domain.Challenge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Challenge entity.
 */
@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query(value = "select distinct challenge from Challenge challenge left join fetch challenge.categories",
        countQuery = "select count(distinct challenge) from Challenge challenge")
    Page<Challenge> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct challenge from Challenge challenge left join fetch challenge.categories")
    List<Challenge> findAllWithEagerRelationships();

    @Query("select challenge from Challenge challenge left join fetch challenge.categories where challenge.id =:id")
    Optional<Challenge> findOneWithEagerRelationships(@Param("id") Long id);
}
