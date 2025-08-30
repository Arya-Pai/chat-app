package com.project.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.model.Messages;

public interface MessageRepo extends MongoRepository<Messages,String>{
	List<Messages> findByRoomId(String roomId);
}
