package com.project.controller;

import java.util.Collections;
import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.model.User;
import com.project.repo.UserRepo;

@RestController

public class HomeController {
	private final UserRepo userRepo;

   
    public HomeController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
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
}
