package com.company.CompanyApp.repository;

import com.company.CompanyApp.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findTaskByName(String name);

    Optional<Task> findTaskById(Long id);

    Page<Task> findAllByConstructionId(Long constructionId, Pageable pageable);

    @Query("SELECT t FROM Task t JOIN t.customers c WHERE c.username = :username AND t.isDone = :done")
    Page<Task> findTasksByCustomerUsernameAndIsDone(@Param("username") String username,
                                                    @Param("done") boolean done,
                                                    Pageable pageable);


}
