package de.wirvsvirus.app.repository;

import de.wirvsvirus.app.domain.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Task entity.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "select distinct task from Task task left join fetch task.skills",
        countQuery = "select count(distinct task) from Task task")
    Page<Task> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct task from Task task left join fetch task.skills")
    List<Task> findAllWithEagerRelationships();

    @Query("select task from Task task left join fetch task.skills where task.id =:id")
    Optional<Task> findOneWithEagerRelationships(@Param("id") Long id);
}
