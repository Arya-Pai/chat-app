package com.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.AuthRes;
import com.project.model.LoginDTO;
import com.project.model.RegisterDTO;
import com.project.service.AuthService;


@RestController
@RequestMapping("/api")
public class authController {
	private final AuthService authService;

    public  authController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/registerController")
    public ResponseEntity<String> register(@RequestBody RegisterDTO req) {
        authService.register(req); // just saves user, no token returned
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/loginController")
    public ResponseEntity<AuthRes> login(@RequestBody LoginDTO req) {
        return ResponseEntity.ok(authService.login(req));
        
    }
    
    
    
	
}
