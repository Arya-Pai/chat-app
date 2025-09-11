package com.project.service;

import java.util.HashMap;
import java.util.List;
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
		 HashMap<String, String> encodedMembers = new HashMap<>();
		    roomReq.getMembers().forEach((key, value) -> {
		        encodedMembers.put(ChatRoom.encodeEmail(key), value);
		    });
		    roomReq.setMembers(encodedMembers);
		if (roomReq.getMembers().size() == 2) {
            String uniqueName = generatePrivateRoomName(roomReq.getMembers());
            Optional<ChatRoom> existingRoom = chatRoomRepository.findByName(uniqueName);
            if (existingRoom.isPresent()) {
//            	System.out.println("Room presnt"+existingRoom.get());
            	return existingRoom.get();
            }
            roomReq.setName(uniqueName);
            return createRoom(roomReq);
        }else if(roomReq.getMembers().size() > 2) {
        	 Optional<ChatRoom> existingRoom = chatRoomRepository.findByName(roomReq.getName());
        	 if (existingRoom.isPresent()) {
//             	System.out.println("Room presnt"+existingRoom.get());
             	return existingRoom.get();
             }
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
	 private String generatePrivateRoomName(HashMap<String,String> members) {
	        return members.values().stream()
	                      .sorted()
	                      .reduce((a, b) -> a + "_" + b)
	                      .orElseThrow(() -> new IllegalArgumentException("Invalid members"));
	    }
	 
	 
	 public List<ChatRoom> fetchRooms(String email) {
		 String encoded_email=ChatRoom.encodeEmail(email);
		 List<ChatRoom> rooms=chatRoomRepository.findByMemberEmailsContaining(encoded_email);
		 rooms.forEach(room -> {
		        HashMap<String, String> decodedMembers = new HashMap<>();
		        room.getMembers().forEach((key, value) -> {
		            decodedMembers.put(ChatRoom.decodeEmail(key), value);
		        });
		        room.setMembers(decodedMembers);
		    });

		    return rooms;
	 }
	
	
	
}
