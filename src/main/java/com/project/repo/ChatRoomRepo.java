package com.project.repo;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.model.ChatRoom;

public interface ChatRoomRepo extends MongoRepository<ChatRoom,String>{
	Optional<ChatRoom> findByName(String name);
	
}
