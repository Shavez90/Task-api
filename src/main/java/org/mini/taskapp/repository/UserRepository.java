package org.mini.taskapp.repository;

import org.mini.taskapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String > {


    Optional<User>  findByUsername(String username);// OPTIONAL  Is giving repo choice to
    // either return  the thing we want oe return null is also safe choice
    boolean existsByUsername(String username);// check if this username exist or not


}
