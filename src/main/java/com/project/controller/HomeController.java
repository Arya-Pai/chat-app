package com.project.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.ChatRoom;
import com.project.model.User;
import com.project.repo.SearchUser;
import com.project.repo.UserRepo;
import com.project.service.ChatRoomService;

@RestController

public class HomeController {
	@Autowired
	private  UserRepo userRepo;
	@Autowired
	private  ChatRoomService chatRoomService;
	@Autowired
	private  SearchUser searchUser; 
	
   
   
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUser(Authentication authentication){
		String email=authentication.getName();
		try {
			return ResponseEntity.ok(userRepo.findByEmailNot(email));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(Collections.emptyList());
		}
		
		
	}
	
	@GetMapping("/users/search{text}")
	public ResponseEntity<List<User>> getAllUser(@PathVariable String text){
		try {
			return ResponseEntity.ok(searchUser.findAllUser(text));
		}catch(Exception e) {
			e.getMessage();
			e.getCause();
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(Collections.emptyList());
		}
	}
	
	
	
	@GetMapping("/getUser")
	public ResponseEntity<User> getUser(@RequestParam String email){
		
		try {
			Optional<User> user=userRepo.findByEmail(email);
			if(user.isPresent())
				return ResponseEntity.ok(user.get());
			else {
				 return ResponseEntity.notFound().build();
			}
		}catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			
		}
	}
}
