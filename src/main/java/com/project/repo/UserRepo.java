package com.project.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.model.User;

public interface UserRepo extends MongoRepository<User,String> {
	Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
