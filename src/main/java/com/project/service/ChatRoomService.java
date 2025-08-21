package com.project.service;

import java.util.Optional;

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
		Optional<ChatRoom> existingRoom = chatRoomRepository.findByName(roomReq.getName());
        if (existingRoom.isPresent()) {
        	ChatRoom room=existingRoom.get();
            System.out.println("Room already exists");
            return room;
        }
        return createRoom(roomReq);
		
		
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
	
	
	
}
