package com.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.Messages;
import com.project.service.ChatService;

@RestController
@RequestMapping("/api")
public class MessageController {
	private ChatService chatService;
	public MessageController(ChatService chatService) {
		this.chatService=chatService;
		
	}
	
	@GetMapping("/messages")
	public ResponseEntity<?> loadMessage(@RequestParam String roomId){
		try {
			List<Messages> list_message=chatService.getMessage(roomId);
			return ResponseEntity.ok(list_message);
			
		}catch(Exception e) {
//			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
