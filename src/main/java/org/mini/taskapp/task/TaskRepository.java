package org.mini.taskapp.task;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task,String> {
    Optional<Task> findByTitle(String title);
    
    // Find task by ID and userId - for ownership validation
    Optional<Task> findByIdAndUserId(String id, String userId);
    
    // Find all tasks for a specific user
    List<Task> findByUserId(String userId);
    
    // Delete task by ID and userId - ensures ownership
    void deleteByIdAndUserId(String id, String userId);
}
