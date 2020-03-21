package de.wirvsvirus.app.repository;

import de.wirvsvirus.app.domain.Project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Project entity.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "select distinct project from Project project left join fetch project.users left join fetch project.skills left join fetch project.tasks",
        countQuery = "select count(distinct project) from Project project")
    Page<Project> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct project from Project project left join fetch project.users left join fetch project.skills left join fetch project.tasks")
    List<Project> findAllWithEagerRelationships();

    @Query("select project from Project project left join fetch project.users left join fetch project.skills left join fetch project.tasks where project.id =:id")
    Optional<Project> findOneWithEagerRelationships(@Param("id") Long id);
}
