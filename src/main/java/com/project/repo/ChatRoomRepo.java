package com.project.repo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.project.model.ChatRoom;

public interface ChatRoomRepo extends MongoRepository<ChatRoom,String>{
	Optional<ChatRoom> findByName(String name);
	@Query("{ 'members.?0': { $exists: true } }")
	List<ChatRoom> findByMemberEmailsContaining(String email);
	
}
