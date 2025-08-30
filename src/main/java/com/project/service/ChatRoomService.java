package com.project.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.project.model.ChatRoom;
import com.project.repo.ChatRoomRepo;

@Service
public class ChatRoomService {
	  private final ChatRoomRepo chatRoomRepository;
	  
	  public ChatRoomService(ChatRoomRepo chatRoomRepository) {
	        this.chatRoomRepository = chatRoomRepository;
	    }
	public ChatRoom joinRoom(ChatRoom roomReq) throws Exception {
		// TODO Auto-generated method stub
		
		if (roomReq.getMembers().size() == 2) {
            String uniqueName = generatePrivateRoomName(roomReq.getMembers());
            Optional<ChatRoom> existingRoom = chatRoomRepository.findByName(uniqueName);
            if (existingRoom.isPresent()) {
//            	System.out.println("Room presnt"+existingRoom.get());
            	return existingRoom.get();
            }
            roomReq.setName(uniqueName);
            return createRoom(roomReq);
        }
		Optional<ChatRoom> existingRoom = chatRoomRepository.findByName(roomReq.getName());
        return existingRoom.orElseGet(() -> createRoom(roomReq));
        
		
		
	}
	public ChatRoom createRoom(ChatRoom roomReq) {
		boolean isGroup=roomReq.getMembers().size()>2?true:false;
		System.out.println(isGroup);
		ChatRoom room=new ChatRoom(roomReq.getName(),isGroup,roomReq.getMembers());
		
		try {
			return chatRoomRepository.save(room);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        
	}
	 private String generatePrivateRoomName(Set<String> members) {
	        return members.stream()
	                      .sorted()
	                      .reduce((a, b) -> a + "_" + b)
	                      .orElseThrow(() -> new IllegalArgumentException("Invalid members"));
	    }
	
	
	
}
