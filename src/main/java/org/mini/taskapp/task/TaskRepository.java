package org.mini.taskapp.task;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task,String> {
    Optional<Task>  findByTitle(String title);


}
