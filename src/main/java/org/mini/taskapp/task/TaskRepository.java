package org.mini.taskapp.task;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task,String> {
    Optional<Task>  findByTitle(String title);
    // NEW - Find task by ID AND userId (for ownership check)
    Optional<Task> findByIdAndUserId(String id, String userId);

    // NEW - Get all tasks for a specific user
    List<Task> findByUserId(String userId);
    List<Task> findAllByUserId(String userId);
    // NEW - Delete task only if user owns it
    void deleteByIdAndUserId(String id, String userId);

}
