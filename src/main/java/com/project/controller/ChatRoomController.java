package com.project.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.ChatRoom;
import com.project.model.User;
import com.project.service.ChatRoomService;

@RestController
@RequestMapping("/chatRooms")
public class ChatRoomController {
	private ChatRoomService chatRoomService;
	public ChatRoomController(ChatRoomService chatRoomService) {
		this.chatRoomService=chatRoomService;
	}
	
	
	@PostMapping("/joinRoom")
	public ResponseEntity<?> createChatRoom(@RequestBody ChatRoom roomReq) {
		
		try {
			ChatRoom created = chatRoomService.joinRoom(roomReq);
			return ResponseEntity.status(HttpStatus.CREATED).body(created);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error creating chat room: " + e.getMessage());
		}
		
	}
	@GetMapping("/fetchRooms")
	public ResponseEntity<List<ChatRoom>> fetchAllRooms(Authentication authentication){
		String email=authentication.getName();
		try {
			return ResponseEntity.ok(chatRoomService.fetchRooms(email));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(Collections.emptyList());
		}
		
		
	}
	@GetMapping("/getAll")
	public ResponseEntity<List<ChatRoom>> getAllRooms(Authentication authentication){
		String email =authentication.getName();
		try {
			return ResponseEntity.ok(chatRoomService.fetchRooms(email));
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(Collections.emptyList());
		}
	}
	
	
	
}
