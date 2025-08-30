package com.project.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.model.Messages;

public interface MessageRepo extends MongoRepository<Messages,String>{
	
}
